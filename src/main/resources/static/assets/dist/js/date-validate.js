$(document).on("change", ".validate-date", function () {
    validateDate($(this).val(), $(this));
});

$(".validate-date").bind("change", function () {
    validateDate($(this).val(), $(this));
});

function dateToDisableAfter() {
    var date = AD2BS(getCurrentDate());
    var splittedDate = date.split("-");
    return splittedDate[1] + "/" + (parseInt(splittedDate[2]) - parseInt(1)) + "/" + splittedDate[0];
}

function validateDate(date, instance) {
    date = $.trim(date);
    if (Date.parse(date)) {
        $(instance).val(date);
        return true;
    } else {
        alert("Invalid date");
        $(instance).val("");
        return false;
    }

}

function isValidDate(date) {
    return !!Date.parse(date);
}

function isFirstDateGreater(date1, date2) {
    date1 = parseDateString(date1);
    date2 = parseDateString(date2);
    return Date.parse(date1) > Date.parse(date2);
}

function isFirstDateGreaterOrEqualToSecond(date, currentDate) {
    date = parseDateString(date);
    currentDate = parseDateString(currentDate);
    return Date.parse(date) >= Date.parse(currentDate);
}

function parseDateString(rawDate) {
    const splitted = rawDate.split("-");
    return parseInt(splitted[0]) + "-" + parseInt(splitted[1]) + "-" + parseInt(splitted[2]);
}

function isFirstDateLessOrEqualToSecond(firstDate, secondDate) {
    firstDate = parseDateString(firstDate);
    secondDate = parseDateString(secondDate);
    return Date.parse(firstDate) <= Date.parse(secondDate);
}

function validateBirthDate(instance, currentDate) {
    let date = $.trim(instance.val());
    if (date !== "") {
        if (!isFirstDateGreater(date, currentDate)) {
            instance.val(date);
        } else {
            showAlert("Birth date can not be greater than current date.", INFO_ALERT);
            instance.val("");
            $("#englishDate").val("");
        }
    }
}

function validateJoinedAndLeftDate(joinedDateInstance, leftDateInstance, currentDate) {
    let joinedDate = $.trim(joinedDateInstance.val());
    let leftDate = $.trim(leftDateInstance.val());

    if (!isFirstDateLessOrEqualToSecond(joinedDate, currentDate)) {
        joinedDateInstance.val(currentDate);
        showAlert("Joined date must be less than or equals to current date", INFO_ALERT);
    }

    if (leftDate) {
        if (!isFirstDateGreater(leftDate, joinedDate)) {
            showAlert("Left date must be greater than joined date.", INFO_ALERT);
            leftDateInstance.val("");
        } else {
            if (!isFirstDateLessOrEqualToSecond(leftDate, currentDate)) {
                showAlert("Left date must be less than or equal to current date.", INFO_ALERT);
                leftDateInstance.val("");
            }
        }
    }
}

function isValidStartAndEndDate(startDateInstance, endDateInstance, operationDateSetting) {
    var startDate = $.trim(startDateInstance.val());
    var endDate = $.trim(endDateInstance.val());

    if (startDate === '') {
        var validationVal = $(startDateInstance).attr("validation-val");
        showAlert("Please pick " + (validationVal ? validationVal : "start") + " date first.", INFO_ALERT);
        $(endDateInstance).val("");
        return false;
    }

    if (endDate === '') {
        //alert("Please pick end date first.");
        return false;
    }

    if (operationDateSetting === 'np') {
        startDate = BS2AD(startDate);
        endDate = BS2AD(endDate);
    }
    var startDateMoment = moment(startDate);
    var endDateMoment = moment(endDate);
    if (!endDateMoment.isSameOrAfter(startDateMoment)) {
        var startDateValidationVal = $(startDateInstance).attr("validation-val");
        var endDateValidationVal = $(endDateInstance).attr("validation-val");
        if (!startDateValidationVal) startDateValidationVal = "start";
        if (!endDateValidationVal) endDateValidationVal = "end";

        alert(ucFirst(endDateValidationVal) + " date must be greater than or equals to " + startDateValidationVal + " date.");
        setDate(endDateInstance, operationDateSetting === 'np' ? AD2BS(startDate) : startDate, operationDateSetting);
        return false;
    }
    return true;
}


