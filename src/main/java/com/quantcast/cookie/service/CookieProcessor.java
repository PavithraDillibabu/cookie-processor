package com.quantcast.cookie.service;


import com.quantcast.cookie.parser.LogAggregator;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CookieProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CookieProcessor.class);
    private final LogAggregator logAggregator;

    public CookieProcessor(LogAggregator logAggregator) {
        this.logAggregator = logAggregator;
    }

    public List<String> process(File file, LocalDate targetDate) {

        LOGGER.info("[9cc9fe76-8b40-4f52-aff1-6b73dcc4d1a1] Started cookie processing for date: {}", targetDate);
        Map<String, Integer> cookieCounts = logAggregator.getCookiesFromFile(file, targetDate);
        List<String> mostActiveCookies = getMostActiveCookies(cookieCounts);
        LOGGER.info("[f0d98b8b-a961-451f-adcc-db610a148ac6] Cookie aggregation completed. Total unique cookies: {}", cookieCounts.size());
        return mostActiveCookies;
    }

    private List<String> getMostActiveCookies(Map<String, Integer> cookieCounts) {

        int maxCount = cookieCounts.values()
                                    .stream()
                                    .max(Integer::compareTo)
                                    .orElse(0);

        return cookieCounts.entrySet()
                            .stream()
                            .filter(entry -> entry.getValue() == maxCount)
                            .map(Map.Entry::getKey)
                            .toList();
    }
}