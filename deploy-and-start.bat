@echo off
REM Deploy WAR and Start Tomcat - Insurance Claim Processing SOA
REM This script deploys the WAR file and starts Tomcat server

echo ================================================
echo Insurance Claim Processing - Deploy and Start
echo ================================================
echo.

set TOMCAT_HOME=C:\apache-tomcat-9.0.89

REM Check if WAR file exists
if not exist "target\claim-processing.war" (
    echo ERROR: WAR file not found!
    echo Please run: mvn package
    pause
    exit /b 1
)

REM Check if Tomcat exists
if not exist "%TOMCAT_HOME%" (
    echo ERROR: Tomcat not found at %TOMCAT_HOME%
    echo Please update TOMCAT_HOME in this script
    pause
    exit /b 1
)

REM Deploy WAR file
echo [Step 1/2] Deploying WAR file to Tomcat...
copy /Y target\claim-processing.war "%TOMCAT_HOME%\webapps\"
if %errorlevel% neq 0 (
    echo ERROR: Deployment failed
    pause
    exit /b 1
)
echo ✓ WAR file deployed to Tomcat
echo.

REM Start Tomcat
echo [Step 2/2] Starting Tomcat server...
cd /d "%TOMCAT_HOME%\bin"
call startup.bat

echo.
echo ================================================
echo Deployment Complete!
echo ================================================
echo.
echo Tomcat is starting...
echo Wait 10-15 seconds for deployment to complete.
echo.
echo Service URLs:
echo   REST:     http://localhost:8080/claim-processing/api/claim/submit
echo   GraphQL:  http://localhost:8080/claim-processing/graphql
echo   SOAP:     http://localhost:8080/claim-processing/services/IdentityVerification?wsdl
echo.
echo To view logs: tail -f "%TOMCAT_HOME%\logs\catalina.out"
echo To stop Tomcat: shutdown.bat
echo.
pause
