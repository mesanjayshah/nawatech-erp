let SUCCESS_ALERT = "SUCCESS_ALERT", ERROR_ALERT = "ERROR_ALERT", INFO_ALERT = "INFO_ALERT",
    WARNING_ALERT = "WARNING_ALERT";

function showAlert(content, isSuccessAlert = true, title = null, callback) {
    if (!content) return;

    let type = "blue", icon = "fas fa-bullhorn";
    if (!title) {
        // console.log()
        if (typeof isSuccessAlert === 'boolean') {
            title = isSuccessAlert ? "Success" : "Failed";
            type = isSuccessAlert ? "green" : "red";
            icon = isSuccessAlert ? "fa fa-check" : "fa fa-times";
        } else {
            switch (isSuccessAlert) {
                case SUCCESS_ALERT:
                    title = "Success";
                    type = "green";
                    icon = "fa fa-check";
                    break;

                case ERROR_ALERT:
                    title = "Failed";
                    type = "error";
                    icon = "fa fa-times";
                    break;

                case INFO_ALERT:
                    title = "Message";
                    type = "blue";
                    icon = "fa fa-info-circle";
                    break;

                case WARNING_ALERT:
                    title = "Warning";
                    type = "orange";
                    icon = "fa fa-warning";
                    break;

                default:
                    break;
            }
        }
    }

    $.alert({
        title: title,
        type: type,
        icon: icon,
        content: content,
        draggable: false,
        onOpen: function () {
            $(this.$btnc[0]).find(".btn:eq(0)").focus();
        },
        buttons: {
            ok: {
                action: function () {
                    if (callback) {
                        if (callback.func) {
                            setTimeout(function () {
                                let params = callback.params;
                                callback.func.apply(null, params);
                            }, 500);
                        } else callback();
                    }
                }
            }
        }
        // useBootstrap: false
    });
}

const defaultConfirmButtons = [{text: 'Yes [y]'}, {text: 'No [n]'}];

function showConfirmDialog(title, content, positiveCallback, negativeCallback, actionButtons = defaultConfirmButtons) {
    $.confirm({
        title: title,
        content: content,
        draggable: false,
        type: "orange",
        icon: 'fa fa-warning',
        columnClass: 'col-md-5',
        onOpen: function () {
            $(this.$btnc[0]).find(".btn:eq(0)").focus();
        },
        buttons: {
            yes: {
                keys: ['y'],
                text: actionButtons[0].text,
                btnClass: 'btn-primary',
                action: function () {
                    if (positiveCallback) {
                        let params = positiveCallback.params;
                        positiveCallback.func.apply(null, params);
                    }
                }
            },
            no: {
                keys: ['n'],
                text: actionButtons[1].text,
                action: function () {
                    if (negativeCallback) {
                        let params = negativeCallback.params;
                        negativeCallback.func.apply(null, params);
                    }
                }
            },
            // cancel: {
            //     keys: ['c'],
            //     text: 'Cancel',
            //     action: function() {
            //     }
            // }
        }
    });
}

function confirmAndDelete(callback, params) {
    showConfirmDialog(CONFIRM_DELETE, DELETE_CONFIRMATION, generateCallback(callback, params));
}

function confirmAndSubmit(instance, validateMethod = null, saveAndPrint = false, action = null) {
    showConfirmDialog("Confirm Action", `Are you sure you want to ${action ? action : 'perform this action'} ?`, generateCallback(submitFormNow, [instance, validateMethod, saveAndPrint]));
}

function confirmAndEval(instance) {
    showConfirmDialog("Confirm Action", "Are you sure you want to perform this action ?", generateCallback(evalMethod, [instance]));
}

function evalMethod(instance) {
    let that = instance;
    let onValidMethod = instance.attr('onValid');
    onValidMethod = onValidMethod.replace('this', 'that');
    eval(onValidMethod);
}

function submitFormNow(buttonInstance, validateMethod = null, saveAndPrint = false) {
    let formInstance = $(buttonInstance).closest("form");
    if (typeof (validateMethod) !== 'boolean') {
        if (validateMethod && !validateMethod()) return;
        if (!isValidForm(formInstance)) return;
    }
    if (saveAndPrint) $("input[name=saveAndPrint]").val("1");
    formInstance.submit();
    showButtonLoader(buttonInstance, PLEASE_WAIT);
}

/**
 *
 * @param func
 * @param params
 * @returns {{func: *, params: *}}
 */
function generateCallback(func, params) {
    return {func, params};
}

