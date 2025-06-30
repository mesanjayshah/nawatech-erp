let moduleId, subModuleId, menuLineId, subMenuLineId;

$(function () {
    populateParentModuleNamesOnly();
})

function getRequestParams(instance) {
    let parentModuleId = $("#parent-module option:selected").val();
    return finalizeRequestParams({parentModuleId}, instance);
}

let request;

function populateNavigations(instance = "#selectLimit") {

    const listInstance = $("#custom-list-menu");
    showCustomListLoader(listInstance);

    performRequest("navigation/find-all", getRequestParams(instance), POST)
        .then((response) => {

            let rows = "";
            if (response.body) {
                const modules = response.body.moduleList;

                $.each(modules, function (i, module) {

                    const id = module.id;
                    const editRoute = baseUrl + "navigation/" + id + "/edit";
                    const deleteRoute = baseUrl + "navigation/" + id + "/delete";
                    let actionButtons = generateActionButton("Quick Edit", EDIT_ICON, null, {
                        "onclick": "displayQuickEditModalNew(this)",
                        "data-val": id
                    });

                    let imageSource = "";
                    let image = module.iconSVG ? module.iconSVG : "";
                    if (image) imageSource = baseUrl + "files/icons/" + image;

                    const auditLog = "Created by: " + module.createdBy + " at " + module.createdAt + "\n" + (module.modifiedBy ? "Modified by: " + module.modifiedBy + " at " + module.modifiedAt : "");

                    actionButtons += generateActionButton(EDIT_TEXT, EDIT_ICON, editRoute);
                    actionButtons += generateActionButton(DELETE_TEXT, DELETE_ICON, deleteRoute, {"onclick": "return confirm('Are you sure you want to delete ?')"});

                    rows += `<div class="list-row">
                                <div class="list-col column-action">
                                    <div class="column-action-content d-flex">
                                            <div class="pl-2 flex-info">
                                            <div class="title font-weight-500">${getValue(module.name)}</a></div>
                                          </div>
                                    </div>
                                    ${wrapActionButtons(actionButtons, true)}
                                </div>
                                
                                <div class="list-col">${getValue(module.parentModuleName)}</div>`

                    if (module.iconBootstrap)
                        rows += `<div class="list-col">
                                    <i class="bi bi-${module.iconBootstrap}" style="width: 20px; height: 20px;"></i>
                                 </div>`
                    else
                        rows += `<div class="list-col"><i class="material-icons">${module.icon}</i></div>`

                    rows += `<div class="list-col">${getRoleShortCode(module.role)}</div>
                            <div class="list-col flex-column justify-content-center">
                                <div>${module.permissionKey ?? ''}</div>
                                <div>${module.targetUrl ?? '#'}</div>
                            </div>
                            <div class="list-col">${getValue(module.rank)}</div>
                            <div class="list-col">${getDisplayableStatus(module.status, ACTIVE_INACTIVE)} &nbsp; ${getDisplayableStatus(module.viewMenu, YES_NO)}</div>
                            <div class="list-col">${getDisplayableStatus(module.topViewMenu, YES_NO)}</div>
                            <div class="list-col" data-toggle="tooltip" title="${auditLog}"><i class="material-icons">timer</i></div>
                            <div class="list-col">${(module.attachment ? '<a target="_blank" href="' + (baseUrl + "files/module-attachment/" + module.attachment) + '"><i class="material-icons">search</i>View</a>' : '')}</div>
                </div>`;

                })

                initCustomList(listInstance, wrapResult(rows, modules.length, response.body.total), false, false);
            }
            showAlert(response.message, success);
        })
        .finally(() => {
            hideButtonLoader(instance);
            hideCustomListLoader(listInstance);
        })
}

function getRoleShortCode(role) {
    let shortCode = "";
    switch (role) {
        case "MODULE":
            shortCode = "M";
            break;

        case "SUB_MODULE":
            shortCode = "SM";
            break;

        case "MENU_LINE":
            shortCode = "ML";
            break;

        case "SUB_MENU_LINE":
            shortCode = "SML";
            break;

        default:
            break;
    }
    return shortCode;
}

function populateParentModuleNamesOnly() {
    let url = baseUrl + "navigation/fetch-all-parent-modules";

    $.post(url)
        .done(function (response) {
            let html = "<option value = ''> All </option>";
            if (response.success) {
                $.each(response.body, function (index, item) {
                    html += "<option value = '" + item.id + "'>" + item.name + "</option>";
                });

                $("#parent-module").html(html);
            }
        })
        .fail(function (xhr, status, error) {
            showAlert(SOMETHING_WENT_WRONG, false);
        })
        .always(function () {

        })
}

