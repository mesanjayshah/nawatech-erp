const
    token = $("meta[name='_csrf']").attr("content"),
    header = $("meta[name='_csrf_header']").attr("content"),
    parameterName = $("meta[name='_csrf_parameterName']").attr("content");

//append to ajax requests
/*$(document).ajaxSend(function (e, xhr, options) {
    xhr.setRequestHeader(header, token);
    // xhr.setRequestHeader("Content-type", "application/json; charset=utf-8");
});*/

//append hidden CSRF field to all form tags
// $("form").append(`<input type="hidden" name="${parameterName}" value="${token}" />`);

//append CSRF token on each form tag
$("form").prepend("<input type='hidden' class='restrict-clear' name='" + parameterName + "' value='" + token + "'>");


// code below can be removed
/*
const parameterName = "${_csrf.parameterName}";
const token = "${_csrf.token}";

//append CSRF token on each form tag
$("form").prepend("<input type='hidden' class='restrict-clear' name='" + parameterName + "' value='" + token + "'>");

//add CSRF token on each ajax request
$.ajaxSetup({
    headers: {
        'X-CSRF-TOKEN': token
    }
});*/
