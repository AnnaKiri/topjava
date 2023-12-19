package ru.javawebinar.topjava.util.exception;

import java.util.List;

public class ErrorInfo {
    private final String url;
    private final String type;
    private final List<String> details;

    public ErrorInfo(CharSequence url, ErrorType type, List<String> details) {
        this.url = url.toString();
        this.type = type.toString();
        this.details = details;
    }
}