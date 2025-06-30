function convertIntoNepaliSystem(x, showZeroValue = true, zeroValueReplacement = "") {
    if (!x) return showZeroValue ? "0.00" : zeroValueReplacement;

    if (x == 0 && !showZeroValue) return zeroValueReplacement;

    var isNegative = false;
    if (x < 0) isNegative = true;
    x = Math.abs(x).toString();
    var afterPoint = '';

    if (x.indexOf('.') > 0)
        afterPoint = x.substring(x.indexOf('.'), x.length);

    x = Math.floor(x);
    x = x.toString();

    var lastThree = x.substring(x.length - 3);
    var otherNumbers = x.substring(0, x.length - 3);

    if (otherNumbers !== '') lastThree = ',' + lastThree;

    var res = otherNumbers.replace(/\B(?=(\d{2})+(?!\d))/g, ",") + lastThree + afterPoint;
    var output = isNegative ? '-' + res : res;
    if (output.indexOf(".") < 0) output += ".00";

    //append 0 if output is something like 10.7. Final format must be XXXXX.XX at the end.
    let splittedOutput = output.split("."),
        countAfterDecimal = splittedOutput[1].length;
    if (countAfterDecimal < 2) output += "0";

    return output;
}

//toggle nepali number system and entry amount display
$(function () {
    $(document).on("change", ".entryAmount[readonly]", function () {
        toggleEntryAndDisplayAmount(this, 'entryAmount');
    });
    initNepaliNumberFormat();
});

function initNepaliNumberFormat() {
    $(".entryAmount").each(function () {
        let val = $(this).val();
        let isReadOnly = $(this).is("[readonly]");
        let closestInstance = $(this).closest("td").length > 0 ? $(this).closest("td") : $(this).closest("div");
        let errorClassInstance = closestInstance.find(".error");
        let extraClass = $(this).hasClass("text-danger") ? 'text-danger' : $(this).hasClass("text-success") ? 'text-success' : '';
        let appendableHtml = `<input type="text" class="form-control d-none ${extraClass} ${isReadOnly ? 'alwaysDisplay' : ''} amountDisplay text-right bg-white" value='${convertIntoNepaliSystem(val, false)}' readonly>`;
        closestInstance.prepend(appendableHtml);
        if (isReadOnly) {
            let closestInstance = $(this).closest("td").length > 0 ? "td" : "div";
            if (closestInstance) $(this).addClass("d-none").closest(closestInstance).find(".amountDisplay").removeClass("d-none");
        }
    });
    $(".entryAmount.inactive").trigger("blur");
}

// $(document).on("blur", ".entryAmount:not([readonly])", function () {
$(document).on("blur", ".entryAmount", function () {
    if ($(this).is("[readonly]") && !$(this).hasClass("ignore-readonly")) return;
    toggleEntryAndDisplayAmount(this, 'entryAmount');
});

$(document).on("focus", ".amountDisplay:not(.alwaysDisplay)", function () {
    toggleEntryAndDisplayAmount(this, 'displayAmount');
});

function toggleEntryAndDisplayAmount(instance, type) {
    const showZeroValue = $(instance).closest("table#tableVoucherEntry").length;
    let inputInstance = $(instance).closest("td").length > 0 ? $(instance).closest("td").find(".entryAmount") : $(instance).closest("div").find(".entryAmount");
    let showZeroValueIfZero = $(instance).closest("table.showZeroValueIfZero").length;
    let inputVal = $.trim(inputInstance.val());
    if (type === 'displayAmount') {
        $(instance).addClass('d-none');
        if (inputVal == 0 && !showZeroValueIfZero) inputInstance.val("");
        inputInstance.removeClass('d-none').removeAttr("readonly").focus();
    } else {
        if (inputVal == '' && !showZeroValueIfZero) inputInstance.val(0);
        let amountInstance = $(instance).closest("td").length > 0 ? $(instance).closest("td").find(".amountDisplay") : $(instance).closest("div").find(".amountDisplay");
        if (showZeroValueIfZero && inputVal !== "") {
            amountInstance.val(convertIntoNepaliSystem($(instance).val(), true)).removeClass('d-none');
        } else {
            amountInstance.val(convertIntoNepaliSystem($(instance).val(), showZeroValue)).removeClass('d-none');
        }
        $(instance).addClass('d-none');
    }
}

// toggle nepali number system and entry amount display end