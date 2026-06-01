package com.quantcast.cookie;

import com.quantcast.cookie.cli.ArgValidator;
import com.quantcast.cookie.cli.CommandLineArgs;
import com.quantcast.cookie.parser.CsvLogParser;
import com.quantcast.cookie.parser.LogAggregator;
import com.quantcast.cookie.service.CookieProcessor;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

public class MostActiveCookie {

    public static void main(String[] args) {

        try {

            CommandLineArgs validatedArgs = ArgValidator.parseAndValidate(args);

            File file = new File(validatedArgs.getFileName());
            LocalDate targetDate = LocalDate.parse(validatedArgs.getDateStr());

            List<String> activeCookies = executeCookieProcessor(file, targetDate);

            activeCookies.forEach(System.out::println);

        } catch (IllegalArgumentException e) {
            System.err.println("Configuration Error: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("An unexpected system error occurred: " + e.getMessage());
            System.exit(1);
        }
    }


    private static List<String> executeCookieProcessor(File file, LocalDate targetDate) {

        CsvLogParser csvParser = new CsvLogParser();
        LogAggregator logAggregator = new LogAggregator(csvParser);
        CookieProcessor processor = new CookieProcessor(logAggregator);

        return processor.process(file, targetDate);
    }
}