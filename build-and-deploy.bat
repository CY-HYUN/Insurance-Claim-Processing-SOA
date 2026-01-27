@echo off
REM Build and Deploy Insurance Claim Processing - SOA Project
REM This script compiles the project and deploys it to Tomcat

echo ================================================
echo Insurance Claim Processing - Build and Deploy
echo ================================================
echo.

REM Step 1: Clean and compile
echo [Step 1/3] Cleaning and compiling project...
call mvn clean compile
if %errorlevel% neq 0 (
    echo ERROR: Compilation failed!
    pause
    exit /b 1
)
echo ✓ Compilation successful
echo.

REM Step 2: Generate gRPC classes from proto files
echo [Step 2/3] Generating gRPC classes...
call mvn protobuf:compile protobuf:compile-custom
if %errorlevel% neq 0 (
    echo ERROR: gRPC generation failed!
    pause
    exit /b 1
)
echo ✓ gRPC classes generated
echo.

REM Step 3: Package WAR file
echo [Step 3/3] Packaging WAR file...
call mvn package
if %errorlevel% neq 0 (
    echo ERROR: Packaging failed!
    pause
    exit /b 1
)
echo ✓ WAR file created
echo.

REM Check if Tomcat is set up
set TOMCAT_HOME=C:\apache-tomcat-9.0.89
if not exist "%TOMCAT_HOME%" (
    echo WARNING: Tomcat not found at %TOMCAT_HOME%
    echo Please set TOMCAT_HOME variable in this script
    echo.
    echo WAR file location: target\claim-processing.war
    echo Manual deployment: Copy WAR to Tomcat webapps folder
    pause
    exit /b 0
)

REM Deploy to Tomcat
echo Deploying to Tomcat...
copy /Y target\claim-processing.war "%TOMCAT_HOME%\webapps\"
if %errorlevel% equ 0 (
    echo ✓ Deployed to Tomcat successfully
    echo.
    echo WAR file: claim-processing.war
    echo Location: %TOMCAT_HOME%\webapps\
    echo.
    echo To start Tomcat, run: start-tomcat.bat
) else (
    echo ERROR: Deployment failed
)

echo.
echo ================================================
echo Build and Deploy Complete
echo ================================================
pause