function handleRoleChange(instance) {
    $(".moduleBlock, .subModuleBlock, .menuLineBlock").hide();
    $(".module, .subModule, .menuLine").attr({required: false});
    $(".targetUrl.mandatory").hide();

    var value = $(instance).val();
    if (value === 'SUB_MENU_LINE') {
        $(".moduleBlock, .subModuleBlock, .menuLineBlock").show();
        $("input[name=targetUrl]").attr({required: true});
        $(".targetUrl.mandatory").show();
    } else if (value === 'MENU_LINE') {
        $(".targetUrlBlock").show();
        $("input[name=targetUrl]").attr({required: true});
        $(".targetUrl.mandatory").show();

        $(".moduleBlock").show();
        $(".subModuleBlock").show();
    } else if (value === 'SUB_MODULE') {
        $(".moduleBlock").show();
    }

    if (value !== 'MODULE') {
        if (value === 'MENU_LINE') {
            $("select.subModule").attr("required", true);
        }
        $("select.module").attr("required", true);
        filterModules('select.module', null);
    }
}

function filterModules(instance, role) {

    let parentModuleId = role ? $(instance).val() : null;

    let selectInstance;
    let id;
    switch (role) {
        case "SUB_MODULE":
            id = subModuleId;
            selectInstance = $("select.subModule");
            break;
        case "MENU_LINE":
            id = menuLineId;
            selectInstance = $("select.menuLine");
            break;
        default:
            id = moduleId;
            selectInstance = $("select.module");
    }
    if (!role) role = 'MODULE';

    performRequest("navigation/filter", {role, parentModuleId, activeOnly: true}, POST)
        .then((response) => {

            console.log("response  >>> " , response);

            const success = response.success;
            let options = `<option value="">--- Select ---</option>`;
            if (success) {
                $.each(response.body, function (i, item) {
                    options += `<option ${item.id === id ? 'selected' : ''} value="${item.id}">${item.name}</option>`;
                });
            }
            updateSelectOptions(selectInstance, options);
            showAlert(response.message, success);

            if (role === 'MODULE' && subModuleId) $("select.module").trigger("change");
            if (role === 'SUB_MODULE' && menuLineId) $("select.subModule").trigger("change");
        })
        .finally(() => {
            showAlert(SOMETHING_WENT_WRONG, false);
        })

}

function filterAssignedModules(instance, role) {
    const url = baseUrl + "admin/module/filter-assigned";
    let parentModuleId = role ? $(instance).val() : null;
    let selectInstance;
    let id;
    switch (role) {
        case "SUB_MODULE":
            id = subModuleId;
            selectInstance = $("select.subModule");
            break;
        case "MENU_LINE":
            id = menuLineId;
            selectInstance = $("select.menuLine");
            break;
        default:
            id = moduleId;
            selectInstance = $("select.module");
    }
    if (!role) role = 'MODULE';

    $.post(url, {role, parentModuleId, activeOnly: true})
        .done(function (response) {
            const success = response.success;

            let options = `<option value="">--- Select ---</option>`;
            if (success) {
                $.each(response.body, function (i, item) {
                    options += `<option ${item.id === id ? 'selected' : ''} value="${item.id}">${item.name}</option>`;
                });
            }
            updateSelectOptions(selectInstance, options);
            showAlert(response.message, success);

            if (role === 'MODULE' && subModuleId) $("select.module").trigger("change");
            if (role === 'SUB_MODULE' && menuLineId) $("select.subModule").trigger("change");
        })
        .fail(function () {
            showAlert(SOMETHING_WENT_WRONG, false);
        })
        .always(function () {

        });
}

function filterModuleByParentIds(instance, role) {
    let parentModuleIds = role ? $(instance).val() : null;
    let selectInstance, id, selectedInstanceValue;
    switch (role) {
        case "SUB_MODULE":
            id = subModuleId;
            selectInstance = $("select.subModule");
            selectedInstanceValue = selectInstance.val();
            break;
        case "MENU_LINE":
            id = menuLineId;
            selectInstance = $("select.menuLine");
            break;
        default:
            id = moduleId;
            selectInstance = $("select.module");
    }
    if (!role) role = 'MODULE';

    if (parentModuleIds.length > 0) {
        const url = baseUrl + "admin/module/filterByParentModuleIds";
        $.post(url, {role, parentModuleIds: parentModuleIds.join(), activeOnly: true})
            .done(function (response) {
                const success = response.success;
                let options = DEFAULT_SELECT_OPTION_SELECTED_DISABLED;
                if (success) {
                    $.each(response.body, function (i, item) {
                        options += `<option value="${item.id}">${item.name}</option>`;
                    });
                }
                updateSelectOptions(selectInstance, options);
                showAlert(response.message, success);

                if (role === 'MODULE' && subModuleId) $("select.module").trigger("change");
                if (role === 'SUB_MODULE' && menuLineId) $("select.subModule").trigger("change");
            })
            .fail(function () {
                showAlert(SOMETHING_WENT_WRONG, false);
            })
            .always(function () {

            });
    } else {
        updateSelectOptions(selectInstance, []);
    }
}

