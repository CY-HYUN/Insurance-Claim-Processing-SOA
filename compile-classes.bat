@echo off
REM Compile all Java classes before demo
REM This is a simplified version of build-and-deploy.bat (compile only)

echo ================================================
echo Compiling Java Classes
echo ================================================
echo.

REM Navigate to project directory
cd /d "D:\Study\Github\Insurance-Claim-Processing-SOA"

REM Try common Maven locations
set MAVEN_FOUND=0

REM Check 1: Maven in PATH
where mvn >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    set MAVEN_CMD=mvn
    set MAVEN_FOUND=1
    goto :compile
)

REM Check 2: Common Maven installation paths
set MAVEN_LOCATIONS=C:\apache-maven-3.9.6\bin;C:\apache-maven-3.8.8\bin;C:\Program Files\Apache\Maven\bin;C:\Program Files (x86)\Apache\Maven\bin

for %%P in (%MAVEN_LOCATIONS%) do (
    if exist "%%P\mvn.cmd" (
        set MAVEN_CMD=%%P\mvn.cmd
        set MAVEN_FOUND=1
        goto :compile
    )
)

REM Check 3: Conda environment Maven
if defined CONDA_PREFIX (
    if exist "%CONDA_PREFIX%\Scripts\mvn.cmd" (
        set MAVEN_CMD=%CONDA_PREFIX%\Scripts\mvn.cmd
        set MAVEN_FOUND=1
        goto :compile
    )
)

REM Maven not found
if %MAVEN_FOUND% EQU 0 (
    echo.
    echo ==========================================
    echo ERROR: Maven not found!
    echo ==========================================
    echo.
    echo Please run: build-and-deploy.bat instead
    echo Or install Maven from: https://maven.apache.org/download.cgi
    echo.
    pause
    exit /b 1
)

:compile
echo Using Maven: %MAVEN_CMD%
echo.
echo Step 1: Cleaning previous build...
call "%MAVEN_CMD%" clean

if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Maven clean failed
    pause
    exit /b 1
)

echo.
echo Step 2: Compiling Java sources...
call "%MAVEN_CMD%" compile

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERROR: Compilation failed
    pause
    exit /b 1
)

echo.
echo ================================================
echo Compilation Successful!
echo ================================================
echo.
echo Compiled classes are in: target\classes\
echo.
echo You can now run:
echo   - start-grpc-java.bat (Terminal 2)
echo   - run-demo-java.bat (Terminal 3)
echo.
pause
