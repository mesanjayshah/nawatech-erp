package io.nawatech.erp.utils.dateutil;

import io.nawatech.erp.utils.Helper;

import java.util.ArrayList;
import java.util.List;

public class EngToNepaliDateConverter {

    // nepali date array
    public static final int[][] nepaliDateArray = {
            {1999, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31},
            {2000, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31},
            {2001, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30},
            {2002, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30},
            {2003, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31},
            {2004, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31},
            {2005, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30},
            {2006, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30},
            {2007, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31},
            {2008, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 29, 31},
            {2009, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30},
            {2010, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30},
            {2011, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31},
            {2012, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30},
            {2013, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30},
            {2014, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30},
            {2015, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31},
            {2016, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30},
            {2017, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30},
            {2018, 31, 32, 31, 32, 31, 30, 30, 29, 30, 29, 30, 30},
            {2019, 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31},
            {2020, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30},
            {2021, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30},
            {2022, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30},
            {2023, 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31},
            {2024, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30},
            {2025, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30},
            {2026, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31},
            {2027, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31},
            {2028, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30},
            {2029, 31, 31, 32, 31, 32, 30, 30, 29, 30, 29, 30, 30},
            {2030, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31},
            {2031, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31},
            {2032, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30},
            {2033, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30},
            {2034, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31},
            {2035, 30, 32, 31, 32, 31, 31, 29, 30, 30, 29, 29, 31},
            {2036, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30},
            {2037, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30},
            {2038, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31},
            {2039, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30},
            {2040, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30},
            {2041, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30},
            {2042, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31},
            {2043, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30},
            {2044, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30},
            {2045, 31, 32, 31, 32, 31, 30, 30, 29, 30, 29, 30, 30},
            {2046, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31},
            {2047, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30},
            {2048, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30},
            {2049, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30},
            {2050, 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31},
            {2051, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30},
            {2052, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30},
            {2053, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30},
            {2054, 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31},
            {2055, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30},
            {2056, 31, 31, 32, 31, 32, 30, 30, 29, 30, 29, 30, 30},
            {2057, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31},
            {2058, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31},
            {2059, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30},
            {2060, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30},
            {2061, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31},
            {2062, 30, 32, 31, 32, 31, 31, 29, 30, 29, 30, 29, 31},
            {2063, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30},
            {2064, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30},
            {2065, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31},
            {2066, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 29, 31},
            {2067, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30},
            {2068, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30},
            {2069, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31},
            {2070, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30},
            {2071, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30},
            {2072, 31, 32, 31, 32, 31, 30, 30, 29, 30, 29, 30, 30},
            {2073, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31},
            {2074, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30},
            {2075, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30},
            {2076, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30},
            {2077, 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31},
            {2078, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30},
            {2079, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30},
            {2080, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30},
            {2081, 31, 31, 32, 32, 31, 30, 30, 30, 29, 30, 30, 30},
            {2082, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 30, 30},
            {2083, 31, 31, 32, 31, 31, 30, 30, 30, 29, 30, 30, 30},
            {2084, 31, 31, 32, 31, 31, 30, 30, 30, 29, 30, 30, 30},
            {2085, 31, 32, 31, 32, 30, 31, 30, 30, 29, 30, 30, 30},
            {2086, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 30, 30},
            {2087, 31, 31, 32, 31, 31, 31, 30, 30, 29, 30, 30, 30},
            {2088, 30, 31, 32, 32, 30, 31, 30, 30, 29, 30, 30, 30},
            {2089, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 30, 30},
            {2090, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 30, 30}
    };

    public static String engToNep(int yyyy, int mm, int dd) throws Exception {
        // Check for date range
        boolean chk = verifyDateOnRange(yyyy, mm, dd);

        if (chk != true) {
            throw new Exception("Date not on range");
        } else {
            // Month data.
            List<Integer> monthList = getListWithMonthsDetail();
            // Month for leap year
            List<Integer> leapMonthList = getListWithLeapYearMonthsDetail();

            int initialEnglishDate = 1943;    // initial english date.
            int initialNepaliDate = 1999;
            int def_nmm = 9;
            int def_ndd = 17 - 1;    // inital nepali date.
            int total_eDays = 0;
            int total_nDays = 0;
            int a = 0;
            int day = 7 - 1;
            int m = 0;
            int y = 0;
            int i = 0;
            int j = 0;
            int numDay = 0;

            // Count total no. of days in-terms year
            for (i = 0; i < (yyyy - initialEnglishDate); i++) //total days for month calculation...(english)
            {
                if (checkLeapYear(initialEnglishDate + i) == true) {
                    for (j = 0; j < 12; j++) {
                        total_eDays += leapMonthList.get(j);
                    }
                } else {
                    for (j = 0; j < 12; j++) {
                        total_eDays += monthList.get(j);
                    }
                }
            }

            // Count total no. of days in-terms of month
            for (i = 0; i < (mm - 1); i++) {
                if (checkLeapYear(yyyy) == true) {
                    total_eDays += leapMonthList.get(i);
                } else {
                    total_eDays += monthList.get(i);
                }
            }

            // Count total no. of days in-terms of date
            total_eDays += dd;
            i = 0;
            j = def_nmm;
            total_nDays = def_ndd;
            m = def_nmm;
            y = initialNepaliDate;

            // Count nepali date from array
            while (total_eDays != 0) {
                a = getBSDateDetail(i, j);
                total_nDays++;        //count the days
                day++;                //count the days interms of 7 days

                if (total_nDays > a) {
                    m++;
                    total_nDays = 1;
                    j++;
                }

                if (day > 7) {
                    day = 1;
                }

                if (m > 12) {
                    y++;
                    m = 1;
                }

                if (j > 12) {
                    j = 1;
                    i++;
                }

                total_eDays--;
            }

            numDay = day;
            return y + "-" + (m < 10 ? "0" : "") + m + "-" + (total_nDays < 10 ? "0" : "") + total_nDays;//$this->_nep_date;
        }
    }


