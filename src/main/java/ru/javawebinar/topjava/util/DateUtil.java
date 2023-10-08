package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter DATE_FORMATTER_T = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    public static String format(LocalDateTime date) {
        return date.format(DATE_FORMATTER);
    }

    public static LocalDateTime parse(String date) {
        return LocalDateTime.parse(date, DATE_FORMATTER_T);
    }
}
