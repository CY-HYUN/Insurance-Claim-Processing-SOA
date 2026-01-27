@echo off
REM Start gRPC Fraud Detection Server

echo ================================================
echo Starting gRPC Fraud Detection Server
echo ================================================
echo.

cd /d "D:\Study\Github\TSP\Service Oriented Computing\Project\Insurance-Claim-Processing-SOA"

echo Starting server on port 50051...
echo Press Ctrl+C to stop the server
echo.

REM Use Maven from VSCode
set MVN_CMD=%USERPROFILE%\AppData\Roaming\Code\User\globalStorage\pleiades.java-extension-pack-jdk\maven\latest\bin\mvn.cmd

"%MVN_CMD%" exec:java -Dexec.mainClass="com.insurance.grpc.FraudDetectionServer"

pause
