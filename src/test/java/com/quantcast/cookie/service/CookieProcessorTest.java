package com.quantcast.cookie.service;

import com.quantcast.cookie.parser.LogAggregator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CookieProcessorTest {

    @Mock
    private LogAggregator mockLogAggregator;

    private CookieProcessor cookieProcessor;
    private File dummyFile;
    private LocalDate targetDate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cookieProcessor = new CookieProcessor(mockLogAggregator);

        // Since LogAggregator is mocked, the file and date don't need real values
        dummyFile = new File("dummy_path.csv");
        targetDate = LocalDate.parse("2018-12-09");
    }

    @Test
    void testProcess_SingleMostActiveCookie() {
        // Arrange
        Map<String, Integer> simulatedCounts = Map.of(
                "cookieA", 1,
                "cookieB", 3, // Clearly the max
                "cookieC", 2
        );
        when(mockLogAggregator.getCookiesFromFile(dummyFile, targetDate)).thenReturn(simulatedCounts);

        // Act
        List<String> result = cookieProcessor.process(dummyFile, targetDate);

        // Assert
        assertEquals(1, result.size(), "Should return exactly one active cookie");
        assertTrue(result.contains("cookieB"), "cookieB should be extracted as the max");
    }

    @Test
    void testProcess_MultipleCookiesWithTieScore() {
        // Arrange
        // Using LinkedHashMap to maintain a predictable iteration order for the assertions
        Map<String, Integer> simulatedCounts = new LinkedHashMap<>();
        simulatedCounts.put("cookieA", 3); // Max tie
        simulatedCounts.put("cookieB", 1);
        simulatedCounts.put("cookieC", 3); // Max tie

        when(mockLogAggregator.getCookiesFromFile(dummyFile, targetDate)).thenReturn(simulatedCounts);

        // Act
        List<String> result = cookieProcessor.process(dummyFile, targetDate);

        // Assert
        assertEquals(2, result.size(), "Should handle tie scores and return both cookies");
        assertTrue(result.contains("cookieA"));
        assertTrue(result.contains("cookieC"));
        assertFalse(result.contains("cookieB"));
    }

    @Test
    void testProcess_EmptyLogCountsReturnsEmptyList() {
        // Arrange
        // Simulating that no entries matched the given date at all
        when(mockLogAggregator.getCookiesFromFile(dummyFile, targetDate)).thenReturn(Collections.emptyMap());

        // Act
        List<String> result = cookieProcessor.process(dummyFile, targetDate);

        // Assert
        assertNotNull(result, "Result list should never be null");
        assertTrue(result.isEmpty(), "Result should be empty if no cookies matched the target date");
    }
}