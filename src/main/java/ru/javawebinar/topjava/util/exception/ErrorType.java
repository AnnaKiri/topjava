package ru.javawebinar.topjava.util.exception;

public enum ErrorType {
    APP_ERROR("Application error"),
    DATA_NOT_FOUND("Data not found"),
    DATA_ERROR("Data error"),
    VALIDATION_ERROR("Validation error");

    private final String title;

    ErrorType(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
