@echo off
REM Demo Script - Run all service tests

echo ================================================
echo Insurance Claim Processing - Service Demo
echo ================================================
echo.

REM Set Maven path from VSCode
set MVN_CMD=%USERPROFILE%\AppData\Roaming\Code\User\globalStorage\pleiades.java-extension-pack-jdk\maven\latest\bin\mvn.cmd

REM Check if project is compiled
if not exist "target\classes" (
    echo Project not compiled. Running build first...
    call "%MVN_CMD%" compile
    if %errorlevel% neq 0 (
        echo ERROR: Compilation failed!
        pause
        exit /b 1
    )
)

echo.
echo Select a service to test:
echo.
echo 1. SOAP - Identity Verification Service
echo 2. gRPC - Fraud Detection Client (requires server running)
echo 3. GraphQL - Policy Validation Client (requires Tomcat)
echo 4. REST - Claim Submission Client (requires Tomcat)
echo 5. Run All Tests (requires all servers)
echo.
set /p choice="Enter choice (1-5): "

if "%choice%"=="1" goto SOAP
if "%choice%"=="2" goto GRPC
if "%choice%"=="3" goto GRAPHQL
if "%choice%"=="4" goto REST
if "%choice%"=="5" goto ALL
goto END

:SOAP
echo.
echo === Running SOAP Client ===
call "%MVN_CMD%" exec:java -Dexec.mainClass="com.insurance.client.SoapClient"
goto END

:GRPC
echo.
echo === Running gRPC Client ===
echo Make sure gRPC server is running (start-grpc-server.bat)
echo.
pause
call "%MVN_CMD%" exec:java -Dexec.mainClass="com.insurance.client.GrpcClient"
goto END

:GRAPHQL
echo.
echo === Running GraphQL Client ===
echo Make sure Tomcat is running (start-tomcat.bat)
echo.
pause
call "%MVN_CMD%" exec:java -Dexec.mainClass="com.insurance.client.GraphQLClient"
goto END

:REST
echo.
echo === Running REST Client ===
echo Make sure Tomcat is running (start-tomcat.bat)
echo.
pause
call "%MVN_CMD%" exec:java -Dexec.mainClass="com.insurance.client.RestClient"
goto END

:ALL
echo.
echo === Running All Tests ===
echo.
echo Test 1: SOAP Service
call "%MVN_CMD%" exec:java -Dexec.mainClass="com.insurance.client.SoapClient"
echo.
echo Press any key to continue to gRPC test...
pause

echo.
echo Test 2: gRPC Service
call "%MVN_CMD%" exec:java -Dexec.mainClass="com.insurance.client.GrpcClient"
echo.
echo Press any key to continue to GraphQL test...
pause

echo.
echo Test 3: GraphQL Service
call "%MVN_CMD%" exec:java -Dexec.mainClass="com.insurance.client.GraphQLClient"
echo.
echo Press any key to continue to REST test...
pause

echo.
echo Test 4: REST Service
call "%MVN_CMD%" exec:java -Dexec.mainClass="com.insurance.client.RestClient"

:END
echo.
echo ================================================
echo Demo Complete
echo ================================================
pause
