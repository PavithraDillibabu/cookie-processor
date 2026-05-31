package com.quantcast.cookie.model;

import java.time.LocalDate;

public class LogEntry {
    private final String cookie;
    private final LocalDate date;

    public LogEntry(String cookie, LocalDate date) {
        this.cookie = cookie;
        this.date = date;
    }

    public String getCookie() {
        return cookie;
    }

    public LocalDate getDate() {
        return date;
    }
}
