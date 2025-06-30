const POST = "POST", GET = "GET";

/**
 * performs get and post ajax request and returns promises
 * @param url
 * @param params
 * @param requestType
 * @param instance
 * @param isExternalRequest
 * @returns {Promise<unknown>}
 */
function performRequest(url, params, requestType = POST, instance = "#btnFilter", isExternalRequest = false) {
    let isHideLoader = url !== "support/ticket/notification/count";
    if (!isExternalRequest) url = baseUrl + url;
    return new Promise((resolve, reject) => {
        const request = !requestType || requestType === POST ? $.post(url, params) : $.get(url, params);
        request.done((response) => {
            resolve(response);
        }).fail((xhr, status, error) => {
            if (status !== "abort") {
                hideButtonLoader(instance);
                showAlert(SOMETHING_WENT_WRONG, false);
            }
        }).always(() => {
            hideButtonLoader(instance);
            if (isHideLoader) hideLoader();
        });
    });
}

/**
 * performs get and post request that is supported by spring as a
 * @param url
 * @param payload
 * @param requestType
 * @returns {Promise<unknown>}
 */
function performAdvancedRequest(url, payload, requestType = POST, instance = "#btnFilter") {
    url = baseUrl + url;
    return new Promise((resolve, reject) => {
        $.ajax({
            url: url,
            dataType: 'json',
            contentType: 'application/json',
            method: requestType,
            data: JSON.stringify(payload)
        }).done((response) => {
            resolve(response);
        }).fail((xhr, status, error) => {
            if (status !== "abort") showAlert(SOMETHING_WENT_WRONG, false);
        }).always(() => {
            if (instance) hideButtonLoader(instance);
            hideLoader();
        });
    });
}

//supports select instances with multiple selection attribute
function extractRequestParams(formInstance) {
    let params = {};
    $(formInstance).find("input:not(.select-dropdown)[name], select[name], textarea[name]").each(function () {
        const name = $(this).attr("name");
        if (name) {
            let val;

            if ($(this).prop("tagName") === "SELECT" && getValue($(this).attr("multiple"))) {
                val = $(this).val() ? $(this).val().join(",") : $(this).val();
            } else val = $(this).val();

            params[name] = val;
        }
    });
    return params;
}

function performMultipartRequest(url, formData, requestType = POST, instance = "#btnFilter") {
    url = baseUrl + url;
    return new Promise((resolve, reject) => {
        $.ajax({
            type: requestType,
            data: formData,
            url: url,
            cache: false,
            contentType: false,
            processData: false
        }).done((response) => {
            resolve(response);
        }).fail((xhr, status, error) => {
            if (status !== "abort") showAlert(SOMETHING_WENT_WRONG, false);
        }).always(() => {
            if (instance) hideButtonLoader(instance);
            hideLoader();
        });
    });
}

function performMultipartRequestForAPI(url, formData, requestType = POST, instance = "#btnFilter") {
    // url = baseUrl + url;
    return new Promise((resolve, reject) => {
        $.ajax({
            type: requestType,
            data: formData,
            url: url,
            cache: false,
            contentType: false,
            processData: false
        }).done((response) => {
            resolve(response);
        }).fail((xhr, status, error) => {
            if (status !== "abort") showAlert(SOMETHING_WENT_WRONG, false);
        }).always(() => {
            if (instance) hideButtonLoader(instance);
            hideLoader();
        });
    });
}