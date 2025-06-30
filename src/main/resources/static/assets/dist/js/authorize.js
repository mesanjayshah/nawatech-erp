function authorize(key, instance, callback, params, isAdmin = false) {
    showButtonLoader(instance, PLEASE_WAIT);
    const url = baseUrl + (!isAdmin ? "userpermission" : "permission") + "/isauthorized";
    $.post(url, {key: key})
        .done(function (response) {
            const isAuthorized = response.success;
            if (!isAuthorized) {
                $("#unauthorizedAccessModal").modal("show");
                return;
            }
            if (callback) {
                setTimeout(function () {
                    if (params) callback(...params);
                    else callback(instance);
                });
            }
        })
        .fail(function () {
            showAlert("Sorry but something went wrong while checking your privilege to access this page.", false);
        })
        .always(function () {
            hideButtonLoader(instance);
        });
}



