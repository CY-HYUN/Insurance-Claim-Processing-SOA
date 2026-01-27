@echo off
REM Start All Servers using Java 11 (no Maven needed)

echo ================================================
echo Starting All Servers (Java 11)
echo ================================================
echo.

REM Set Java 11 path
set JAVA_HOME=C:\Program Files\Microsoft\jdk-11.0.16.101-hotspot
set PATH=%JAVA_HOME%\bin;%PATH%

REM 1. Start Tomcat
echo [1/2] Starting Tomcat (REST, SOAP, GraphQL)...
start "Tomcat Server" C:\apache-tomcat-9.0.113\bin\startup.bat
timeout /t 8 /nobreak

REM 2. Start gRPC Server
echo [2/2] Starting gRPC Server...
cd /d "D:\Study\Github\TSP\Service Oriented Computing\Project\Insurance-Claim-Processing-SOA"

REM Set classpath for gRPC
set CLASSPATH=target\classes;target\claim-processing\WEB-INF\classes
for %%i in (target\claim-processing\WEB-INF\lib\*.jar) do set CLASSPATH=!CLASSPATH!;%%i

start "gRPC Server" cmd /k "set JAVA_HOME=C:\Program Files\Microsoft\jdk-11.0.16.101-hotspot && set PATH=%JAVA_HOME%\bin;%PATH% && java -cp "%CLASSPATH%" com.insurance.grpc.FraudDetectionServer"

echo.
echo ================================================
echo All servers are starting...
echo - Tomcat: http://localhost:8080
echo - gRPC: localhost:50051
echo ================================================
echo.
echo Wait 10 seconds for servers to fully start.
echo Then run: .\run-demo-java.bat
echo.

pause