    /**
     * returns the list of months with number of days for leap year
     */
    private static List<Integer> getListWithLeapYearMonthsDetail() {
        List<Integer> leapMonthList = new ArrayList<Integer>();
        leapMonthList.add(31);
        leapMonthList.add(29);
        leapMonthList.add(31);
        leapMonthList.add(30);
        leapMonthList.add(31);
        leapMonthList.add(30);
        leapMonthList.add(31);
        leapMonthList.add(31);
        leapMonthList.add(30);
        leapMonthList.add(31);
        leapMonthList.add(30);
        leapMonthList.add(31);
        return leapMonthList;
    }

    /*
     * returns the list of months with number of days
     *
     */
    private static List<Integer> getListWithMonthsDetail() {
        List<Integer> monthList = new ArrayList<Integer>();
        monthList.add(31);
        monthList.add(28);
        monthList.add(31);
        monthList.add(30);
        monthList.add(31);
        monthList.add(30);
        monthList.add(31);
        monthList.add(31);
        monthList.add(30);
        monthList.add(31);
        monthList.add(30);
        monthList.add(31);
        return monthList;
    }

    private static boolean verifyDateOnRange(int yyyy, int mm, int dd) {

        if (yyyy < 1943 || yyyy > 2033) {
            // 'Supported only between 1944-2022';
            return false;
        }

        if (mm < 1 || mm > 12) {
            // 'Error! month value can be between 1-12 only';
            return false;
        }

        if (dd < 1 || dd > 31) {
            // 'Error! day value can be between 1-31 only';
            return false;
        }

        return true;
    }

    /**
     * Calculates whether english year is leap year or not
     *
     * @param int $year
     * @return bool
     */
    public static boolean checkLeapYear(int year) {
        if (year % 100 == 0) {
            if (year % 400 == 0) {
                return true;
            } else {
                return false;
            }
        } else {
            if (year % 4 == 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    private static int getBSDateDetail(int year, int mnth) {
        return nepaliDateArray[year][mnth];
    }

    public static int getLastDayByNepaliYearAndMonth(int year, int month) {
        year = year - 1999;//min year in array is 1999
        return nepaliDateArray[year][month];
    }

    public static String convertEngToNepFullDate(String engDate) {
        String[] splittedDateTime = engDate.split(" ");//YYYY-mm-dd HH:mm:ss
        String[] splittedDateOnly = splittedDateTime[0].split("-");//YYYY-mm-dd
        int year = Integer.parseInt(splittedDateOnly[0]);
        int month = Integer.parseInt(splittedDateOnly[1]);
        int day = Integer.parseInt(splittedDateOnly[2]);

        String englishDate = "";
        String time = splittedDateTime[1];
        if (year != 2075) {//2075 itself is nepali year
            try {
                englishDate = engToNep(year, month, day);
            } catch (Exception e) {
                englishDate = engDate;
            }
        } else {
            englishDate = splittedDateTime[0];
        }
        return englishDate + " " + time.substring(0, 8);
    }

    public static String convertEngToNepDateOnly(String engLishDate) {
        String engDate = "";

        if (!Helper.isNullOrEmpty(engLishDate)) {
            String[] splittedDateOnly = engLishDate.split("-");
            int year = Integer.parseInt(splittedDateOnly[0]);
            int month = Integer.parseInt(splittedDateOnly[1]);
            int day = Integer.parseInt(splittedDateOnly[2]);


            if (year != 2075 && year != 2074) {//2075, 2074 itself is nepali year
                try {
                    engDate = engToNep(year, month, day);
                } catch (Exception e) {
//					e.printStackTrace();
                    //date not in range.
                    engDate = engLishDate;//let date input given be the output
                }
            } else {
                engDate = engLishDate;
            }
        }

        return engDate;

    }


}