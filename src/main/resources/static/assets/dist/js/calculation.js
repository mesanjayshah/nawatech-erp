/**
 * fully developed for new accounting system and has used moment.js
 */

var ONE_TIME = "One Time";
var MONTHLY = "Monthly";
var QUARTERLY = "Quarterly";
var YEARLY = "Yearly";
var SEMESTER = "Semester";
var TRIMESTER = "Trimester";
var contextPath = window.location.pathname.split("/")[1];
var baseUrl = window.location.origin + "/" + contextPath + "/";
const MAX_AMOUNT = 9999999999;

$(".autofocus").focus(function () {
    $(this).select();
});

function getFinalAmount(previousTotal, amount, feeParticularId = null) {
    return sum(previousTotal, amount);
}

function roundOff(val) {
    return Number(Number($.trim(val)).toFixed(2));
}

function sum(a, b) {
    if (!a) a = 0;
    if (!b) b = 0;
    return roundOff(Number(a) + Number(b));
}

function sumOfThreeNumber(a, b, c) {
    return roundOff(Number(a) + Number(b) + Number(c));
}

function subtract(a, b) {
    if (!a) a = 0;
    if (!b) b = 0;
    return roundOff(Number(a) - Number(b));
}

function multiply(a, b) {
    if (!a) a = 0;
    if (!b) b = 0;
    return roundOff(Number(a) * Number(b));
}

function divide(a, b, enableRoundOff = true) {
    if (!a) a = 0;
    if (!b) b = 0;
    const value = Number(a) / Number(b);
    return enableRoundOff ? roundOff(value) : value;
}

function calculateTaxAmount(initialFee, taxPercentage) {
    return roundOff((Number(initialFee) * Number(taxPercentage)) / 100);
}

function getYearOrMonth(date, type = "year") {
    var index = type.toLowerCase() === "year" ? 0 : 1;
    return parseInt(date.split("-")[index]);
}

function calculateTotalYears(startDate, endDate) {
    var diff = (Number(calculateTotalMonths(startDate, endDate)) / 12);
    return Math.ceil(diff);
}

function calculateTotalMonths(startDate, endDate) {
    var totalMonths = parseInt(0);

    if (operationDateSetting.toLowerCase() === "np") {
        startDate = AD2BS(startDate);
        endDate = AD2BS(endDate);
    }

    var startYear = getYearOrMonth(startDate, "year");
    var endYear = getYearOrMonth(endDate, "year");
    var startMonth = getYearOrMonth(startDate, "month");
    var endMonth = getYearOrMonth(endDate, "month");

    if (parseInt(startYear) === parseInt(endYear)) {
        totalMonths += (parseInt(endMonth) - parseInt(startMonth) + 1);
    } else {
        for (var year = startYear; year <= endYear; year++) {
            if (year == startYear) totalMonths += (12 - parseInt(startMonth) + 1);
            else if (year == endYear) totalMonths += parseInt(endMonth);
            else totalMonths += 12;
        }
    }

    return totalMonths;
}

function calculateQuarters(totalMonths) {
    return Math.ceil(Number(totalMonths) / 3);//3 months = 1 quarter
}

function calculateSemesters(totalMonths) {
    return Math.ceil(Number(totalMonths) / 6);//6 months = 1 semester
}

function calculateTrimesters(totalMonths) {
    return Math.ceil(Number(totalMonths) / 4);//4 months = 1 trimester
}

function calculateFinalFee(initialFee, taxPercentage, type, startDate, endDate, instance) {
    var currentInstanceName = instance.attr("name");
    var rowInstance = instance.closest("tr");
    var timesInstance = rowInstance.find("input[name=times]");
    var rawTimes = timesInstance.val();
    var finalFee;
    var totalMonths = calculateTotalMonths(startDate, endDate);
    var initialFeeWithTax = sum(initialFee, (divide(multiply(initialFee, taxPercentage), 100)));
    let times = calculateTimes(type, totalMonths, startDate, endDate);

    if (rawTimes) {
        if (parseInt(rawTimes) > parseInt(times)) {
            timesInstance.val(times);
            if (currentInstanceName !== 'type')
                showAlert("Time(s) can not be greater than " + totalMonths + " according to current criteria.");
        }
    } else timesInstance.val(times);

    if (currentInstanceName === 'type') timesInstance.val(times);
    $(timesInstance).prop("readonly", type !== 'Monthly');

    if (timesInstance.val()) times = timesInstance.val();
    finalFee = initialFeeWithTax * times;

    return roundOff(finalFee);
}

function calculateTimes(type, totalMonths, startDate, endDate, maxIncludable = null) {
    let times = 0;
    switch (type) {
        case ONE_TIME:
            times = 1;
            break;

        case MONTHLY:
            times = totalMonths;
            //set max possibility to max includable defined by user on fee setup.
            if (maxIncludable != null && times > maxIncludable) times = maxIncludable;
            break;

        case QUARTERLY:
            times = calculateQuarters(totalMonths);
            break;

        case YEARLY:
            times = calculateTotalYears(startDate, endDate);
            break;

        case SEMESTER:
            times = calculateSemesters(totalMonths);
            break;

        case TRIMESTER:
            times = calculateTrimesters(totalMonths);
            break;

        default:
            times = 1;
            break;
    }
    return times;
}

function getFinalFee(initialFee, type, startDate, endDate) {
    const totalMonths = calculateTotalMonths(startDate, endDate);
    const times = calculateTimes(type, totalMonths, startDate, endDate);
    return multiply(initialFee, times);
}

function getPercentageAmount(amount, percentage) {
    return roundOff(divide(multiply(amount, percentage), 100));
}

function getPercentage(amount, totalAmount) {
    return roundOff((amount / totalAmount) * 100);
}

function getAmount(instance, allowNegative = false) {
    let amount = Number($.trim($(instance).val()));
    if ($(instance).val() === "") $(instance).val("");//to handle cases like 0-0 input

    const isNegativeOrNAN = (!allowNegative && amount < 0) || Number.isNaN(amount) || amount === undefined;
    if ((!allowNegative && !amount) || isNegativeOrNAN) {
        if (isNegativeOrNAN) {
            $(instance).val("");
            if ($(instance).hasClass("entryAmount")) $(instance).trigger('blur');
        }
        amount = 0;
    }

    if (amount > MAX_AMOUNT) {
        amount = MAX_AMOUNT;
        $(instance).val(amount);
        if ($(instance).hasClass("entryAmount")) $(instance).trigger('blur');
        showAlert(`Amount entry greater than <b>Rs. ${convertIntoNepaliSystem(amount)}</b> is restricted.`, INFO_ALERT);
    }

    return amount;
}

function setAmount(instance, amount, triggerChange = false) {
    $(instance).val((amount + "").replace(".00", ""));
    if (triggerChange) $(instance).trigger('change');
}

function getNumericValue(instance) {
    const value = Number($(instance).val());
    return value && !isNaN(value) ? value : 0;
}