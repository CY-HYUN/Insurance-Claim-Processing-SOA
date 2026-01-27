@echo off
REM Demo Script - Run service tests using Java directly (no Maven needed)

echo ================================================
echo Insurance Claim Processing - Service Demo
echo ================================================
echo.

REM Set Java 11 path
set JAVA_HOME=C:\Program Files\Microsoft\jdk-11.0.16.101-hotspot
set PATH=%JAVA_HOME%\bin;%PATH%

REM Set classpath
set CLASSPATH=target\classes;target\claim-processing\WEB-INF\classes
for %%i in (target\claim-processing\WEB-INF\lib\*.jar) do call :append_classpath "%%i"
goto :after_classpath

:append_classpath
set CLASSPATH=%CLASSPATH%;%~1
goto :eof

:after_classpath

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
java -cp "%CLASSPATH%" com.insurance.client.SoapClient
goto END

:GRPC
echo.
echo === Running gRPC Client ===
echo Make sure gRPC server is running (start-grpc-server.bat)
echo.
pause
java -cp "%CLASSPATH%" com.insurance.grpc.FraudDetectionClient
goto END

:GRAPHQL
echo.
echo === Running GraphQL Client ===
echo Make sure Tomcat is running (start-tomcat.bat)
echo.
pause
java -cp "%CLASSPATH%" com.insurance.client.GraphQLClient
goto END

:REST
echo.
echo === Running REST Client ===
echo Make sure Tomcat is running (start-tomcat.bat)
echo.
pause
java -cp "%CLASSPATH%" com.insurance.client.RestClient
goto END

:ALL
echo.
echo === Running All Tests ===
echo.
echo Test 1: SOAP Service
java -cp "%CLASSPATH%" com.insurance.client.SoapClient
echo.
echo Press any key to continue to gRPC test...
pause

echo.
echo Test 2: gRPC Service
java -cp "%CLASSPATH%" com.insurance.grpc.FraudDetectionClient
echo.
echo Press any key to continue to GraphQL test...
pause

echo.
echo Test 3: GraphQL Service
java -cp "%CLASSPATH%" com.insurance.client.GraphQLClient
echo.
echo Press any key to continue to REST test...
pause

echo.
echo Test 4: REST Service
java -cp "%CLASSPATH%" com.insurance.client.RestClient

:END
echo.
echo ================================================
echo Demo Complete
echo ================================================
pause
