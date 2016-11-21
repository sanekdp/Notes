package com.example.java.notes.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateUtil {

    public static final String DATE_PATTERN = "MMM dd, yyyy, hh.mma";

    public static final String LEADING_ZERO_TEMPLATE = "%02d";

    public static String formatCurrentDate() {
        try {
            DateFormat sdf = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault());
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            String month = calendar.getDisplayName(
                    Calendar.MONTH,
                    Calendar.SHORT,
                    Locale.getDefault());
            int dayOFMonth = calendar.get(Calendar.DAY_OF_MONTH);
            int hours = calendar.get(Calendar.HOUR_OF_DAY);
            int minutes = calendar.get(Calendar.MINUTE);

            return new StringBuilder()
                    .append(String.valueOf(dayOFMonth))
                    .append(" ")
                    .append(month)
                    .append(" ")
                    .append(String.format(Locale.getDefault(), LEADING_ZERO_TEMPLATE, hours))
                    .append(":")
                    .append(String.format(Locale.getDefault(), LEADING_ZERO_TEMPLATE, minutes))
                    .toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(System.currentTimeMillis());
    }
}
