package io.nawatech.erp.utils.dateutil;

import io.nawatech.erp.utils.ArraysUtil;
import io.nawatech.erp.utils.Helper;
import io.nawatech.erp.utils.SessionHelper;
import io.nawatech.erp.utils.Strings;
import io.nawatech.erp.utils.constants.Constants;
import io.nawatech.erp.utils.neptoengdate.NepToEngDateConverter;
import io.nawatech.erp.utils.payload.Response;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class DateUtils {

    public static Date getDateAfter(int days) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

    public static Date getDateAfter(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

    public static String getDateAfter(int days, String operationDateSetting) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, days);
        return getFinalDate(new SimpleDateFormat(Strings.DATE_PATTERN).format(c.getTime()), operationDateSetting, false, false);
    }

    public static String getCurrentDate(String operationDateSetting, boolean includeTime) {
        String date;

        if (operationDateSetting.equalsIgnoreCase("np")) {
            if (includeTime) date = Helper.getCurrentNepaliDateTime();
            else date = Helper.getCurrentNepaliDateOnly();
        } else {
            if (includeTime) date = Helper.getCurrentDate();
            else date = Helper.getCurrentEnglishDateOnly();
        }

        return date;
    }

    public static boolean isBeforeOrEqual(Date firstDate, Date secondDate) {
        LocalDate firstLocalDate = new LocalDate(firstDate);
        LocalDate secondLocalDate = new LocalDate(secondDate);
        return firstLocalDate.isBefore(secondLocalDate) || firstLocalDate.isEqual(secondLocalDate);
    }

    public static boolean isEqual(Date firstDate, Date secondDate) {
        LocalDate firstLocalDate = new LocalDate(firstDate);
        LocalDate secondLocalDate = new LocalDate(secondDate);
        return firstLocalDate.isEqual(secondLocalDate);
    }

    public static boolean isBefore(Date firstDate, Date secondDate) {
        LocalDate firstLocalDate = new LocalDate(firstDate);
        LocalDate secondLocalDate = new LocalDate(secondDate);
        return firstLocalDate.isBefore(secondLocalDate);
    }

    public static boolean isAfter(Date firstDate, Date secondDate) {
        LocalDate firstLocalDate = new LocalDate(firstDate);
        LocalDate secondLocalDate = new LocalDate(secondDate);
        return firstLocalDate.isAfter(secondLocalDate);
    }

    public static boolean isEqualOrAfterCurrentDate(Date date) {
        LocalDate inputDate = new LocalDate(date);
        LocalDate currentDate = new LocalDate(new Date());
        return inputDate.isEqual(currentDate) || inputDate.isAfter(currentDate);
    }

    public static boolean isSameOrAfter(Date firstDate, Date secondDate) {
        LocalDate firstLocalDate = new LocalDate(firstDate);
        LocalDate secondLocalDate = new LocalDate(secondDate);
        return firstLocalDate.isEqual(secondLocalDate) || firstLocalDate.isAfter(secondLocalDate);
    }

    public static int getCurrentYear(String operationDateSetting) {
//        return operationDateSetting.equalsIgnoreCase("np") ? Helper.getCurrentNepaliYear() : Helper.getCurrentEnglishYear();
        return Helper.getCurrentEnglishYear();
    }

    public static int getCurrentMonth(String operationDateSetting) {
        return operationDateSetting.equalsIgnoreCase("np") ? Helper.getCurrentNepaliMonth() : Helper.getCurrentEnglishMonth();
    }

    public static String getCurrentMonthStartDate(String operationDateSetting) {
        int currentMonth = getCurrentMonth(operationDateSetting);
        return getCurrentYear(operationDateSetting) + "-" + (currentMonth < 10 ? "0" : "") + currentMonth + "-01";
    }

    public static String getCurrentMonthString(String operationDateSetting) {
        return getStringMonth(getCurrentMonth(operationDateSetting), operationDateSetting);
    }

    public static int getMinYear(String operationDateSetting) {
        return operationDateSetting.equalsIgnoreCase("np") ? 2060 : 2015;
    }

    public static int getMaxYear(String operationDateSetting) {
        return operationDateSetting.equalsIgnoreCase("np") ? Helper.getCurrentNepaliYear() + 2 : Helper.getCurrentEnglishYear() + 2;
    }

    public static int getYearOrMonth(String date, String type) {
        int index = type.equalsIgnoreCase("year") ? 0 : 1;
        return Integer.parseInt(date.split("-")[index]);
    }

    public static int[] getYearAndMonth(String date) {
        if (Helper.isNullOrEmpty(date))
            return new int[]{0, 0};

        String[] splitted = date.split("-");
        return new int[]{Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1])};
    }

    public static int calculateTotalYears(Date startDate, Date endDate, String operationDateSetting, boolean useCeil) {
        float diff = ((float) calculateTotalMonths(startDate, endDate, operationDateSetting) / 12);
        return useCeil ? (int) Math.ceil(diff) : (int) Math.floor(diff);
    }

    public static int calculateTotalMonths(Date startDate, Date endDate, String operationDateSetting) {
        int totalMonths = 0;

        String formatedStartDate = formatDate(startDate, false);
        String formatedEndDate = formatDate(endDate, false);
        if (operationDateSetting.equalsIgnoreCase("np")) {
            formatedStartDate = EngToNepaliDateConverter.convertEngToNepDateOnly(formatedStartDate);
            formatedEndDate = EngToNepaliDateConverter.convertEngToNepDateOnly(formatedEndDate);
        }

        int startYear = getYearOrMonth(formatedStartDate, "year");
        int endYear = getYearOrMonth(formatedEndDate, "year");
        int startMonth = getYearOrMonth(formatedStartDate, "month");
        int endMonth = getYearOrMonth(formatedEndDate, "month");

        if (startYear == endYear) {
            totalMonths += (endMonth - startMonth + 1);
        } else {
            for (int year = startYear; year <= endYear; year++) {
                if (year == startYear) totalMonths += (12 - startMonth + 1);
                else if (year == endYear) totalMonths += endMonth;
                else totalMonths += 12;
            }
        }

        return totalMonths;
    }

    public static int calculateQuarters(int totalMonths, boolean useCeil) {
        return useCeil ? (int) Math.ceil((float) totalMonths / 3) : (int) Math.floor((float) totalMonths / 3);//3 months = 1 quarter
    }

    public static int calculateSemesters(int totalMonths, boolean useCeil) {
        return useCeil ? (int) Math.ceil((float) totalMonths / 6) : (int) Math.floor((float) totalMonths / 6);//6 months = 1 semester
    }

    public static int calculateTrimesters(int totalMonths, boolean useCeil) {
        return useCeil ? (int) Math.ceil((float) totalMonths / 4) : (int) Math.floor((float) totalMonths / 4);//4 months = 1 trimester
    }

    public static Date currentDateTime() {
        return new Date();
    }

    public static Date parseDate(String date, boolean includeTime) {
        if (date == null || date.isEmpty()) return null;

        String format = "yyyy-MM-dd";
        boolean hasTime = date.split(" ").length > 1;
        if (includeTime && hasTime) format = "yyyy-MM-dd HH:mm:ss";

        DateFormat df = new SimpleDateFormat(format);
        try {
            return df.parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public static Date parseTime(String time) {
        if (time == null || time.trim().isEmpty()) return null;
        DateFormat df = new SimpleDateFormat("HH:mm");
        try {
            return df.parse(time);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public static String formatDate(Date date, boolean hasTime) {
        if (date == null) return null;
        DateFormat df = new SimpleDateFormat(hasTime ? Strings.DATE_TIME_PATTERN : Strings.DATE_PATTERN);
        return df.format(date);
    }

    public static String[] getEnglishDateRangeByNepaliYearAndMonth(int nepYear, int nepMonth) {
        int lastDay = EngToNepaliDateConverter.getLastDayByNepaliYearAndMonth(nepYear, nepMonth > 0 ? nepMonth : 12);

        String nepDateBegining = nepYear + "-" + (nepMonth > 0 ? nepMonth : 1) + "-" + "01";
        String nepDateEnd = nepYear + "-" + (nepMonth > 0 ? nepMonth : 12) + "-" + lastDay;

        return new String[]{NepToEngDateConverter.convertBsToAd(nepDateBegining), NepToEngDateConverter.convertBsToAd(nepDateEnd)};
    }

    public static String[] getEnglishDateRangeByNepaliYear(int year) {
        int lastDayOfYear = EngToNepaliDateConverter.getLastDayByNepaliYearAndMonth(year, 12);//12 means month chaitra
        String nepDateBegining = year + "-01-" + "01";
        String nepDateEnd = year + "-12-" + lastDayOfYear;
        return new String[]{NepToEngDateConverter.convertBsToAd(nepDateBegining), NepToEngDateConverter.convertBsToAd(nepDateEnd)};
    }

    /**
     * returns first and last date of year
     *
     * @param year
     * @return
     */
    public static String[] getEnglishDateRangeByYear(int year) {
        String startDate = year + "-01-" + "01";
        String endDate = new LocalDate(parseDate(year + "-12-01", false)).dayOfMonth().withMaximumValue().toString();
        return new String[]{startDate, endDate};
    }

    /**
     * returns first and last date of year by year and month
     *
     * @param year
     * @param month
     * @return
     */
    public static String[] getEnglishDateRangeByYearAndMonth(int year, int month) {
        String monthStr = (month < 10 ? "0" + month : month + "") + "-",
                startDate = String.valueOf(year).concat("-").concat(monthStr).concat("01"),
                endDate = (year + "-" + (monthStr) + getLastDayByYearAndMonth(year, month, "en"));
        return new String[]{startDate, endDate};
    }

    /**
     * converts dates to lie inside provided year
     * for eg : if dates are {2080-10-01, 20780-12-10} and year is 2081 then final dates will be {2081-01-01, 2081-12-30}
     * else it returns the same date
     * dates in argument are in english, so it must be parsed to nepali
     *
     * @param dates
     * @param year
     * @return returns nepali date range
     */
    public static String[] convertDatesToFitInGivenYear(String[] dates, int year) {
        if (dates == null || dates.length == 0) return dates;

        String fromDate = dates[0] != null ? EngToNepaliDateConverter.convertEngToNepDateOnly(dates[0]) : null;
        String tillDate = dates[1] != null ? EngToNepaliDateConverter.convertEngToNepDateOnly(dates[1]) : null;

        String[] engDateRange = DateUtils.getEnglishDateRangeByNepaliYear(year);//english date range

        if (fromDate != null) {
            if (!fromDate.contains(String.valueOf(year)))
                fromDate = EngToNepaliDateConverter.convertEngToNepDateOnly(engDateRange[0]);
        }

        if (tillDate != null) {
            if (!tillDate.contains(String.valueOf(year)))
                tillDate = EngToNepaliDateConverter.convertEngToNepDateOnly(engDateRange[1]);
        }

        return new String[]{fromDate, tillDate};
    }

    /**
     * converts dates to lie inside provided year
     * for eg : if dates are {2018-10-01, 2018-12-10} and year is 2075 then final dates will be {2019-01-01, 2019-12-30}
     * else it returns the same date
     * dates in argument are in english
     *
     * @param dates
     * @param year
     * @return returns english date range
     */
    public static String[] convertEngDatesToFitInGivenYear(String[] dates, int year) {
        if (dates == null || dates.length == 0) return dates;

        String fromDate = dates[0];
        String tillDate = dates[1];

        String[] engDateRange = DateUtils.getEnglishDateRangeByYearAndMonth(year, Integer.parseInt(fromDate.split("-")[1]));

        if (!fromDate.contains(String.valueOf(year))) fromDate = engDateRange[0];

        if (tillDate != null) {
            if (!tillDate.contains(String.valueOf(year))) tillDate = engDateRange[1];
        }

        return new String[]{fromDate, tillDate};
    }

    /**
     * checks operation date setting
     * returns date in nepali format if operation date setting is 'np' else returns same value passed in parameter
     *
     * @param date (english date)
     * @return getFinalDate
     */
    public static String getFinalDate(String date, String operationDateSetting, boolean hasTime, boolean isInsertable) {
        if (date == null || date.isEmpty()) return null;
        String finalDate = date;
        if (operationDateSetting.equals("np")) {
            if (isInsertable) {
                finalDate = hasTime ? NepToEngDateConverter.convertNepToFullEngDate(date) : NepToEngDateConverter.convertBsToAd(date);
            } else {
                finalDate = hasTime ? EngToNepaliDateConverter.convertEngToNepFullDate(date) : EngToNepaliDateConverter.convertEngToNepDateOnly(date);
            }
        }

        return finalDate;
    }

    /**
     * checks operation date setting
     * returns date in nepali format if operation date setting is 'np' else returns same value passed in parameter
     *
     * @param date    (english date)
     * @param session
     * @return
     */
    public static String getFinalDate(String date, HttpSession session, boolean hasTime, boolean isInsertable) {
        if (date == null || date.isEmpty()) return null;
        String finalDate = date;
        if (SessionHelper.getOperationDateSetting(session).equals("np")) {
            if (isInsertable) {
                finalDate = hasTime ? NepToEngDateConverter.convertNepToFullEngDate(date) : NepToEngDateConverter.convertBsToAd(date);
            } else {
                finalDate = hasTime ? EngToNepaliDateConverter.convertEngToNepFullDate(date) : EngToNepaliDateConverter.convertEngToNepDateOnly(date);
            }
        }
        return finalDate;
    }

    /**
     * returns english date in java.util.date datatype
     *
     * @param date
     * @param session
     * @return
     */
    public static Date getFinalEngDateInJavaDateFormat(String date, HttpSession session, boolean hasTime) {
        if (date == null || date.trim().isEmpty()) return null;

        Date finalDate = null;

        if (SessionHelper.getOperationDateSetting(session).equals("np")) {
            finalDate = parseDate(hasTime ? NepToEngDateConverter.convertNepToFullEngDate(date) : NepToEngDateConverter.convertBsToAd(date), hasTime);
        } else finalDate = parseDate(date, hasTime);

        return finalDate;
    }


    /**
     * returns english date in java.util.date datatype
     *
     * @param date
     * @return
     */
    public static Date getEngDateInJavaDateFormat(String date, String operationDateSetting, boolean hasTime) {
        if (date == null || date.trim().isEmpty()) return null;
        Date finalDate;
        if (operationDateSetting.equalsIgnoreCase("np")) {
            finalDate = parseDate(hasTime ? NepToEngDateConverter.convertNepToFullEngDate(date) : NepToEngDateConverter.convertBsToAd(date), hasTime);
        } else finalDate = parseDate(date, hasTime);

        return finalDate;
    }

    /**
     * returns english date in java.util.date datatype
     *
     * @param date
     * @param operationDateSetting
     * @return
     */
    public static Date getFinalEngDateInJavaDateFormat(String date, String operationDateSetting, boolean hasTime) {
        if (date == null || date.trim().isEmpty()) return null;
        Date finalDate;

        if (operationDateSetting.equals("np")) {
            finalDate = parseDate(hasTime ? NepToEngDateConverter.convertNepToFullEngDate(date) : NepToEngDateConverter.convertBsToAd(date), hasTime);
        } else finalDate = parseDate(date, hasTime);

        return finalDate;
    }

    public static String calculateTotalTimeTaken(Date startTime, Date endTime) {
        Period period = new Period(new DateTime(startTime), new DateTime(endTime));
        return period.getHours() + " Hour(s), " + period.getMinutes() + " Minute(s), " + period.getSeconds() + " Second(s)";
    }

    public static Integer calculateNumberOfDays(Date dateFirst, Date dateSecond, boolean inclusive) {
        return Days.daysBetween(new LocalDate(dateFirst), new LocalDate(dateSecond)).getDays() + (inclusive ? 1 : 0);//count startDate as 1 day if inclusive
    }

    public static Integer calculateNumberOfWeeks(Date dateFirst, Date dateSecond) {
        return Weeks.weeksBetween(new LocalDate(dateFirst), new LocalDate(dateSecond)).getWeeks();
    }

    public static int getLastDayOfYear(int year, String operationDateSetting) {
        int lastDay;
        if (operationDateSetting.equalsIgnoreCase("en"))
            lastDay = new LocalDate(year, 12, 1).dayOfMonth().getMaximumValue();
        else {
            lastDay = EngToNepaliDateConverter.getLastDayByNepaliYearAndMonth(year, 12);
        }
        return lastDay;
    }

    /**
     * return last date of year on the basis of operation date setting
     *
     * @param year                 (english year)
     * @param operationDateSetting
     * @return
     */
    public static String getLastDateOfYear(Integer year, String operationDateSetting) {
        int lastDay = getLastDayOfYear(year, operationDateSetting);
        return year + "-" + 12 + "-" + lastDay;
    }

    public static String getFormatedStringDate(String date, String operationDateSetting) {
        date = getFinalDate(date, operationDateSetting, false, false);
        if (Helper.isNullOrEmpty(date)) return "";
        String[] splittedDate = date.split("-");
        int year = Integer.parseInt(splittedDate[0]);
        int day = Integer.parseInt(splittedDate[2]);
        String month = operationDateSetting.equalsIgnoreCase("np") ? Helper.getStringMonthNew(Integer.parseInt(splittedDate[1])) : Helper.getStringEngMonthNew(Integer.parseInt(splittedDate[1]));
        return day + "<sup>" + Helper.ordinalSuffixOf(day) + "</sup>" + " " + month + " " + year;
    }

    public static String getFormatedStringDates(String fromDate, String toDate, String operationDateSetting) {
        return getFormatedStringDate(fromDate, operationDateSetting) + " <b>to</b> " + getFormatedStringDate(toDate, operationDateSetting);
    }

    /*new one*/
    public static String getFormattedDateString(String date, String operationDateSetting) {
        if (Helper.isNullOrEmpty(date))
            return "";

        String[] splittedDate = date.split("-");
        int year = Integer.parseInt(splittedDate[0]);
        int month = Integer.parseInt(splittedDate[1]);
        int day = Integer.parseInt(splittedDate[2]);
        String monthString = operationDateSetting.equalsIgnoreCase("np") ? Helper.getStringMonthNew(month) : Helper.getStringEngMonthNew(month);

        return day + Helper.ordinalSuffixOf(day) + " " + monthString + " " + year;
    }

    public static String getFormattedDatesString(String fromDate, String toDate, String operationDateSetting) {
        if (fromDate.equalsIgnoreCase(toDate))
            return getFormattedDateString(fromDate, operationDateSetting);
        return (getFormattedDateString(fromDate, operationDateSetting) + " to " + getFormattedDateString(toDate, operationDateSetting));
    }
    /*end new one*/

    public static LocalDate asLocalDate(Date date) {
        if (date == null) return null;
        return LocalDate.parse(formatDate(date, false));
    }

    public static LocalDate asLocalDate(String date, boolean includeTime) {
        if (date == null) return null;
        return DateUtils.asLocalDate(DateUtils.parseDate(date, includeTime));
    }

    public static int getLastDayByNepaliYearAndMonth(int year, int month) {
//        log.debug("year: {}, month: {}", year, month);
        year = year - 1999;//min year in array is 1999
        return EngToNepaliDateConverter.nepaliDateArray[year][month];
    }

    public static String[] getDateRangeByYearAndMonth(Integer year, Integer month, String operationDateSetting, boolean inEnglishFormat) {
        if (operationDateSetting.equalsIgnoreCase("en"))
            return getEnglishDateRangeByYearAndMonth(year, month);
        else {
            String yearAndMonth = year + "-" + (month < 10 ? "0" + month : month) + "-";
            String[] dateRange = new String[]{yearAndMonth + "01", yearAndMonth + getLastDayByNepaliYearAndMonth(year, month)};
            if (inEnglishFormat) {
                dateRange[0] = NepToEngDateConverter.convertBsToAd(dateRange[0]);
                dateRange[1] = NepToEngDateConverter.convertBsToAd(dateRange[1]);
            }
            return dateRange;
        }
    }

    public static String[] getDateRangeByCurrentYearAndMonth(String operationDateSetting, boolean inEnglishFormat) {
        return getDateRangeByYearAndMonth(
                DateUtils.getCurrentYear(operationDateSetting),
                DateUtils.getCurrentMonth(operationDateSetting),
                operationDateSetting, inEnglishFormat
        );
    }

    public static String[] getDateRangeByYear(Integer year, String operationDateSetting) {
        String[] dateRange;
        if (operationDateSetting.equalsIgnoreCase("en")) {
            dateRange = getEnglishDateRangeByYear(year);
            return dateRange;
        } else dateRange = getEnglishDateRangeByNepaliYear(year);
        return dateRange;
    }

    public static int[] getHourAndMinutes(int minutes) {
        int totalHours = minutes / 60;
        int totalMinutes = minutes - (60 * totalHours);
        return new int[]{totalHours, totalMinutes};
    }

    public static String getHrsAndMinsString(int minutes, boolean makeAbsolute) {
        int[] totalHoursAndMinutes = getHourAndMinutes(minutes);
        return generateHrsAndMinsString(totalHoursAndMinutes[0], totalHoursAndMinutes[1], makeAbsolute);
    }

    public static String generateHrsAndMinsString(int hours, int minutes, boolean makeAbsolute) {
        if (hours == 0 && minutes == 0) return "-";
        String response = "";
        if (hours != 0) response += (makeAbsolute ? Math.abs(hours) : hours) + " hr(s)";
        if (minutes != 0)
            response += (hours != 0 ? " " : "") + (makeAbsolute ? Math.abs(minutes) : minutes) + " min(s)";
        return response;
    }

    public static DateTime asJodaTime(String time) {
        if (time == null || time.isEmpty()) return null;
        org.joda.time.format.DateTimeFormatter dtf = DateTimeFormat.forPattern(Strings.TIME_PATTERN);
        return dtf.parseDateTime(time);
    }

    public static int getTimeDifferenceInMin(String firstTime, String secondTime) {
        if (Helper.isNullOrEmpty(firstTime) || Helper.isNullOrEmpty(secondTime)) return 0;
        Period period = new Period(asJodaTime(firstTime), asJodaTime(secondTime));
        return calculateTotalMinutes(period.getHours(), period.getMinutes(), period.getSeconds());
    }

    public static int[] getTimeDifferenceInHrMinAndSec(String firstTime, String secondTime) {
        Period period = new Period(asJodaTime(firstTime), asJodaTime(secondTime));
        return new int[]{period.getHours(), period.getMinutes(), period.getSeconds()};
    }

    public static int calculateTotalMinutes(int hour, int min, int seconds) {
        return ((hour * 60) + min + (seconds / 60));
    }

    public static int dayOfWeek(String date) {
        Calendar c = Calendar.getInstance();
        c.setTime(parseDate(date, false));
        return c.get(Calendar.DAY_OF_WEEK);
    }

    public static Time parseStringToTime(String time) {
        if (time == null || time.isEmpty()) return null;
        try {
            DateFormat formatter = new SimpleDateFormat("HH:mm");
            return new Time(formatter.parse(time).getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Set<Integer> getListOfNepaliYearFromEnglishDateRange(String startDate, String endDate) {
        Set<Integer> years = new HashSet<>();
        String startNepaliDate = EngToNepaliDateConverter.convertEngToNepDateOnly(startDate);
        String endNepaliDate = EngToNepaliDateConverter.convertEngToNepDateOnly(endDate);

        String[] startDateParts = startNepaliDate.split("-");
        String[] endDateParts = endNepaliDate.split("-");

        int startYear = Integer.parseInt(startDateParts[0]);
        int endYear = Integer.parseInt(endDateParts[0]);

        for (int year = startYear; year <= endYear; year++)
            years.add(year);

        return years;
    }

    public static Set<Integer> getListOfYearFromDateRange(String startDate, String endDate, String operationDateSetting) {
        Set<Integer> years = new HashSet<>();
        if (operationDateSetting.equalsIgnoreCase("np")) {
            startDate = EngToNepaliDateConverter.convertEngToNepDateOnly(startDate);
            endDate = EngToNepaliDateConverter.convertEngToNepDateOnly(endDate);
        }
        String[] startDateParts = startDate.split("-");
        String[] endDateParts = endDate.split("-");

        int startYear = Integer.parseInt(startDateParts[0]);
        int endYear = Integer.parseInt(endDateParts[0]);

        for (int year = startYear; year <= endYear; year++) {
            years.add(year);
        }

        return years;
    }

    public static String formatTime(LocalTime time) {
        return time.toString("HH:mm:ss");
    }

    public static String getStringMonth(int month, String operationDateSetting) {
        if (month > 12) return "Invalid month.";
        if (operationDateSetting.equalsIgnoreCase("np"))
            return ArraysUtil.NEPALI_MONTH_NEW[month - 1];// array index starts from 0 so subtracting 1 from month input
        else
            return ArraysUtil.ENGLISH_MONTHS[month - 1];// array index starts from 0 so subtracting 1 from month input;
    }

    public static String getFromAndToMonths(String dateFirst, String dateSecond, String operationDateSetting) {
        dateFirst = DateUtils.getFinalDate(dateFirst, operationDateSetting, false, false);
        dateSecond = DateUtils.getFinalDate(dateSecond, operationDateSetting, false, false);

        int fromMonthInt = Integer.parseInt(dateFirst.split("-")[1]);
        int toMonthInt = Integer.parseInt(dateSecond.split("-")[1]);

        String month = getStringMonth(fromMonthInt, operationDateSetting);
        if (toMonthInt != fromMonthInt) month += " to " + getStringMonth(toMonthInt, operationDateSetting);

        return month;
    }

    public static String getFromAndToMonthAndYear(String dateFirst, String dateSecond, String operationDateSetting) {
        if (Helper.isNullOrEmpty(dateFirst) || Helper.isNullOrEmpty(dateSecond)) return null;

        dateFirst = DateUtils.getFinalDate(dateFirst, operationDateSetting, false, false);
        dateSecond = DateUtils.getFinalDate(dateSecond, operationDateSetting, false, false);

        String[] dateFirstArray = dateFirst.split("-");
        String[] dateSecondArray = dateSecond.split("-");
        int fromMonthInt = Integer.parseInt(dateFirstArray[1]);
        int toMonthInt = Integer.parseInt(dateSecondArray[1]);

        String month = getStringMonth(fromMonthInt, operationDateSetting) + " " + dateFirstArray[0];
        if (toMonthInt != fromMonthInt)
            month += " to " + getStringMonth(toMonthInt, operationDateSetting) + " " + dateSecondArray[0];

        return month;
    }

    public static Integer getLastDayByYearAndMonth(Integer year, Integer month, String operationDateSetting) {
        if (operationDateSetting.equalsIgnoreCase("np")) {
            return getLastDayByNepaliYearAndMonth(year, month);
        } else {
            return new LocalDate(year, month, 1).dayOfMonth().getMaximumValue();
        }
    }

    public static String getCurrentYearStartDate(String operationDateSetting) {
        return (getCurrentYear(operationDateSetting) + "-01-01");
    }

    public static String getCurrentYearEndDate(String operationDateSetting) {
        int year = getCurrentYear(operationDateSetting);
        return (year + "-12-" + getLastDayByYearAndMonth(year, 12, operationDateSetting));
    }

    public static String getDisplayableAcademicYear(Integer year, String operationDateSetting) {
        if (operationDateSetting.equalsIgnoreCase("en"))
            return year.toString();
        String[] dates = DateUtils.getDateRangeByYear(year, operationDateSetting);
        return year + " (" + dates[0].split("-")[0] + "/" + dates[1].split("-")[0].substring(2) + ")";
    }


    public static String getReadableDate(String operationDateSetting, String date, boolean includeTime) {
        if (date == null) return null;
        String readableDate;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, dd MMM yyyy");
        if (operationDateSetting.equalsIgnoreCase("np")) {
            readableDate = includeTime ? EngToNepaliDateConverter.convertEngToNepFullDate(date) : EngToNepaliDateConverter.convertEngToNepDateOnly(date);
            return simpleDateFormat.format(readableDate);
        } else {
            return simpleDateFormat.format(date);
        }
    }

    public static boolean isBetween(Date dateToBeBetween, Date fromDate, Date toDate) {
        return !(dateToBeBetween.before(fromDate) || dateToBeBetween.after(toDate));
    }

    public static int countTotalDays(String fromDate, String toDate, boolean inclusive) {
        if (fromDate == null || fromDate.isEmpty() || toDate == null || toDate.isEmpty()) return 0;
        return Days.daysBetween(asLocalDate(fromDate, false), asLocalDate(toDate, false)).getDays() + (inclusive ? 1 : 0);
    }

    public static Response isValidTimeToSolve(Date startDate, Date endDate, String startTime, String endTime, Integer plusMinutes, String type, String operationDateSetting) {
        try {
            String currentTime = DateUtils.getCurrentDate("en", true).split(" ")[1];
            Date currentDate = DateUtils.parseDate(DateUtils.getCurrentDate("en", false), false);
            DateTime startTimeJoda = startTime != null ? DateUtils.asJodaTime(startTime) : null;
            DateTime endTimeJoda = endTime != null ? DateUtils.asJodaTime(endTime).plusMinutes(plusMinutes) : null;
            DateTime currentTimeJoda = DateUtils.asJodaTime(currentTime);
            boolean isValid = true;
            String message = null;
            if ((startDate != null && !DateUtils.isBetween(currentDate, startDate, endDate)) || ((startTimeJoda != null && currentTimeJoda.isBefore(startTimeJoda)) || (endTimeJoda != null && currentTimeJoda.isAfter(endTimeJoda)))) {
                String startDateStr = DateUtils.getFinalDate(startDate != null ? startDate.toString() : Helper.getCurrentDateOnly(), operationDateSetting, false, false), endDateStr = DateUtils.getFinalDate(endDate.toString(), operationDateSetting, false, false);
                message = "Invalid date or time to start " + type + " right now. Please try again later." +
                        "<br> Exam Schedule: Date: (" + startDateStr + " to " + endDateStr + ")" +
                        "<br> Time: (" + startTime + " to " + endTime + ").";
                isValid = false;
            }
            return new Response(endTimeJoda != null ? endTimeJoda.toString(Strings.TIME_PATTERN) : null, message, isValid);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(null, Strings.SOMETHING_WENT_WRONG, false);
        }
    }

    public static Date getTime(Date time) {
        if (time == null) return null;

        String timeArray = time.toString().split(" ")[3];
        String hours = timeArray.split(":")[0];
        String minutes = timeArray.split(":")[1];
        String seconds = timeArray.split(":")[2];

        return hours.equals("00") && minutes.equals("00") && seconds.equals("00") ? null : time;
    }

    public static String getCurrentDayName() {
        DateFormat formatter = new SimpleDateFormat("EEEE", new Locale("EN", "NEPAL"));
        return formatter.format(new Date());
    }

    public static Comparator<String> getOrderedWeekDaysName() {
        return (s1, s2) -> {
            try {
                SimpleDateFormat format = new SimpleDateFormat("EEE");
                Date d1 = format.parse(s1);
                Date d2 = format.parse(s2);
                if (d1.equals(d2)) {
                    return s1.substring(s1.indexOf(" ") + 1).compareTo(s2.substring(s2.indexOf(" ") + 1));
                } else {
                    Calendar cal1 = Calendar.getInstance();
                    Calendar cal2 = Calendar.getInstance();
                    cal1.setTime(d1);
                    cal2.setTime(d2);
                    return cal1.get(Calendar.DAY_OF_WEEK) - cal2.get(Calendar.DAY_OF_WEEK);
                }
            } catch (ParseException pe) {
                throw new RuntimeException(pe);
            }
        };

    }

    public static String getReadableFormatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy, hh:mm aaa");
        return sdf.format(date);
    }

    public static String getDateBefore(int days, String operationDateSetting) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -days);
        return getFinalDate(new SimpleDateFormat(Strings.DATE_PATTERN).format(c.getTime()), operationDateSetting, false, false);
    }

    public static String getReadableFormatDateOnly(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy");
        return sdf.format(date);
    }

    /**
     * retrieve first and today's date of current year
     *
     * @param operationDateSetting
     * @return first and today's date of current year
     */
    public static String[] getValidDateRange(String operationDateSetting, boolean includeCurrentDate) {
        String firstDateOfCurrentYear = getCurrentYear(operationDateSetting) + "-01-01";
        String todaysDate = includeCurrentDate ? getCurrentDate(operationDateSetting, false) : null;
        return new String[]{firstDateOfCurrentYear, todaysDate};
    }

    public static String getReadableTime(String time) {
        if (Helper.isNullOrEmpty(time))
            return "";
        return DateTimeFormat.forPattern("hh:mm a").print(DateTimeFormat.forPattern("HH:mm:ss").parseDateTime(time));
    }

    public static String getReadableShiftHours(String time) {
        if (Helper.isNullOrEmpty(time))
            return "";

        String[] splitted = time.split(":");
        int length = splitted.length;
        if (length == 0) return "";

        String hours = splitted[0];
        String minutes = length > 1 ? splitted[1] : "";

        return hours + " hr(s)" + (Helper.parseInt(minutes, true) > 0 ? minutes + " min(s)" : "");
    }

    /**
     * returns yyyy-mm-dd => dd nameOfTheMonth, yyyy
     *
     * @param date
     * @param operationDateSetting
     * @param needSuffix
     * @return
     */
    public static String getFormattedDateString(String date, String operationDateSetting, boolean needSuffix) {
        if (Helper.isNullOrEmpty(date))
            return "";

        String[] splitDate = date.split("-");
        int year = Integer.parseInt(splitDate[0]);
        int month = Integer.parseInt(splitDate[1]);
        int day = Integer.parseInt(splitDate[2]);
        String dayStr = day < 10 ? "0" + day : String.valueOf(day);
        String monthString = operationDateSetting.equalsIgnoreCase("np") ? Helper.getStringMonthNew(month) : Helper.getStringEngMonthNew(month);

        return dayStr + (needSuffix ? Helper.ordinalSuffixOf(day) : "") + " " + monthString + ", " + year;
    }

    public static String getReadableDateTime(String dateTime, String operationDateSetting) {
        if (Helper.isNullOrEmpty(dateTime)) return Constants.NOT_APPLICABLE;

        boolean hasTime = dateTime.contains(" ");//date and time is always seperated by space
        dateTime = DateUtils.getFinalDate(dateTime, operationDateSetting, hasTime, false);
        if (Helper.isNullOrEmpty(dateTime)) return Constants.NOT_APPLICABLE;

        String[] splittedDateTime = dateTime.split(" ");//split date and time

        String fin = DateUtils.getFormatedStringDate(splittedDateTime[0], operationDateSetting);
        if (hasTime)
            fin += " at " + DateUtils.getReadableTime(splittedDateTime[1]);

        return fin;
    }

    public static Date getLastDateByAddingMonth(Date date, int noOfMonths, String operationDateSetting, boolean inclusive) {
        String dateParsed = DateUtils.getFinalDate(DateUtils.formatDate(date, false), operationDateSetting, false, false);
//        log.debug("dateParsed: {}", dateParsed);

        int[] yearAndMonth = DateUtils.getYearAndMonth(dateParsed);
        int year = yearAndMonth[0];
        int month = yearAndMonth[1];

        int validMonths = noOfMonths - (inclusive ? 0 : 1);
        int validYear = validMonths / 12;
        int validMonth = validMonths % 12;

        year += validYear;
        month += validMonth;

        int monthDiff = 12 - month;
        if (monthDiff < 0) {
            year++;
            month = Math.abs(monthDiff);
        }

        int lastDay = DateUtils.getLastDayByYearAndMonth(year, month, operationDateSetting);
        String finalDate = year + "-" + month + "-" + lastDay;

//        log.debug("finalDate: {}", finalDate);

        return DateUtils.getFinalEngDateInJavaDateFormat(finalDate, operationDateSetting, false);
    }

    /**
     * returns yyyy-mm-dd => dd/mm/yyyy
     *
     * @param date
     * @return
     */
    public static String getFormattedDate(String date) {
        if (Helper.isNullOrEmpty(date))
            return "";

        String[] splitDate = date.split("-");
        int year = Integer.parseInt(splitDate[0]);
        int month = Integer.parseInt(splitDate[1]);
        int day = Integer.parseInt(splitDate[2]);
        String dayStr = day < 10 ? "0" + day : String.valueOf(day);
        String monthStr = month < 10 ? "0" + month : String.valueOf(month);


        return dayStr + "/" + monthStr + "/" + year;
    }

}