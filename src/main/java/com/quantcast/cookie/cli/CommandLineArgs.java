package com.quantcast.cookie.cli;

public class CommandLineArgs {
    private final String fileName;
    private final String dateStr;

    public CommandLineArgs(String fileName, String dateStr) {
        this.fileName = fileName;
        this.dateStr = dateStr;
    }

    public String getFileName() {
        return fileName;
    }

    public String getDateStr() {
        return dateStr;
    }
}