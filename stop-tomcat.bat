@echo off
REM Stop Tomcat Server

set TOMCAT_HOME=C:\apache-tomcat-9.0.89

if not exist "%TOMCAT_HOME%" (
    echo ERROR: Tomcat not found at %TOMCAT_HOME%
    echo Please set TOMCAT_HOME variable in this script
    pause
    exit /b 1
)

echo ================================================
echo Stopping Tomcat Server
echo ================================================
echo.

cd /d "%TOMCAT_HOME%\bin"
call catalina.bat stop

echo.
echo Tomcat server stopped
echo.
pause