function isEqualOrAfter(dateFirst, dateSecond) {
    return moment(dateFirst).isSameOrAfter(moment(dateSecond));
}

function isSameDate(dateFirst, dateSecond) {
    return moment(dateFirst).isSame(moment(dateSecond));
}

function isBetweenDates(date, dates) {
    return isEqualOrAfter(date, dates[0]) && isEqualOrBefore(date, dates[1]);
}

function isEqualOrBefore(dateFirst, dateSecond) {
    return moment(dateFirst).isSameOrBefore(moment(dateSecond));
}

function isTimeEqualOrBefore(timeFirst, timeSecond) {
    return moment(timeFirst, TIME_FORMAT).isSameOrBefore(moment(timeSecond, TIME_FORMAT));
}

function isEqualOrAfterCurrentDate(date) {
    return moment(date).isSameOrAfter(getCurrentDate());
}

function checkValidStartAndEndDateForFiscalYear(startDateInstance, endDateInstance, operationDateSetting) {
    var startDate = $.trim(startDateInstance.val());
    var endDate = $.trim(endDateInstance.val());

    if (startDate === '') {
        showAlert("Please pick start date first.", INFO_ALERT);
        return;
    }

    if (endDate === '') return;

    if (operationDateSetting === 'np') {
        startDate = BS2AD(startDate);
        endDate = BS2AD(endDate);
    }

    var startDateMoment = moment(startDate);
    var endDateMoment = moment(endDate);
    if (!endDateMoment.isAfter(startDateMoment)) {
        showAlert("End date must be greater than start date.", INFO_ALERT);
        setDate(endDateInstance, operationDateSetting === 'np' ? "" : startDate, operationDateSetting);
        return false;
    }
    return true;
}

let CURRENT_DATE = null, CURRENT_TIME = null;

/**
 * requires moment.js and nepali-date-picker plugins
 * @param operationDateSetting
 * @returns
 */
function getCurrentDate(operationDateSetting = 'en') {
    if (operationDateSetting === "np")
        return AD2BS(CURRENT_DATE_EN);
    return CURRENT_DATE_EN;
}

function isEqualOrBeforeCurrentDate(date) {
    return moment(date).isSameOrBefore(moment());
}

function isBeforeCurrentDate(date, operationDateSetting) {
    if (operationDateSetting === 'np')
        date = BS2AD(date);
    return moment(date).isBefore(getCurrentDate('en'));
}

function convertToAD(date, operationDateSetting) {
    if (operationDateSetting === 'np')
        return BS2AD(date);
    return date;
}

function validateToBeEqualOrAfterCurrentDate(instance, operationDateSetting) {
    var date = $(instance).val();
    if (date) {
        date = convertToAD(date, operationDateSetting);
        if (!isEqualOrAfterCurrentDate(date)) {
            showAlert("Date must be greater than or equals to current date.", INFO_ALERT);
            setDate(instance, getCurrentDate(operationDateSetting), operationDateSetting);
        }
    }
}

function isValidFromAndToDateOfFilter(fromDateInstance, toDateInstance, operationDateSetting) {
    var fromDate = $.trim(fromDateInstance.val());
    var toDate = $.trim(toDateInstance.val());

    if (fromDate === '') return false;
    if (toDate === '') return false;

    if (operationDateSetting === 'np') {
        fromDate = BS2AD(fromDate);
        toDate = BS2AD(toDate);
    }

    if (!isFirstDateLessOrEqualToSecond(fromDate, toDate)) {
        showAlert("From date must be smaller than or equals to To date.", INFO_ALERT);
        setDate(fromDateInstance, getCurrentDate(operationDateSetting), operationDateSetting);
        setDate(toDateInstance, getCurrentDate(operationDateSetting), operationDateSetting);
        return false;
    }
    if (!isEqualOrBeforeCurrentDate(toDate)) {
        showAlert("To date should not be greater than current date.", INFO_ALERT);
        setDate(toDateInstance, getCurrentDate(operationDateSetting), operationDateSetting);

        return false;
    }

    return true;
}

