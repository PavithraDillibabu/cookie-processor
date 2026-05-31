package com.quantcast.cookie.parser;


import com.quantcast.cookie.model.LogEntry;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogAggregator {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAggregator.class);
    private final CsvLogParser logParser;

    public LogAggregator(CsvLogParser logParser) {
        this.logParser = logParser;
    }

    /**
     * Ingests the log file and aggregates frequencies exclusively for the target date.
     * Aborts early if it moves past the target date due to the reverse-chronological sort.
     */
    public Map<String, Integer> getCookieCountsByDate(File file, LocalDate targetDate) {

        LOGGER.info("[27166bac-0e49-469c-a550-a060e8ab5d1c] Started aggregating cookie counts for date: {}", targetDate);
        Map<String, Integer> cookieCounts = new HashMap<>();
        boolean foundTargetDate = false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                LogEntry entry = logParser.parseLine(line);
                if (entry == null) {
                    continue;
                }

                if (entry.getDate().equals(targetDate)) {
                    foundTargetDate = true;
                    cookieCounts.put(entry.getCookie(), cookieCounts.getOrDefault(entry.getCookie(), 0) + 1);
                    LOGGER.info("[d5442098-48aa-4943-ba27-9c7e9cd046c2] Matching cookie found for target date. Cookie: {}", entry.getCookie());
                } else if (foundTargetDate) {
                    break;
                }
                LOGGER.info("[bcd1182a-0f73-4c23-a27c-949cb80f224c] Cookie aggregation completed successfully. Total unique cookies: {}", cookieCounts.size());
            }
        } catch (IOException e) {
            System.err.println("CRITICAL: Error reading log repository - " + e.getMessage());
        }

        return cookieCounts;
    }
}
