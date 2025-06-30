$(function () {
    if (populateAll) populateBranches();
    if (getSearchParams(['modal']).modal) $("#btnAdd").click();
});

function checkBranchCodePresent(instance) {
    let code = $(instance).val();
    let id = $(instance).closest("form").find("input[name=id]").val();
    if (!id) id = null;
    if (!code) return;

    performRequest("branch/isCodeAvailable", {code: code, id: id})
        .then((response) => {
            if (!response.body) $("input[name=code]").val("");
            if (response.message) showAlert(response.message, INFO_ALERT);
        })
        .finally(() => {
            hideLoader();
        })
}

function showAddOrEditBranchModal() {
    const modalInstance = "#modalAddOrEditBranch";
    setModalTitle(modalInstance, "Add Branch");
    setModalSubmitButton(modalInstance, " Save");
    resetForm($("#formAddOrEditBranch"), true);

/*    performRequest("common/code", {type: "branch"})
        .then((response) => {
            if (response.success) {
                const getCode = response.body;
                const formInstance = $("#formAddOrEditBranch");
                formInstance.find("input[name=code]").val(getCode);
                triggerChange($("#modalAddOrEditBranch"));
            }
        })
        .finally(() => {
            hideLoader();
        })*/

    showModal(modalInstance);
}

function populateBranchById(id, showBranchInfoModal) {
    showLoader();
    if (!showBranchInfoModal) setModalTitle($("#modalAddOrEditBranch"), "Edit Branch");
    setModalSubmitButton($("#modalAddOrEditBranch"), " Update", "update");
    const formInstance = showBranchInfoModal ? $("#formBranchInfo") : $("#formAddOrEditBranch");
    resetForm(formInstance, true);
    performRequest("branch/fetch-by-id", {id: id, role: showBranchInfoModal ? 'info' : 'edit'})
        .then((response) => {
            if (response.success) {
                const branch = response.body;
                const modalInstance = showBranchInfoModal ? $("#modalBranchInfo") : $("#modalAddOrEditBranch");
                formInstance.find("input[name='id']").val(branch.id);
                formInstance.find("input[name=code]").val(branch.code);
                formInstance.find("input[name=name]").val(branch.name);
                formInstance.find("select[name=staffId]").val(branch.staff ? branch.staff.id : "");
                formInstance.find("input[name=address]").val(branch.address);
                formInstance.find("input[name=website]").val(branch.website);
                formInstance.find("input[name=location]").val(branch.location);
                formInstance.find("input[name=contactNumber]").val(branch.contactNumber);
                formInstance.find("input[name=contactName]").val(branch.contactName);
                formInstance.find(".toggleStatus").prop("checked", branch.status).trigger("change");
                formInstance.find("textarea[name=remarks]").val(branch.remarks);

                if (showBranchInfoModal) {
                    formInstance.find("#createdDate").val(getFinalDate(branch.createdDate));
                    formInstance.find("#createdBy").val(branch.createdBy.username);
                    formInstance.find("#modifiedDate").val(getFinalDate(branch.modifiedDate));
                    formInstance.find("#modifiedBy").val(branch.modifiedBy ? branch.modifiedBy.username : "");
                }

                showModal(modalInstance);
                triggerChange(modalInstance);
            }
        })
        .finally(() => {
            hideLoader();
        })

}

function saveOrUpdateBranch(instance) {
    const formInstance = $("#formAddOrEditBranch");
    if (!isValidForm(formInstance)) return;
    showButtonLoader(instance, PLEASE_WAIT);
    const data = formInstance.serialize();
    console.log(data, "data")
    performRequest("branch/save-update", data)
        .then((response) => {
            console.log(response)
            const success = response.success;
            if (success) hideModal("#modalAddOrEditBranch");
            showAlert(response.message, success, null, success ? populateBranches : null);
        })
        .finally(() => {
            hideButtonLoader(instance);
        })
}

function populateBranches() {
    console.log(">>>>>   2")
    showLoader();
    const tableInstance = $("#tableBranch");
    destroyDataTable(tableInstance);

    performRequest("branch/findAll", {})
        .then((response) => {
            console.log("response   >>> ", response)

            const success = response.success;
            let rows = "";
            if (success) {
                $.each(response.body, function (i, branch) {
                    const managerId = getValue(branch.managerId, null);
                    const branchId = branch.id;
                    const managerHtml = managerId ? `<a target="_blank" href="${baseUrl}/staff/${managerId}/info">${branch.managerName}</a>` : '';

                    let actionButtons = generateActionButton(EDIT_TEXT, EDIT_ICON, null, {"onclick": `authorize('${editRoutePermissionKey}', this, populateBranchById, [${branchId}])`});
                    actionButtons += generateActionButton(DELETE_TEXT, DELETE_ICON, null, {
                        "onclick": `confirmAndDelete(deleteBranch, [this, ${branchId}])`
                    });

                    rows += `<tr class="text-center">
                                <td>${i + 1}.</td>
                                <td class="text-left"><a href='#' onclick='populateBranchById(${branch.id}, true)'>${branch.name}</a></td>
                                <td>${getValue(branch.code)}</td>
                                <td>${managerHtml}</td>
                                <td>${getValue(branch.contactName)}</td>
                                <td>${getValue(branch.contactNumber)}</td>
                                <td class="text-left">${getValue(branch.address)}</td>
                                <td>${getDisplayableStatus(branch.status, ACTIVE_INACTIVE, true)}</td>
                                ${wrapActionButtons(actionButtons)}
                            </tr>`;
                });
            }
            tableInstance.find("tbody").html(rows);
            showAlert(response.message, success);
        })
        .finally(() => {
            initDataTable(tableInstance);
            hideLoader();
        })

}

function deleteBranch(instance, id) {
    showButtonLoader(instance);
    const url = baseUrl + 'branch/delete';

    performRequest("branch/delete", {id})
        .then((response) => {
            const success = response.success;
            showAlert(response.message, success, null, success ? populateBranches : null);
        })
        .finally(() => {
            hideButtonLoader(instance);
        })
}