function isEqualORAfterCurrentDate(instance, dateMsg, operationDateSetting) {
    let date = $(instance).val();
    if (date) {
        date = convertToAD(date, operationDateSetting);
        if (!isEqualOrAfterCurrentDate(date)) {
            showAlert("" + dateMsg + " Date must be after or equals to current date.", INFO_ALERT);
            setDate(instance, getCurrentDate(operationDateSetting), operationDateSetting);
        }
    }
}

function isEqualORBeforeCurrentDate(instance, dateMsg, operationDateSetting) {
    let date = $(instance).val();
    if (date) {
        date = convertToAD(date, operationDateSetting);
        if (!isEqualOrBeforeCurrentDate(date)) {
            let msg = dateMsg + " date must be less than or equals to current date.";
            showAlert(msg, INFO_ALERT);
            setDate(instance, getCurrentDate(operationDateSetting), operationDateSetting);
        }
    }
}

/** latest functions **/
function isSameOrAfterCurrentDate(instance) {
    let val = $.trim($(instance).val());
    if (!val) return;
    if (operationDateSetting === "np") val = BS2AD(val);
    let currentDate = getCurrentDate('en');
    if (!isEqualOrAfter(val, currentDate)) {
        showAlert("Date must be greater than or equals to current date.", INFO_ALERT);
        setDate($(instance), operationDateSetting === "np" ? AD2BS(currentDate) : currentDate, operationDateSetting);
    }
}

function isSameOrBeforeCurrentDate(instance) {
    let val = $.trim($(instance).val());
    if (!val) return;
    if (operationDateSetting === "np") val = BS2AD(val);
    let currentDate = getCurrentDate('en');
    if (!isEqualOrBefore(val, currentDate)) {
        showAlert("Date must be less than or equals to current date.", INFO_ALERT);
        setDate($(instance), operationDateSetting === "np" ? AD2BS(currentDate) : currentDate, operationDateSetting);
    }

    //restrict date to be greater than max date
    if ($(instance).hasClass("max-date")) {
        const maxDateEng = $(instance).attr("data-max-date");
        if (maxDateEng && !isEqualOrBefore(val, maxDateEng)) {
            const finalDate = getFinalDate(maxDateEng);
            showAlert(`Date must be less than or equals to ${finalDate}.`, INFO_ALERT);
            setDate($(instance), finalDate, operationDateSetting);
        }
    }
}

