package io.nawatech.erp.utils.neptoengdate;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Class has functionality to convert bikram sambat to Gregorian(AD) date
 */
@Slf4j
public class NepToEngDateConverter {
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd";
    static String format = DEFAULT_FORMAT;
    private static String separator = "-";

    public static void main(String[] args) {
//		System.out.println(convertBsToAd("2052-08-19"));
    }

    /**
     * converts nepali Bikram Sambat date to Gregorian date
     *
     * @param bsDate
     * @return
     */
    public static String convertBsToAd(String bsDate) {
        if (bsDate == null || bsDate.isEmpty())
            return null;

        int bsYear = 0, bsMonth = 0, bsDayOfMonth = 0;

        String[] bsDates = bsDate.split(separator);
        bsYear = Integer.parseInt(bsDates[0]);
        bsMonth = Integer.parseInt(bsDates[1]);
        bsDayOfMonth = Integer.parseInt(bsDates[2]);

        int lookupIndex = getLookupIndex(bsYear);
        if (validateBsDate(bsYear, bsMonth, bsDayOfMonth)) {
            return convertBsToAd(bsDate, bsMonth, bsDayOfMonth, lookupIndex);
        } else {
            throw new IllegalStateException("invalid BS date");
        }

    }

    /**
     * converts Gregorian date to Bikram Sambat date
     *
     * @param adDate
     * @return Bikram Sambat date - String type
     */
    public String convertAdToBs(String adDate) throws ParseException {

        String getCurrentYear[] = adDate.split("-");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date current = df.parse(adDate);
        Calendar adCurrent = new GregorianCalendar(current.getYear(), current.getMonth(), current.getDate());
        Date start = null;
        long equBs = Lookup.lookupNepaliYearStart;
        Integer monthDay[] = null;
        int count = 0;
        for (int i = 0; i < Lookup.lookup.size(); i++) {
            String getStartYear[] = Lookup.lookup.get(i)[0].split("-");
            if (getStartYear[2].equals(getCurrentYear[2])) {
                DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
                start = df1.parse(Lookup.lookup.get(i)[0]);
                monthDay = Lookup.monthDays.get(i);
                equBs += i;
                if (start.getTime() >= current.getTime()) {
                    start = df1.parse(Lookup.lookup.get(i - 1)[0]);
                    equBs -= 1;
                }
            }
        }
        Calendar adStart = new GregorianCalendar(start.getYear(), start.getMonth(), start.getDate());
        long diff = adCurrent.getTime().getTime() - adStart.getTime().getTime();
        long difference = diff / (1000 * 60 * 60 * 24);
        int nepYear = (int) equBs, nepMonth = 0, nepDay = 1, DaysInMonth;
        while (difference != 0) {
            if (difference >= 0) {
                DaysInMonth = monthDay[nepMonth];
                nepDay++;
                if (nepDay > DaysInMonth) {
                    nepMonth++;
                    nepDay = 1;
                }
                if (nepMonth >= 12) {
                    nepYear++;
                    nepMonth = 0;
                }
                difference--;
            }
        }

        nepMonth += 1;
        return nepYear + "-" + nepMonth + "-" + nepDay;

    }

    /**
     * converts nepali bikram sambat date to Gregorian date
     */
    private static String convertBsToAd(String bsDate, int bsMonth, int bsDayOfMonth, int lookupIndex) {
        int numberOfDaysPassed = bsDayOfMonth - 1;// number of days
        // passed since start of year
        // 1 is decreased as year start day has already included
        for (int i = 0; i <= bsMonth - 2; i++) {
            numberOfDaysPassed += Lookup.monthDays.get(lookupIndex)[i];
        }

        // From look up table we need to find corresponding english date for nepali new year
        // we need to add number of days passed from new year to english date
        // which will find corresponding english date we need what starts where...

        String DATE_FORMAT = "dd-MMM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setLenient(false);
        Calendar c1 = Calendar.getInstance();
        try {
            c1.setTime(sdf.parse(Lookup.lookup.get(lookupIndex)[0]));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c1.add(Calendar.DATE, numberOfDaysPassed);

        String dateStr = c1.getTime().toString();
        DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
        Date date = new Date();
        try {
            date = formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DATE);
        return cal.get(Calendar.YEAR) + "-" + ((month < 10 ? "0" + month : month)) + "-"
                + (day < 10 ? "0" + day : day);
    }

    /**
     * validates nepali year
     *
     * @param bsYear
     * @param bsMonth
     * @param bsDayOfMonth
     * @return boolean if there is no lookup for provided year , false is returned
     */
    private static boolean validateBsDate(int bsYear, int bsMonth, int bsDayOfMonth) {
        if (Lookup.lookupNepaliYearStart <= bsYear && bsYear <= (Lookup.lookupNepaliYearStart + Lookup.monthDays.size() - 1)) {
            if (bsMonth >= 1 && bsMonth <= 12) {
                int dayOfMonth = Lookup.monthDays.get(getLookupIndex(bsYear))[bsMonth - 1];
                if (bsDayOfMonth <= dayOfMonth) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * gets array lookup index in lookup data structure
     *
     * @param bsYear
     * @return
     */
    private static int getLookupIndex(int bsYear) {
        return bsYear - Lookup.lookupNepaliYearStart;
    }

    public static String convertNepToFullEngDate(String nepDate) {
        String[] dates = nepDate.split(" ");
        return convertBsToAd(dates[0]) + " " + dates[1];
    }

}
