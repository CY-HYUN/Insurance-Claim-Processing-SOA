@echo off
REM Start All Servers for Insurance Claim Processing SOA

echo ================================================
echo Starting All Servers
echo ================================================
echo.

REM 1. Start Tomcat
echo [1/2] Starting Tomcat (REST, SOAP, GraphQL)...
start "Tomcat Server" C:\apache-tomcat-9.0.113\bin\startup.bat
timeout /t 5 /nobreak

REM 2. Start gRPC Server
echo [2/2] Starting gRPC Server...
cd /d "D:\Study\Github\TSP\Service Oriented Computing\Project\Insurance-Claim-Processing-SOA"
start "gRPC Server" cmd /k "%USERPROFILE%\AppData\Roaming\Code\User\globalStorage\pleiades.java-extension-pack-jdk\maven\latest\bin\mvn.cmd" exec:java -Dexec.mainClass="com.insurance.grpc.FraudDetectionServer"

echo.
echo ================================================
echo All servers are starting...
echo - Tomcat: http://localhost:8080
echo - gRPC: localhost:50051
echo ================================================
echo.
echo Wait 10 seconds for servers to fully start.
echo Then test with Postman!
echo.

pause
