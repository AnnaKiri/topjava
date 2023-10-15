package ru.javawebinar.topjava.util;

public class Utils {
    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T data, T start, T end) {
        return data.compareTo(start) >= 0 && data.compareTo(end) < 0;
    }
}