function validateFromAndToDate(canExceedCurrentDate = false, fromDateInstance = ".fromDate",
                               toDateInstance = ".toDate", fromOrToDateInstance = null) {
    if (typeof canExceedCurrentDate == 'object') canExceedCurrentDate = false;

    if (fromOrToDateInstance) {
        const rowInstance = $(fromOrToDateInstance).closest("tr");
        if (!fromDateInstance) fromDateInstance = rowInstance.find(".fromDate");
        if (!toDateInstance) toDateInstance = rowInstance.find(".toDate");
    } else {
        fromDateInstance = $(fromDateInstance);
        toDateInstance = $(toDateInstance);
    }

    let startMsg = ucFirst($.trim(fromDateInstance.attr('data-valid')));
    let endMsg = ucFirst($.trim(toDateInstance.attr('data-valid')));
    let fromDate = $.trim(fromDateInstance.val());
    let toDate = $.trim(toDateInstance.val());

    if (!startMsg) startMsg = "From";
    if (!endMsg) endMsg = "To";

    //restrict to pick end date before start date
    if (!fromDate && toDate) {
        setDate(toDateInstance, "", operationDateSetting);
        showAlert(`Please pick ${startMsg.toLowerCase()} date first.`, INFO_ALERT);
        return false;
    }

    if (operationDateSetting === 'np') {
        if (fromDate) fromDate = BS2AD(fromDate);
        if (toDate) toDate = BS2AD(toDate);
    }

    let isStartDateInvalid = false, isEndDateInvalid = false;
    if (!canExceedCurrentDate) {
        let currentDate = getCurrentDate(operationDateSetting), currentDateEng = getCurrentDate('en');
        if (!isEqualOrBefore(fromDate, currentDateEng)) {
            setDate(fromDateInstance, currentDate, operationDateSetting);
            fromDate = currentDateEng;
            isStartDateInvalid = true;
        }

        if (!isEqualOrBefore(toDate, currentDateEng)) {
            setDate(toDateInstance, currentDate, operationDateSetting);
            toDate = currentDateEng;
            isEndDateInvalid = true;
        }

        if (isStartDateInvalid || isEndDateInvalid) {
            if (isStartDateInvalid && isEndDateInvalid) {
                showAlert(`${startMsg} date and ${endMsg} must be less than or equals to current date.`, INFO_ALERT);
            } else {
                showAlert(`${isStartDateInvalid ? startMsg : endMsg} date must be less than or equals to current date.`, INFO_ALERT);
            }
        }
    }


    if (fromDate && toDate) {
        if (!isEqualOrBefore(fromDate, toDate)) {
            showAlert(endMsg + " date must be greater than or equals to " + startMsg + " date.", INFO_ALERT);
            setDate(toDateInstance, operationDateSetting === "np" ? AD2BS(fromDate) : fromDate, operationDateSetting);
        }
    }

    return true;
}

function validateFromAndToTime(canExceedCurrentTime = false, fromTimeInstance = ".start-time", toTimeInstance = ".end-time") {
    if (typeof canExceedCurrentTime == 'object') canExceedCurrentTime = false;
    fromTimeInstance = $(fromTimeInstance);
    toTimeInstance = $(toTimeInstance);

    let startMsg = ucFirst($.trim(fromTimeInstance.attr('data-valid')));
    let endMsg = ucFirst($.trim(toTimeInstance.attr('data-valid')));
    let fromTime = $.trim(fromTimeInstance.val());
    let toTime = $.trim(toTimeInstance.val());

    if (!startMsg) startMsg = "Starting";
    if (!endMsg) endMsg = "Ending";

    //restrict to pick end time before start time
    if (!fromTime && toTime) {
        toTimeInstance.val("");
        showAlert(`Please pick ${startMsg.toLowerCase()} time first.`, INFO_ALERT);
        return false;
    }

    let isStartTimeInvalid = false, isEndTimeInvalid = false;
    if (!canExceedCurrentTime) {
        let currentTime = moment().format(TIME_FORMAT_SHORT);
        if (fromTime) {
            if (!isTimeEqualOrBefore(fromTime, currentTime)) {
                fromTimeInstance.val(currentTime);
                fromTime = currentTime;
                isStartTimeInvalid = true;
            }
        }

        if (toTime) {
            if (!isTimeEqualOrBefore(toTime, currentTime)) {
                toTimeInstance.val(currentTime);
                toTime = currentTime;
                isEndTimeInvalid = true;
            }
        }

        if (isStartTimeInvalid || isEndTimeInvalid) {
            if (isStartTimeInvalid && isEndTimeInvalid) {
                showAlert(`${startMsg} time and ${endMsg} must be less than or equals to current time.`, INFO_ALERT);
            } else {
                showAlert(`${isStartTimeInvalid ? startMsg : endMsg} time must be less than or equals to current time.`, INFO_ALERT);
            }
        }
    }

    if (fromTime && toTime) {
        if (!isTimeEqualOrBefore(fromTime, toTime)) {
            showAlert(endMsg + " time must be greater than or equals to " + startMsg + " time.", INFO_ALERT);
            toTimeInstance.val(fromTime);
        }
    }

    return true;
}