package com.shriram.microservices.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;

public class DateTimeUtil {

    // constants to use for date time conversions
    private static final String FORMAT_DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy hh:mm:ss";
    private static final String FORMAT_ISO = "yyyy-MM-dd'T'HH:mm:ss.SSSX";

    static Logger logger = Logger.getLogger(DateTimeUtil.class);

    /**
     * Returns the current time formatted in the format specified by {@link DateTimeUtil#TIME_STAMP_FORMAT}
     *
     * @return
     */
    public static String getFormattedCurrentTime(String format) {
        return new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
    }

    /**
     * Returns the current time formatted in the format specified by {@link DateTimeUtil#TIME_STAMP_FORMAT}
     *
     * @return
     */
    public static String getFormattedPreviousDate(String format) {
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, -1);
        return new SimpleDateFormat(format).format(date.getTime());
    }

    public static String getFormattedNow() {
        return new SimpleDateFormat(FORMAT_DD_MM_YYYY_HH_MM_SS).format(Calendar.getInstance().getTime());
    }

    /**
     * Returns the current date converted to the time zone specified by {@link DateTimeUtil#LOCAL_TIMEZONE}
     *
     * @param date
     *            in ISO Format
     * @return Date
     */
    public static Date convertToTimeZone(String date, String format, String timeZone) {
        DateTime dateTime;
        if (format.equals(FORMAT_ISO))
            dateTime = new DateTime(DateTime.parse(date));
        else
            dateTime = new DateTime(DateTime.parse(date, DateTimeFormat.forPattern(format)));
        DateTime dateTimeWithLocalZone = dateTime.withZone(DateTimeZone.forID(timeZone));
        return dateTimeWithLocalZone.toDate();
    }

    /**
     * Returns the String formatted when date and format specified are provided
     *
     * @param date
     *            - Date
     * @param format
     *            - Time Zone format to be converted to
     * @return Date
     */
    public static String format(Date date, String pattern) {
        logger.debug("Format: " + pattern);
        return DateTimeFormat.forPattern(pattern).print(new DateTime(date));
    }

}
