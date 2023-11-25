package ru.javawebinar.topjava.util.formatter;

import org.springframework.format.Formatter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

public class CustomDateTimeFormatter<T extends TemporalAccessor> implements Formatter<T> {

    private final String format;
    private final T defaultValue;

    public CustomDateTimeFormatter(String format, T defaultValue) {
        this.format = format;
        this.defaultValue = defaultValue;
    }

    @Override
    public T parse(String text, Locale locale) {
        if (text != null) {
            if (defaultValue instanceof LocalDate) {
                return (T) LocalDate.parse(text, DateTimeFormatter.ofPattern(format));
            } else if (defaultValue instanceof LocalTime) {
                return (T) LocalTime.parse(text, DateTimeFormatter.ofPattern(format));
            }
        }
        return defaultValue;
    }

    @Override
    public String print(T dateOrTime, Locale locale) {
        return dateOrTime.toString();
    }
}
