package com.quantcast.cookie.cli;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ArgValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArgValidator.class);

    public static CommandLineArgs parseAndValidate(String[] args) {

        LOGGER.info("[29e10c58-d9dc-4611-84f9-b3364ddf3fd2] Started argument validation");

        String fileName = null;
        String dateStr = null;

        for (int i = 0; i < args.length; i++) {
            if ("-f".equals(args[i]) && i + 1 < args.length) {
                fileName = args[++i];
                LOGGER.info("[c28529dc-2404-464b-9c51-f6824cce2994] File argument received: {}", fileName);
            } else if ("-d".equals(args[i]) && i + 1 < args.length) {
                dateStr = args[++i];
                LOGGER.info("[235bbe9b-1f1e-4ccb-8410-483dd7f64259] Date argument received: {}", dateStr);
            }
        }

        if (fileName == null || dateStr == null) {
            LOGGER.error("[ee070781-00fb-42a0-8b8c-731bfd8c1d13] Missing mandatory arguments");
            throw new IllegalArgumentException("Usage: -f <filename> -d <yyyy-MM-dd>");
        }

        File file = new File(fileName);
        if (!file.exists() || !file.isFile()) {
            LOGGER.error("[1d01505b-11e2-4283-863e-8e87fe08ab4f] Invalid file provided: {}", fileName);
            throw new IllegalArgumentException("Error: File does not exist or is invalid: " + fileName);
        }

        try {
            LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            LOGGER.error("[0c4c8e2c-e1c9-474e-8b2b-f6352d1ba71f] Invalid date format: {}", dateStr);
            throw new IllegalArgumentException("Error: Date must be in yyyy-MM-dd format. Provided: " + dateStr);
        }
        LOGGER.info("[5e6930ba-cb92-45e4-8c4a-2f6815a46665] Argument validation completed successfully");
        return new CommandLineArgs(fileName, dateStr);
    }
}