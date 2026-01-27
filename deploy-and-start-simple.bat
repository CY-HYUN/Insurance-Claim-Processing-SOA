@echo off
echo Deploying Insurance Claim Processing WAR to Tomcat...
echo.

set TOMCAT_HOME=C:\apache-tomcat-9.0.113

if not exist "%TOMCAT_HOME%" (
    echo ERROR: Tomcat not found at %TOMCAT_HOME%
    echo Please install Tomcat first
    pause
    exit /b 1
)

echo Copying WAR file...
copy /Y target\claim-processing.war "%TOMCAT_HOME%\webapps\"

echo Starting Tomcat...
cd /d "%TOMCAT_HOME%\bin"
call startup.bat

echo.
echo Deployment complete!
echo Wait 10-15 seconds for Tomcat to deploy the application.
echo.
echo Service URLs:
echo   REST:     http://localhost:8080/claim-processing/api/claim/submit
echo   GraphQL:  http://localhost:8080/claim-processing/graphql
echo   SOAP:     http://localhost:8080/claim-processing/services/IdentityVerification?wsdl
echo.
pause
