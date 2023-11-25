package ru.javawebinar.topjava.util.formatter;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ru.javawebinar.topjava.util.DateTimeUtil.MAX_DATE;
import static ru.javawebinar.topjava.util.DateTimeUtil.MIN_DATE;

public class CustomDateTimeFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<CustomDateTimeFormat> {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String HH_MM = "HH:mm";

    @Override
    public Set<Class<?>> getFieldTypes() {
        return new HashSet<>(List.of(LocalDate.class, LocalTime.class));
    }

    @Override
    public Printer<?> getPrinter(CustomDateTimeFormat annotation, Class<?> fieldType) {
        return getFormatter(annotation, fieldType);
    }

    @Override
    public Parser<?> getParser(CustomDateTimeFormat annotation, Class<?> fieldType) {
        return getFormatter(annotation, fieldType);
    }

    private Formatter<?> getFormatter(CustomDateTimeFormat annotation, Class<?> fieldType) {
        switch (annotation.type()) {
            case START_DATE -> {
                return new CustomDateTimeFormatter<LocalDate>(YYYY_MM_DD, MIN_DATE.toLocalDate());
            }
            case END_DATE -> {
                return new CustomDateTimeFormatter<LocalDate>(YYYY_MM_DD, MAX_DATE.toLocalDate());
            }
            case START_TIME -> {
                return new CustomDateTimeFormatter<LocalTime>(HH_MM, MIN_DATE.toLocalTime());
            }
            case END_TIME -> {
                return new CustomDateTimeFormatter<LocalTime>(HH_MM, MAX_DATE.toLocalTime());
            }
        }
        return null;
    }
}
