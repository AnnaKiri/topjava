package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static boolean isBetweenDates(LocalDate date, LocalDate startDate, LocalDate endDate) {
        return Utils.isBetweenHalfOpen(date, startDate, endDate == LocalDate.MAX ? endDate : endDate.plusDays(1));
    }
}

