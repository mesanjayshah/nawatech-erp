
const columns = [
    {
        key: "name",
        title: "Name",
        width: 220,
    },
    {
        key: "parent_module",
        title: "Parent Module",
        width: 220,
    },
    {
        key: "role",
        title: "Role",
        width: 80
    },
    {
        key: "permission_key",
        title: "Permission Key/Target Url",
        width: 320,
    },
    {
        key: "rank",
        title: "Rank",
        width: 80
    },
    {
        key: "status_view_menu_quick_menu",
        title: "Status/View Menu/Quick Menu",
        width: 120
    },
    {
        key: "widget",
        title: "Widget",
        width: 80
    },
    {
        key: "thumbnail",
        title: "Thumbnail",
        width: 70
    }
];

function resetDepartmentModal() {
    $("#formAddAdminDepartment").trigger("reset");
    $("label.error").remove();
    $(".error").html("");
    $("#tbl_admin_department tbody>tr").not("#tbl_admin_department tbody>tr:first").remove();
    $("#tbl_admin_department tbody>tr:first>td:last").empty();
    $(".remove").prop('disabled', true);
}

function saveAdminDepartment(instance) {
    if (allRequiredFieldsValid(instance)) {
        showButtonLoader(instance, "Saving");
        var url = baseUrl + "admin/department/save";
        $.post(url, $("#formAddAdminDepartment").serialize())
            .done(function (response) {
                let success = response.success;
                $("#createAdminDepartmentModal").modal("hide");
                showAlert(response.message, success, null, success ? populateAll : null)

            })
            .fail(function (xhr, status, error) {
                showAlert(SOMETHING_WENT_WRONG, false);
            })
            .always(function () {
                hideButtonLoader(instance);
            });
    }
}

function deleteAdminDepartment(id) {
    var pathUrl = baseUrl + "admin/department/delete";
    $.post(pathUrl, {id})
        .done(function(response){
            let success = response.success;
            showAlert(response.message, success, null, success ? populateAll : null);
        })
        .fail(function(xhr, status, error) {
            showAlert(SOMETHING_WENT_WRONG, false);
        })
        .always(function() {
        });
}

function populateAll() {
    var tableInstance = $("#admin_department_list");
    destroyDataTable(tableInstance);
    showLoader();
    var url = baseUrl + "admin/department/findall";
    $.post(url)
        .done(function(response){
            var rows = "";
            if (response.success) {
                $.each(response.body, function(index, adminDepartment) {
                    rows += "<tr>";
                    rows += "<td>"+ (index+1) +".</td>";
                    rows += "<td>"+ adminDepartment.name +"</td>";
                    rows += "<td class='text-center'>"+ getDisplayableStatus(adminDepartment.publicDepartment, PUBLIC_PRIVATE) +"</td>";
                    rows += "<td class='text-center'>"+ getDisplayableStatus(adminDepartment.status, ACTIVE_INACTIVE) +"</td>";
                    rows += "<td class='text-center'>"+ getFinalDate(adminDepartment.createdDate)+"</td>";
                    rows += "<td class='text-center'>"+ adminDepartment.createdBy +"</td>";
                    rows += "<td class='text-center'>"+getFinalDate(adminDepartment.modifiedDate)+"</td>";
                    rows += "<td class='text-center'>"+ (adminDepartment.modifiedBy ? adminDepartment.modifiedBy : "") +"</td>";

                    let actionButtons = generateActionButton("Edit", EDIT_ICON, null, {"onclick":"populateData(this)", "data-val":+adminDepartment.id});
                    actionButtons += generateActionButton("Delete", DELETE_ICON, null, {"onclick":"confirmAndDelete(deleteAdminDepartment, ["+ adminDepartment.id +"])","class": "text-danger"});

                    rows += wrapActionButtons(actionButtons);
                    rows += "</tr>";
                });
                tableInstance.find("tbody").html(rows);
            }
        })
        .fail(function(xhr, status, error){
            showAlert(SOMETHING_WENT_WRONG, false);
        })
        .always(function(){
            initDataTable(tableInstance);
            hideLoader();
        });
}

function populateData(instance) {
    var id = $(instance).attr("data-val");
    var url = baseUrl + "admin/department/findbyid";
    $.post(url, {id})
        .done(function (response) {
            let success = response.success;
            if (success) {
                let data = response.body;
                showModal($("#updateAdminDepartmentModal"));
                let formInstance = $("#formEditAdminDepartment");
                resetForm(formInstance);
                formInstance.find("input[name='name']").val(data.name);
                formInstance.find(".toggleStatusDepartment").prop("checked", data.publicDepartment).trigger('change');
                formInstance.find(".toggleStatus").prop("checked", data.status).trigger('change');
                formInstance.find("#id").val(data.id);
            }
            showAlert(response.message, success);
        }).fail(function (xhr, status, error) {
        showAlert(SOMETHING_WENT_WRONG, false);
    }).always(function () {

    })
}

function updateAdminDepartment(instance) {
    if (isValidForm($("#formEditAdminDepartment"))) {
        showButtonLoader(instance, UPDATING);
        var data = $("#formEditAdminDepartment").serialize();
        var url = baseUrl + "admin/department/update";
        $.post(url, data)
            .done(function (response) {
                let success = response.success;
                if (success) hideModal("#updateAdminDepartmentModal");
                showAlert(response.message, success, null, success ? populateAll : null);
            })
            .fail(function (xhr, status, error) {
                showAlert(SOMETHING_WENT_WRONG, false);
            })
            .always(function () {
                hideButtonLoader(instance);
            });
    }

}