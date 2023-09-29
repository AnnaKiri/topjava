package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicBoolean;

public class UserMealWithExcess {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private AtomicBoolean excess;

    public UserMealWithExcess(LocalDateTime dateTime, String description, int calories, AtomicBoolean excess) {
        this(dateTime, description, calories);
        this.excess = excess;
    }

    public UserMealWithExcess(LocalDateTime dateTime, String description, int calories, boolean excess) {
        this(dateTime, description, calories);
        this.excess = new AtomicBoolean(excess);
    }

    private UserMealWithExcess(LocalDateTime dateTime, String description, int calories) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "UserMealWithExcess{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + excess.get() +
                '}';
    }

    public AtomicBoolean getExcess() {
        return excess;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }
}
