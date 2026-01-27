@echo off
REM Start Tomcat Server

set TOMCAT_HOME=C:\apache-tomcat-9.0.113

if not exist "%TOMCAT_HOME%" (
    echo ERROR: Tomcat not found at %TOMCAT_HOME%
    echo Please set TOMCAT_HOME variable in this script
    pause
    exit /b 1
)

echo ================================================
echo Starting Tomcat Server
echo ================================================
echo.
echo Tomcat Home: %TOMCAT_HOME%
echo.

cd /d "%TOMCAT_HOME%\bin"
call catalina.bat start

echo.
echo Tomcat is starting...
echo.
echo REST API will be available at:
echo http://localhost:8080/claim-processing/api/claims/submit
echo.
echo GraphQL endpoint:
echo http://localhost:8080/claim-processing/graphql
echo.
echo Check logs: %TOMCAT_HOME%\logs\catalina.out
echo.
pause
