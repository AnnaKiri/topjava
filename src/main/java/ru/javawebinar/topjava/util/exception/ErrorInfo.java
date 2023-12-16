package ru.javawebinar.topjava.util.exception;

public class ErrorInfo {
    private final String url;
    private final String type;
    private final String detail;

    public ErrorInfo(CharSequence url, ErrorType type, String detail) {
        this.url = url.toString();
        this.type = type.toString();
        this.detail = detail;
    }
}