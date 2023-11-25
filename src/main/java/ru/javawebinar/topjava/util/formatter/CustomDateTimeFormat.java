package ru.javawebinar.topjava.util.formatter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomDateTimeFormat {

    Type type();

    public enum Type {
        START_DATE,
        END_DATE,
        START_TIME,
        END_TIME
    }
}
