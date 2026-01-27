@echo off
REM Build SOAP Service for Apache Axis2 deployment

echo ================================================
echo Building SOAP Service for Axis2
echo ================================================
echo.

REM Compile Java classes
echo [Step 1/3] Compiling Java classes...
call mvn compile
if %errorlevel% neq 0 (
    echo ERROR: Compilation failed!
    pause
    exit /b 1
)
echo ✓ Compilation successful
echo.

REM Create services directory structure
echo [Step 2/3] Creating SOAP service structure...
if not exist "target\soap-service\META-INF" mkdir "target\soap-service\META-INF"
if not exist "target\soap-service\com\insurance\soap" mkdir "target\soap-service\com\insurance\soap"

REM Copy compiled classes
xcopy /Y /E "target\classes\com\insurance\soap\*" "target\soap-service\com\insurance\soap\"

REM Copy services.xml
copy /Y "src\main\resources\META-INF\services.xml" "target\soap-service\META-INF\"

echo ✓ Service structure created
echo.

REM Create AAR file
echo [Step 3/3] Creating AAR (Axis Archive) file...
cd target\soap-service
jar -cvf ..\IdentityVerificationService.aar META-INF com
cd ..\..

echo ✓ AAR file created: target\IdentityVerificationService.aar
echo.

echo ================================================
echo SOAP Service Build Complete
echo ================================================
echo.
echo AAR file location: target\IdentityVerificationService.aar
echo.
echo To deploy:
echo 1. Copy AAR file to Axis2 services directory
echo 2. Restart Axis2 server
echo.
pause
