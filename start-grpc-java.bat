@echo off
REM Start gRPC Fraud Detection Server using Java directly (no Maven needed)

echo ================================================
echo Starting gRPC Fraud Detection Server
echo ================================================
echo.

REM Set Java 11 path
set JAVA_HOME=C:\Program Files\Microsoft\jdk-11.0.16.101-hotspot
set PATH=%JAVA_HOME%\bin;%PATH%

cd /d "D:\Study\Github\Insurance-Claim-Processing-SOA"

REM Set classpath
set CLASSPATH=target\classes;target\claim-processing\WEB-INF\classes
for %%i in (target\claim-processing\WEB-INF\lib\*.jar) do call :append_classpath "%%i"
goto :after_classpath

:append_classpath
set CLASSPATH=%CLASSPATH%;%~1
goto :eof

:after_classpath

echo Starting server on port 50051...
echo Press Ctrl+C to stop the server
echo.

java -cp "%CLASSPATH%" com.insurance.grpc.FraudDetectionServer

pause
