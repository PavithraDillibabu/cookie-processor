package com.quantcast.cookie.cli;


import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ArgValidatorTest {

    @Test
    void shouldThrowExceptionWhenFileArgumentMissing() {
        String[] args = {"-d", "2018-12-09"};

        assertThrows(IllegalArgumentException.class,
                () -> ArgValidator.parseAndValidate(args));
    }

    @Test
    void shouldThrowExceptionWhenDateArgumentMissing() {
        String[] args = {"-f", "cookie_log.csv"};

        assertThrows(IllegalArgumentException.class,
                () -> ArgValidator.parseAndValidate(args));
    }

    @Test
    void shouldThrowExceptionForInvalidDateFormat() {
        String[] args = {"-f", "cookie_log.csv", "-d", "09-12-2018"};

        assertThrows(IllegalArgumentException.class,
                () -> ArgValidator.parseAndValidate(args));
    }
}