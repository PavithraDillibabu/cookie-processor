package com.quantcast.cookie.parser;


import com.quantcast.cookie.model.LogEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;
import java.time.LocalDate;

public class CsvLogParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvLogParser.class);
    private static final String CSV_DELIMITER = ",";

    public LogEntry parseLine(String line) {

        LOGGER.info("[4604c7ee-886d-4e0c-a5b6-0e515e88647a] Started parsing csv line");
        if (line == null || line.trim().isEmpty() || line.startsWith("cookie")) {
            return null; // Handle empty lines or headers safely
        }

        String[] parts = line.split(CSV_DELIMITER);
        if (parts.length < 2) {
            return null; // Malformed row protection
        }

        String cookie = parts[0].trim();

        LOGGER.info("[bac2aef0-7cf5-4b5a-b664-60c662d7944d] Cookie extracted: {}", cookie);
        ZonedDateTime timestamp = ZonedDateTime.parse(parts[1].trim());
        LocalDate date = timestamp.toLocalDate();
        LOGGER.info("[2dca2766-cde4-4ef4-b15a-c37e36fa01b8]Csv line parsing completed successfully");

        return new LogEntry(cookie, date);
    }
}
