@echo off
cd /d "%~dp0"

java -cp "target\classes;target\dependency\*" com.quantcast.cookie.MostActiveCookie %*