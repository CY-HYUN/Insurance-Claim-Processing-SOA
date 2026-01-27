@echo off
echo Redeploying Insurance Claim Processing to Tomcat...
echo.

set TOMCAT_HOME=C:\apache-tomcat-9.0.113

REM Step 1: Stop Tomcat
echo [1/4] Stopping Tomcat...
cd /d "%TOMCAT_HOME%\bin"
call shutdown.bat
timeout /t 3 /nobreak >nul

REM Step 2: Clean old deployment
echo [2/4] Cleaning old deployment...
del /q "%TOMCAT_HOME%\webapps\claim-processing.war" 2>nul
rmdir /s /q "%TOMCAT_HOME%\webapps\claim-processing" 2>nul

REM Step 3: Copy new WAR
echo [3/4] Copying new WAR file...
cd /d "D:\Study\Github\TSP\Service Oriented Computing\Project\Insurance-Claim-Processing-SOA"
copy /Y target\claim-processing.war "%TOMCAT_HOME%\webapps\"

REM Step 4: Start Tomcat
echo [4/4] Starting Tomcat...
cd /d "%TOMCAT_HOME%\bin"
call startup.bat

echo.
echo Redeployment complete!
echo Wait 10-15 seconds for Tomcat to redeploy the application.
echo.
echo Test URLs:
echo   REST:     http://localhost:8080/claim-processing/api/claims/health
echo   GraphQL:  http://localhost:8080/claim-processing/graphql
echo   SOAP:     http://localhost:8080/claim-processing/services/IdentityVerification?wsdl
echo.
pause
