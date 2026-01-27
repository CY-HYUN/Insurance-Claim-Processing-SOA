@echo off
REM Recompile RestClient.java quickly

echo ================================================
echo Recompiling RestClient.java
echo ================================================
echo.

REM Set Java 11 path
set JAVA_HOME=C:\Program Files\Microsoft\jdk-11.0.16.101-hotspot
set PATH=%JAVA_HOME%\bin;%PATH%

cd /d "D:\Study\Github\TSP\Service Oriented Computing\Project\Insurance-Claim-Processing-SOA"

echo Compiling RestClient.java...
javac -d target\classes -cp "target\claim-processing\WEB-INF\classes;target\claim-processing\WEB-INF\lib\*" src\main\java\com\insurance\client\RestClient.java

if %errorlevel% equ 0 (
    echo.
    echo ================================================
    echo Compilation successful!
    echo ================================================
    echo.
    echo You can now run: .\run-demo-java.bat
) else (
    echo.
    echo ================================================
    echo Compilation FAILED!
    echo ================================================
)

echo.
pause
