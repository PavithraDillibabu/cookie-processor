package com.quantcast.cookie.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LogAggregatorTest {

    private LogAggregator logAggregator;
    private File testLogFile;

    @BeforeEach
    void setUp() throws IOException {
        CsvLogParser realParser = new CsvLogParser();
        logAggregator = new LogAggregator(realParser);

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("cookieLog.csv")) {

            if (inputStream == null) {
                fail("CRITICAL: 'cookieLog.csv' was not found in the test resources folder. " +
                        "Please ensure it is placed in 'src/test/resources/' and trigger a project rebuild.");
            }

            // 1. Create the unique temporary file configuration path
            Path tempFile = Files.createTempFile("cookie_log_test", ".csv");

            // 2. Overwrite the empty placeholder file with our actual resource stream bytes
            Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);

            testLogFile = tempFile.toFile();
            testLogFile.deleteOnExit();
        }
    }
    @Test
    void testGetCookiesFromFile_WithExternalCsvFile() {
        LocalDate targetDate = LocalDate.parse("2018-12-09");


        Map<String, Integer> result = logAggregator.getCookiesFromFile(testLogFile, targetDate);

        assertEquals(2, result.get("AtY0laUfhglK3lC7"), "AtY0laUfhglK3lC7 should have a count of 2");
    }
}