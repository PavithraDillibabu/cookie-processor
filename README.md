# cookie-processor

A command-line Java utility that identifies the most active cookie(s) for a specific date from a cookie log file.

The application separates concerns across three distinct logical layers:

CLI Layer : Responsible for validating and processing command line arguments.
Service Layer : It utilizes Java Streams API to extract the most active cookie frequencies.
Parser Layer : Responsible for ingesting and transforming raw cookie log data into structured domain objects and aggregated frequency information.

Running the application using Batch File: .\cookie-analyzer.bat -f "\path\cookie_log.csv" -d 2018-12-09

Log file location: logs/cookie-analyzer.log

Test coverage includes:

CSV parsing
Aggregation logic
Command-line validation
Most active cookie processing