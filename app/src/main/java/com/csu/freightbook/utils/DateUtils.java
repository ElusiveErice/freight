package com.csu.freightbook.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private static final Date sDate = new Date(System.currentTimeMillis());
    private static final long[] sYMD = fromDate(sDate);

    private static final SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat sFormat2 = new SimpleDateFormat("MM-dd");

    public static long[] fromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        long year = calendar.get(Calendar.YEAR);
        long month = calendar.get(Calendar.MONTH);
        long day = calendar.get(Calendar.DAY_OF_MONTH);

        return new long[]{year, month + 1, day};
    }


    public static String stringFromDate(Date date) {
        return sFormat.format(date);
    }

    public static String stringFromDate2(Date date) {
        return sFormat2.format(date);
    }

    public static Date toDate(long year, long month, long day) throws ParseException {
        return sFormat.parse(year + "-" + month + "-" + day);
    }

    public static long getThisYear() {
        return sYMD[0];
    }

    public static long getThisMonth() {
        return sYMD[1];
    }

    public static long getThisDay() {
        return sYMD[2];
    }
}
