package com.quantcast.cookie.parser;

import com.quantcast.cookie.model.LogEntry;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CsvLogParserTest {
    private final CsvLogParser parser = new CsvLogParser();

    @Test
    void shouldParseValidCsvLine() {
        LogEntry entry = parser.parseLine("AtY0laUfhglK3lC7,2018-12-09T14:19:00+00:00");

        assertNotNull(entry);
        assertEquals("AtY0laUfhglK3lC7", entry.getCookie());
        assertEquals(LocalDate.of(2018, 12, 9), entry.getDate());
    }

    @Test
    void shouldReturnNullForHeader() {
        assertNull(parser.parseLine("cookie,timestamp"));
    }

    @Test
    void shouldReturnNullForEmptyLine() {
        assertNull(parser.parseLine(""));
    }

    @Test
    void shouldReturnNullForMalformedLine() {
        assertNull(parser.parseLine("onlyCookieValue"));
    }
}