function filterModulesBySchoolId(instance, role) {
    const url = baseUrl + "admin/module/filterBySchoolId";
    let parentModuleId = role ? $(instance).val() : null;
    let selectInstance;
    let id;
    switch (role) {
        case "SUB_MODULE":
            id = subModuleId;
            selectInstance = $("select.subModule");
            break;
        case "MENU_LINE":
            id = menuLineId;
            selectInstance = $("select.menuLine");
            break;
        default:
            id = moduleId;
            selectInstance = $("select.module");
    }
    if (!role) role = 'MODULE';

    $.post(url, {role, parentModuleId, activeOnly: true})
        .done(function (response) {
            const success = response.success;
            let options = DEFAULT_SELECT_OPTION_SELECTED_DISABLED;
            if (success) {
                $.each(response.body, function (i, item) {
                    options += `<option value="${item.id}">${item.name}</option>`;
                });
            }
            updateSelectOptions(selectInstance, options);
            showAlert(response.message, success);

            if (role === 'MODULE' && subModuleId) $("select.module").trigger("change");
            if (role === 'SUB_MODULE' && menuLineId) $("select.subModule").trigger("change");
        })
        .fail(function () {
            showAlert(SOMETHING_WENT_WRONG, false);
        })
        .always(function () {

        });
}

function filterModulesForAdmin(instance, role, data, isOpenModal) {
    if (data) {
        if (data.role === 'MENU_LINE') {
            menuLineId = data.id;
            subModuleId = data.parentModuleId;
            moduleId = data.mainParentModuleId;
        } else if (data.role === 'SUB_MODULE') {
            subModuleId = data.id;
            moduleId = data.parentModuleId;
            menuLineId = 0;
        } else {
            moduleId = data.id;
            subModuleId = 0;
            menuLineId = 0;
        }
    } else if (!data && isOpenModal) {
        //if there is no module assigned for given support ticket and request is from open modal, set all the
        //values to 0 to prevent selection of previous request's data
        menuLineId = 0, subModuleId = 0, moduleId = 0;
    }

    const url = baseUrl + "admin/module/admin/filterbyrole";
    let parentModuleId = role ? $(instance).val() : null;
    let selectInstance;
    let id;
    switch (role) {
        case "SUB_MODULE":
            id = subModuleId;
            selectInstance = $("select.subModule");
            break;
        case "MENU_LINE":
            id = menuLineId;
            selectInstance = $("select.menuLine");
            break;
        default:
            id = moduleId;
            selectInstance = $("select.module");
    }
    if (!role) role = 'MODULE';

    $.post(url, {role, parentModuleId, activeOnly: true})
        .done(function (response) {

            const success = response.success;
            let options = DEFAULT_SELECT_OPTION_SELECTED;
            if (success) {
                $.each(response.body, function (i, item) {
                    options += `<option value="${item.id}" ${item.id === id ? 'selected' : ''}>${item.name}</option>`;
                });
            }

            updateSelectOptions(selectInstance, options);
            showAlert(response.message, success);

            if (role === 'MODULE' && subModuleId) {
                $("select.module").trigger("change");
            }
            if (role === 'SUB_MODULE' && menuLineId) {
                $("select.subModule").trigger("change");
            }
        })
        .fail(function () {
            showAlert(SOMETHING_WENT_WRONG, false);
        })
        .always(function () {
        });
}

// edit

function displayQuickEditModalNew(instance) {
    showButtonLoader(instance, PLEASE_WAIT);
    let id = $(instance).attr("data-val");
    $("#id").val(id);

    console.log("id ->>> ", id);

    performRequest("navigation/find-by-id-quick", {id})
        .then((response) => {
            if (response.success) {
                const data = response.body,
                    attachmentContainerInstance = $("#attachmentContainer");
                // attachment = data.attachment;
                attachmentContainerInstance.html("");
                $("input[name='attachmentFile']").val("").trigger("change");
                $("#name").val(data.name);
                $("#icon").val(data.icon);
                $("#iconBootstrap").val(data.iconBootstrap);
                // $("#imageShow").val(image);
                // $("#previousImage").val(attachment);
                $("#targetUrl").val(data.targetUrl);
                $("#permissionKey").val(data.permissionKey);
                $("#rank").val(data.rank);
                $("#chckIsViewMenu").prop("checked", data.viewMenu).trigger("change");
                $("#checkIsTopViewMenu").prop("checked", data.topViewMenu).trigger("change");
                $("#chckActive").prop("checked", data.status).trigger("change");
                if (attachment) attachmentContainerInstance.html("<a href='" + baseUrl + "files/module-attachment/" + attachment + "'><i class='material-icons'>search</i> View</a>")
                showModal("#modalQuickEdit");
            }
        })
        .finally(() => {
            hideButtonLoader(instance);
        })
}

$("#quick-update-module-new").on('submit', function (e) {
    e.preventDefault();
    let buttonInstance = $("#btnSubmit");
    let formData = new FormData(this);
    let url = baseUrl + "admin/module/quickupdate";
    $.ajax({
        type: "POST",
        data: formData,
        url: url,
        cache: false,
        contentType: false,
        processData: false,
        success: function (response) {
            const success = response.success,
                shouldRefresh = $("#checkRefreshOnUpdate").is(":checked");
            showAlert(response.message, success, null, success ? (shouldRefresh ? populateParentModules : null) : null);
            hideModal("#modalQuickEdit");
        }
    })
        .fail(function (xhr, status, error) {
            showAlert(SOMETHING_WENT_WRONG, false);
        }).always(function () {
        hideButtonLoader(buttonInstance);
    })
});
