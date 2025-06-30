var NEPALI_MONTH = ["Baishakh", "Jestha", "Ashadh", "Shrawan", "Bhadra", "Ashwin", "Kartik", "Mangsir", "Poush", "Magh", "Falgun", "Chaitra"];
var NEPALI_MONTHS_INC_ALL = ["All", "Baishakh", "Jestha", "Ashadh", "Shrawan", "Bhadra", "Ashwin", "Kartik", "Mangsir", "Poush", "Magh", "Falgun", "Chaitra"];
var ENGLISH_MONTHS = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
var ENGLISH_MONTHS_INC_ALL = ["All", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
let dayNepaliEnglish = ["आइत/SUN", "सोम/MON", "मङ्गल/TUE", "बुध/WED", "बिहि/THU", "शुक्र/FRI", "शनि/SAT"];
let days = ["Sun", "Mon", "Tues", "Wed", "Thu", "Fri", "Sat"];
const DAYS_FULL = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"],
    DAYS_SHORT = ["Sun", "Mon", "Tues", "Wed", "Thu", "Fri", "Sat"];

function getMonthName(month, operationDateSetting) {
    return operationDateSetting === "np" ? getNepaliMonthName(month) : getEnglishMonthName(month);
}

function getNepaliMonthName(month) {
    return NEPALI_MONTH[month - 1];
}

function getEnglishMonthName(month) {
    return ENGLISH_MONTHS[month - 1];
}

/*function getFinalDate(date, operationDateSetting, timeNotRequired) {
    if (!date) return "";

    if (operationDateSetting === 'np') {
        var splitted = date.split(" ");
        if (splitted.length == 2) {

            if (!timeNotRequired)
                return AD2BS(splitted[0]) + " " + splitted[1];
            else
                return AD2BS(splitted[0]);

        } else {
            return AD2BS(splitted[0]);
        }
    } else {
        if (timeNotRequired) date = date.split(" ")[0];
    }

    return date;
}*/

function getFinalDate(date, isTimeRequired = true, opd = null) {
    if (!date) return "";
    if ((opd ? opd : operationDateSetting) === 'np') {
        const splitted = date.split(" ");
        if (splitted.length === 2) {
            if (isTimeRequired) return AD2BS(splitted[0]) + " " + splitted[1];
            else return AD2BS(splitted[0]);
        } else return AD2BS(splitted[0]);
    } else {
        if (!isTimeRequired)
            date = date.split(" ")[0];
    }

    return date;
}

/**
 *
 * @param date
 * @param operationDateSetting
 * @param conversionRequired converts date according to operation date setting if true
 * @returns {string}
 */
function getFormatedStringDate(date, operationDateSetting, conversionRequired = false) {
    if (conversionRequired) date = getFinalDate(date, false);
    var splittedDate = date.split("-");
    var year = parseInt(splittedDate[0]);
    var day = parseInt(splittedDate[2]);
    var month = operationDateSetting === 'np' ? getNepaliMonthName(parseInt(splittedDate[1])) : getEnglishMonthName(parseInt(splittedDate[1]));
    return day + "<sup>" + ordinalSuffixOf(day) + "</sup>" + " " + month + " " + year;
}

function getFormatedStringDates(dateFirst, dateSecond, operationDateSetting, conversionRequired) {
    if (dateFirst === dateSecond) return getFormatedStringDate(dateFirst, operationDateSetting, conversionRequired);
    return getFormatedStringDate(dateFirst, operationDateSetting, conversionRequired) + " <b>to</b> " + getFormatedStringDate(dateSecond, operationDateSetting, conversionRequired);
}

function fromAndToDateValidation(instance) {
    var momentDate = momentNepaliToEnglish($(instance).val());
    var dateDifference = moment().diff(momentDate, 'years', true);
    if (dateDifference < 0) {
        alert('Date must be less than or equals to current date.');
        $(instance).val(AD2BS(moment().format('YYYY-MM-DD')));
    }

    var instanceName = $(instance).attr("name");
    var row = $(instance).closest(".row");
    var fromDate = row.find("input[name='start_date']").val();
    var toDate = row.find("input[name='end_date']").val();
    if ((instanceName === "start_date" && toDate != "") || (instanceName === "end_date" && fromDate != "")) {
        if (instanceName === "start_date") {
            fromDate = $(instance).val();
        } else {
            toDate = $(instance).val();
        }

        var momentFromDate = momentNepaliToEnglish(fromDate);
        var momentToDate = momentNepaliToEnglish(toDate);

        var dateDifference = momentToDate.diff(momentFromDate, 'day');
        if (dateDifference < 0) {
            alert('Invalid date. From Date can not be greater than To Date.');

            if (instanceName === "start_date") {
                $(instance).val(toDate);
            } else {
                $(instance).val(fromDate);
            }
        }
    }
}

function momentNepaliToEnglish(value) {
    return moment(new Date(BS2AD(value)).toISOString());
}

function valid() {
    $('.datepicker').each(function () {
        var checkValidDate;
        var isValid = "";
        if (operationDateSetting === 'np') {
            checkValidDate = isEqualOrBeforeCurrentDate(BS2AD($("#end-date").val()));

            if (!checkValidDate) {
                isValid = false;
                alert("From date must be less than or equals to current date.");
                $('#end-date').val(getCurrentDate(operationDateSetting));
            } else {
                $(".date-error").html("");
            }
        } else {
            checkValidDate = isEqualOrBeforeCurrentDate($("#end-date").val());
            if (!checkValidDate) {
                isValid = false;
                alert("From must be less than or equals to current date.");
                setDate(toDateInstance, operationDateSetting === 'np' ? AD2BS(fromDate) : fromDate, operationDateSetting);
            } else {
                //$(".date-error").html("");
            }
        }
    });
}

function getEngDate(date) {
    if (!date) return null;
    if (operationDateSetting === 'np')
        return BS2AD(date);
    return date;
}

function calculateTimeDifference(from, to) {
    if (!from || !to) return "";
    let startTime = moment(from, "HH:mm");
    let endTime = moment(to, "HH:mm");
    let duration = moment.duration(endTime.diff(startTime));
    let hours = parseInt(duration.asHours());
    let minutes = parseInt(duration.asMinutes()) % 60;

    if (hours < 0 || minutes < 0) {
        alert("Invalid time difference.");
        return "";
    }

    return hours + ":" + minutes;
}

function calculateDaysDifference(from, to) {
    if (!from || !to) return "";
    let startTime = moment(from, DATE_FORMAT);
    let endTime = moment(to, DATE_FORMAT);
    let duration = moment.duration(endTime.diff(startTime));
    return (duration.asDays() + 1);
}

function calculateWeeks(from, to, inclusive) {
    let diff = moment.duration(moment(to, DATE_FORMAT).diff(moment(from, DATE_FORMAT)))
    return Math.floor(diff.asWeeks()) + (inclusive ? (calculateDaysDifference(from, to) % 7 !== 0 ? 1 : 0) : 0);
}

function getDateDiffInYearMonthAndDay(from, to) {
    if (!from || !to) return "";
    let startDate = moment(from, DATE_FORMAT);
    let endDate = moment(to, DATE_FORMAT);

    let years = endDate.diff(startDate, 'year');
    startDate.add(years, 'years');

    let months = endDate.diff(startDate, 'months');
    startDate.add(months, 'months');

    let days = endDate.diff(startDate, 'days');

    return years + ' Yr(s) ' + months + ' Mo(s) ' + days + ' Day(s) ';
}

function getCurrentYear(operationDateSetting) {
    return getCurrentDate(operationDateSetting).split("-")[0];
}

/**
 * required to handle nepali date saved case on old version of software.
 * checks whether date input is already in nepali date format or not.
 * returns true if year >= 2074 and vice-versa.
 * @param date
 * @returns {boolean}
 */
function isAlreadyNepaliDate(date) {
    if (date) {
        let year = parseInt(date.split("-")[0]);
        return year >= 2074;
    }
    return false;
}

function isSameOrAfterTime(firstTime, secondTime) {
    firstTime = moment(firstTime, 'HH:mm');
    secondTime = moment(secondTime, 'HH:mm');
    return firstTime.isSameOrAfter(secondTime);
}

function isSameOrBeforeTime(firstTime, secondTime) {
    firstTime = moment(firstTime, 'HH:mm');
    secondTime = moment(secondTime, 'HH:mm');
    return firstTime.isSameOrBefore(secondTime);
}

/**
 *
 * @param operationDateSetting
 * @param year must lies between 2000 and 2090 (min and max year supported by plugin)
 * @param month must lies between 1 and 12
 * @returns {null|*}
 */
function getNumberOfBsDays(operationDateSetting, year, month) {
    if (year < 2000 || year > 2090) {
        console.error("Year out of range.");
        return null;
    }

    if (month < 1 || month > 12) {
        console.error("Month out of range.");
        return null;
    }

    if (operationDateSetting === 'np') {
        return (new NepaliDateConverter().bs[year][month - 1]);
    } else {
        return Math.round(((new Date(year, month)) - (new Date(year, month - 1))) / 86400000);
    }
}

/**
 *
 * @param instance
 */
function fromAndToDateValidationOnly(instance) {
    const isFromDate = $(instance).hasClass("fromDate");
    var row = $(instance).closest("tr").length ? $(instance).closest("tr") : $(instance).closest(".row");
    var fromDate = row.find(".fromDate").val();
    var toDate = row.find(".toDate").val();

    if (fromDate && toDate) {
        var momentFromDate = momentNepaliToEnglish(fromDate);
        var momentToDate = momentNepaliToEnglish(toDate);

        var dateDifference = momentToDate.diff(momentFromDate, 'day');
        if (dateDifference < 0) {
            showAlert('Invalid date. From Date can not be greater than To Date.', INFO_ALERT);
            $(instance).val(isFromDate ? toDate : fromDate);
        }
    }
}

/**
 *
 * @param year
 * @param from
 * @param to
 * @returns {string|*[]}
 */
function getMonthDayRangeFromDateRange(year, from, to) {
    if (!from || !to) return "";

    let splitDate = getFinalDate(from).split('-');
    let splitEndDate = getFinalDate(to).split('-');

    let startMonth = splitDate[1];
    let startDay = splitDate[2];
    let endMonth = splitEndDate[1];
    let endDay = splitEndDate[2];

    if (splitEndDate[0] > splitDate[0]) {
        if (parseInt(year) === parseInt(splitDate[0])) {
            endMonth = 12;
            endDay = getNumberOfBsDays(operationDateSetting, year, 12);
        } else {
            startMonth = 1;
            startDay = 1;
        }
    }

    let finalStartMonthDay = startMonth + "-" + startDay;
    let finalEndMonthDay = endMonth + "-" + endDay;

    return [finalStartMonthDay, finalEndMonthDay];

}

/**
 *
 * @param date
 * @param operationDateSetting
 * @returns {*}
 */
function getDay(date, operationDateSetting) {

    let days = ["Sun", "Mon", "Tues", "Wed", "Thu", "Fri", "Sat"];
    let dayIndex = '';

    if (operationDateSetting === 'np') {
        dayIndex = new Date(getEngDate(date));
    } else {
        dayIndex = new Date(date);
    }

    return days[dayIndex.getDay()];
}

function getStartAndEndDateByYear(year, operationDateSetting) {
    let lastDayOfYear = getNumberOfBsDays(operationDateSetting, year, 12);
    let startDate = year + "-01-01";
    let endDate = year + "-12-" + lastDayOfYear;
    return {startDate, endDate};
}

function getStartAndEndDateByYearInAD(year, operationDateSetting) {
    let lastDayOfYear = getNumberOfBsDays(operationDateSetting, year, 12);
    let startDate = year + "-01-01";
    let endDate = year + "-12-" + lastDayOfYear;
    if (operationDateSetting === "np") {
        startDate = BS2AD(startDate);
        endDate = BS2AD(endDate);
    }
    return {startDate, endDate};
}

function getEnglishDateRangeByNepaliYear(year) {
    let startAndEndDate = getStartAndEndDateByYear(year, "np");
    return {startDate: startAndEndDate.startDate, endDate: startAndEndDate.endDate};
}

var getDaysArray = function (year, month) {
    var monthIndex = month - 1;
    var names = ['sun', 'mon', 'tue', 'wed', 'thu', 'fri', 'sat'];
    var date = new Date(year, monthIndex, 1);
    var result = [];
    while (date.getMonth() === monthIndex) {
        result.push(names[date.getDay()]);
        date.setDate(date.getDate() + 1);
    }
    return result;
};

/**
 *
 * @returns {[]} > list of days of current week.
 */
function getCurrentWeekDays() {
    let currentDate = moment();
    let weekStart = currentDate.clone().startOf('week');
    // use isoWeek instead of week for monday as starting week
    let weekEnd = currentDate.clone().endOf('week');
    let days = [];
    for (let i = 0; i <= 6; i++) {
        days.push(moment(weekStart).add(i, 'days').format("MMMM Do,dddd"));
    }
    return days;
}

function getFirstAndLastDateOfCurrentWeek() {
    let current = new Date();     // get current date
    let weekStart = current.getDate() - current.getDay();  // current.getDay() +1; If start day is monday
    let weekend = weekStart + 6;       // end day is the first day + 6
    let startWeekDate = new Date(current.setDate(weekStart));
    let endWeekDate = new Date(current.setDate(weekend));

    return {startWeekDate, endWeekDate};
}

function getDaysByDateRange(startFrom, endTo) {
    let from = new Date(getEngDate(startFrom).replace(/-/g, ','));
    let to = new Date(getEngDate(endTo).replace(/-/g, ','));
    let d = new Date(from),
        a = [],
        y = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
    while (d < to) {
        a.push(y[d.getDay()]);
        d.setDate(d.getDate() + 1);
    }
    if (d.getDay() === to.getDay()) // include last day
        a.push(y[d.getDay()]);
    return a;
}

function getDates(startFrom, endTo) {
    let from = new Date(getEngDate(startFrom).replace(/-/g, ','));
    let to = new Date(getEngDate(endTo).replace(/-/g, ','));
    var dateArray = [];
    var currentDate = moment(from);
    var stopDate = moment(to);
    while (currentDate <= stopDate) {
        dateArray.push(moment(currentDate).format('YYYY-MM-DD'));
        currentDate = moment(currentDate).add(1, 'days');
    }
    return dateArray;
}

function getFirstAndLastDateFromCurrentYear() {
    let year = getCurrentYear(operationDateSetting);
    let startDate = year + "-01-01";
    let endDate = year + "-12-" + getNumberOfBsDays(operationDateSetting, year, 12);
    return [startDate, endDate];
}

function validateDate(instance) {
    let date = $(instance).val();
    if (!date) return;

    let validate = JSON.parse($(instance).attr("validate"));
    if (!validate.canExceedCurrentDate) {
        let engDate = getEngDate(date);
        const currentDate = getCurrentDate("en");
        if (!isEqualOrBefore(engDate, currentDate)) {
            setDate($(instance), getFinalDate(currentDate), operationDateSetting);
            showAlert("Date must be less than or equals to current date.", false);
        }
    }

    const callback = validate.callback;
    if (callback) eval(callback)();
}

function getTimeDifference(startTime, endTime) {
    startTime = moment(startTime, "HH:mm");
    endTime = moment(endTime, "HH:mm");
    const duration = moment.duration(endTime.diff(startTime));
    return parseInt(duration.asHours()) + " Hr(s) " + parseInt(duration.asMinutes()) % 60 + " Min(s)";
}

function populateDaysDifference(fromDayInstance, toDayInstance, differenceInstance, operationDateSetting) {
    const startDay = convertToAD($(fromDayInstance).val(), operationDateSetting);
    const endDay = convertToAD($(toDayInstance).val(), operationDateSetting);

    if (startDay && !endDay) {
        alert("Please pick start/initial Day first!");
        $(toDayInstance).val("");
        return;
    }

    const difference = calculateDaysDifference(startDay, endDay);
    $(differenceInstance).val(difference).trigger('change');
    if (!difference) $(toDayInstance).val("");
}

function populateDateRangeTitle(title, fromDate, toDate, displayInstance, asOfTitle = false) {
    let fromDateEng = fromDate, toDateEng = toDate;
    if (operationDateSetting === 'np') {
        fromDateEng = BS2AD(fromDate);
        toDateEng = BS2AD(toDate);
    }
    let isSame = isSameDate(fromDateEng, toDateEng);
    let finalDate = "";
    if (asOfTitle) {
        finalDate = title + " as " + (isSame ? "on" : "of") + ": <strong>" + (isSame ? getFormatedStringDate(fromDate, operationDateSetting) : getFormatedStringDate(fromDate, operationDateSetting) + " to " + getFormatedStringDate(toDate, operationDateSetting)) + "</strong>";
    } else {
        finalDate = "Date: <strong>" + (isSame ? getFormatedStringDate(fromDate, operationDateSetting) : getFormatedStringDate(fromDate, operationDateSetting) + " - " + getFormatedStringDate(toDate, operationDateSetting)) + "</strong>";
    }
    $(displayInstance).html(finalDate);
}

function getMonthsArray(operationDateSetting, includeAll) {
    let months;
    if (operationDateSetting === "np") {
        months = includeAll ? NEPALI_MONTHS_INC_ALL : NEPALI_MONTH;
    } else {
        months = includeAll ? ENGLISH_MONTHS_INC_ALL : ENGLISH_MONTHS;
    }
    return months;
}

function oppositeDateSetting(opds) {
    return opds === "np" ? "en" : 'np';
}

function getCalendarDate(date, operationDateSetting) {
    if (!date) return "";
    if (operationDateSetting === 'np') {
        let e = new NepaliDateConverter
        return e.ad2bs(getNepaliFormat(date))
    } else {
        let e = new NepaliDateConverter
        return e.bs2ad(getNepaliFormat(date))
    }
}

function getDateRangeByYearAndMonth(year, month, operationDateSetting) {
    const lastDay = getNumberOfBsDays(operationDateSetting, year, month);
    const dateInitials = year + "-" + (month < 10 ? "0" + month : month) + "-";
    const fromDate = dateInitials + "01";
    const toDate = dateInitials + lastDay;
    return {fromDate, toDate};
}