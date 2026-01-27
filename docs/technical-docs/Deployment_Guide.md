# Insurance Claim Processing SOA - Deployment Guide

## Table of Contents
- [Overview](#overview)
- [Prerequisites](#prerequisites)
- [Environment Setup](#environment-setup)
- [Build Process](#build-process)
- [Deployment Steps](#deployment-steps)
- [Configuration](#configuration)
- [Starting Services](#starting-services)
- [Verification](#verification)
- [Production Deployment](#production-deployment)
- [Maintenance](#maintenance)
- [Troubleshooting](#troubleshooting)

---

## Overview

This guide provides step-by-step instructions for deploying the Insurance Claim Processing SOA system. The deployment includes:

- **REST Service**: JAX-RS application on Tomcat
- **SOAP Service**: JAX-WS web service
- **gRPC Server**: Standalone server on port 50051
- **GraphQL Service**: GraphQL endpoint on Tomcat

### Deployment Architecture

```
Development Environment (Single Server):
┌─────────────────────────────────────────────────┐
│  Server: localhost                              │
│                                                 │
│  ┌─────────────────────────────────────────┐  │
│  │ Apache Tomcat 9.0 (Port 8080)           │  │
│  │ - REST API                              │  │
│  │ - GraphQL Endpoint                      │  │
│  │ - SOAP Service                          │  │
│  └─────────────────────────────────────────┘  │
│                                                 │
│  ┌─────────────────────────────────────────┐  │
│  │ gRPC Server (Port 50051)                │  │
│  │ - Fraud Detection Service               │  │
│  └─────────────────────────────────────────┘  │
└─────────────────────────────────────────────────┘
```

---

## Prerequisites

### Required Software

1. **Java Development Kit (JDK)**
   - Version: 11 or higher
   - Download: https://adoptium.net/
   - Verify installation:
     ```bash
     java -version
     javac -version
     ```
   - Expected output:
     ```
     java version "11.0.x" (or higher)
     javac 11.0.x
     ```

2. **Apache Maven**
   - Version: 3.6 or higher
   - Download: https://maven.apache.org/download.cgi
   - Verify installation:
     ```bash
     mvn -version
     ```
   - Expected output:
     ```
     Apache Maven 3.6.x (or higher)
     Maven home: C:\...\apache-maven-3.x.x
     Java version: 11.0.x
     ```

3. **Apache Tomcat**
   - Version: 9.0 or higher
   - Download: https://tomcat.apache.org/download-90.cgi
   - Installation:
     - Extract to: `C:\apache-tomcat-9.0.89` (or your preferred location)
     - No additional installation needed
   - Verify:
     ```bash
     # Windows
     %TOMCAT_HOME%\bin\version.bat

     # Linux/Mac
     $TOMCAT_HOME/bin/version.sh
     ```

### System Requirements

- **Operating System**: Windows 10/11, Linux, or macOS
- **RAM**: Minimum 4GB (8GB recommended)
- **Disk Space**: 500MB for application + dependencies
- **Network**: Internet connection (for Maven dependencies)
- **Ports Required**:
  - 8080 (Tomcat/HTTP)
  - 50051 (gRPC)

### Environment Variables (Optional)

**Windows**:
```cmd
set JAVA_HOME=C:\Program Files\Java\jdk-11
set MAVEN_HOME=C:\Program Files\apache-maven-3.x.x
set TOMCAT_HOME=C:\apache-tomcat-9.0.89
set PATH=%JAVA_HOME%\bin;%MAVEN_HOME%\bin;%PATH%
```

**Linux/Mac**:
```bash
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk
export MAVEN_HOME=/opt/apache-maven-3.x.x
export TOMCAT_HOME=/opt/apache-tomcat-9.0.89
export PATH=$JAVA_HOME/bin:$MAVEN_HOME/bin:$PATH
```

---

## Environment Setup

### Step 1: Clone/Download Project

```bash
# If using Git
git clone <repository-url>
cd Insurance-Claim-Processing-SOA

# Or extract ZIP file
unzip Insurance-Claim-Processing-SOA.zip
cd Insurance-Claim-Processing-SOA
```

### Step 2: Configure Tomcat Path

**Edit the following batch files** and set your Tomcat installation path:

1. **build-and-deploy.bat**
2. **start-tomcat.bat**
3. **stop-tomcat.bat**

**Example Configuration**:
```batch
REM Set your Tomcat installation path here
set TOMCAT_HOME=C:\apache-tomcat-9.0.89

REM Or use environment variable
REM set TOMCAT_HOME=%TOMCAT_HOME%
```

**Verify Tomcat Path**:
```bash
# Windows
echo %TOMCAT_HOME%
dir %TOMCAT_HOME%\bin\catalina.bat

# Linux/Mac
echo $TOMCAT_HOME
ls $TOMCAT_HOME/bin/catalina.sh
```

### Step 3: Verify Project Structure

Ensure the following structure exists:

```
Insurance-Claim-Processing-SOA/
├── pom.xml                          ✓ Maven configuration
├── src/
│   └── main/
│       ├── java/                    ✓ Java source files
│       ├── proto/                   ✓ Protocol buffer files
│       ├── resources/               ✓ Configuration files
│       └── webapp/                  ✓ Web application files
├── build-and-deploy.bat             ✓ Build script
├── start-tomcat.bat                 ✓ Start script
├── stop-tomcat.bat                  ✓ Stop script
├── start-grpc-server.bat            ✓ gRPC server script
└── docs/                            ✓ Documentation
```

---

## Build Process

### Build Command

```bash
mvn clean package
```

**What This Does**:
1. **Clean**: Removes previous build artifacts (`target/` folder)
2. **Compile**: Compiles Java source files
3. **Process Resources**: Copies resources to target
4. **Generate Sources**: Generates gRPC classes from `.proto` files
5. **Test**: Runs unit tests (if any)
6. **Package**: Creates WAR file for deployment

### Build Output

After successful build:

```
target/
├── claim-processing.war              # Deployable WAR file
├── classes/                          # Compiled classes
│   ├── com/insurance/                # Application classes
│   └── schema.graphql                # GraphQL schema
└── generated-sources/                # Generated gRPC classes
    └── protobuf/
        ├── java/                     # Protobuf Java classes
        └── grpc-java/                # gRPC service stubs
```

**Verify Build Success**:
```bash
# Check if WAR file exists
dir target\claim-processing.war      # Windows
ls target/claim-processing.war       # Linux/Mac

# Check WAR size (should be ~30-50 MB)
# Verify it's not 0 bytes
```

### Build Troubleshooting

**Issue: Maven cannot download dependencies**
```bash
# Solution 1: Check internet connection
ping repo.maven.apache.org

# Solution 2: Clear Maven cache
mvn clean -U

# Solution 3: Delete local repository
# Windows: del /s /q %USERPROFILE%\.m2\repository
# Linux: rm -rf ~/.m2/repository
```

**Issue: Protocol buffer compilation fails**
```bash
# Solution: Ensure proto file exists
dir src\main\proto\fraud_detection.proto

# Rebuild with verbose output
mvn clean compile -X
```

**Issue: Java version mismatch**
```bash
# Solution: Verify Java version
java -version
mvn -version

# Ensure both use Java 11 or higher
```

---

## Deployment Steps

### Method 1: Automated Deployment (Recommended)

**Single Command Deployment**:

```bash
build-and-deploy.bat
```

**What This Does**:
1. Cleans previous builds (`mvn clean`)
2. Compiles and packages application (`mvn package`)
3. Stops Tomcat (if running)
4. Removes old deployment
5. Copies WAR file to Tomcat webapps
6. Starts Tomcat
7. Waits for deployment to complete

**Expected Output**:
```
============================================
Insurance Claim Processing - Build & Deploy
============================================

Step 1: Building project with Maven...
[INFO] Building war: target\claim-processing.war
[INFO] BUILD SUCCESS

Step 2: Stopping Tomcat (if running)...
Tomcat stopped.

Step 3: Deploying to Tomcat...
Copying WAR file...
WAR file deployed successfully

Step 4: Starting Tomcat...
Tomcat started successfully

Step 5: Waiting for deployment...
Deployment verification in progress...
✓ Application deployed successfully!

============================================
Deployment Complete!
============================================

Services available at:
- REST API: http://localhost:8080/claim-processing/api/claims
- GraphQL: http://localhost:8080/claim-processing/graphql
- SOAP: http://localhost:8080/claim-processing/soap/identity

Next steps:
1. Start gRPC server: start-grpc-server.bat
2. Run tests: run-demo.bat

============================================
```

---

### Method 2: Manual Deployment

If automated deployment fails, follow these manual steps:

#### Step 1: Build Project

```bash
mvn clean package
```

#### Step 2: Stop Tomcat

```bash
# Windows
stop-tomcat.bat

# Or manually:
%TOMCAT_HOME%\bin\shutdown.bat

# Linux/Mac
$TOMCAT_HOME/bin/shutdown.sh
```

**Verify Tomcat Stopped**:
```bash
# Check if port 8080 is free
netstat -ano | findstr :8080    # Windows
lsof -i :8080                   # Linux/Mac

# Should return no results
```

#### Step 3: Remove Old Deployment

```bash
# Windows
del %TOMCAT_HOME%\webapps\claim-processing.war
rmdir /s /q %TOMCAT_HOME%\webapps\claim-processing

# Linux/Mac
rm -f $TOMCAT_HOME/webapps/claim-processing.war
rm -rf $TOMCAT_HOME/webapps/claim-processing
```

#### Step 4: Deploy WAR File

```bash
# Windows
copy target\claim-processing.war %TOMCAT_HOME%\webapps\

# Linux/Mac
cp target/claim-processing.war $TOMCAT_HOME/webapps/
```

**Verify Copy**:
```bash
# Windows
dir %TOMCAT_HOME%\webapps\claim-processing.war

# Linux/Mac
ls -lh $TOMCAT_HOME/webapps/claim-processing.war
```

#### Step 5: Start Tomcat

```bash
# Windows
start-tomcat.bat

# Or manually:
%TOMCAT_HOME%\bin\startup.bat

# Linux/Mac
$TOMCAT_HOME/bin/startup.sh
```

#### Step 6: Wait for Deployment

Tomcat will automatically extract the WAR file:

```bash
# Wait 30-60 seconds, then verify:

# Windows
dir %TOMCAT_HOME%\webapps\claim-processing\

# Linux/Mac
ls $TOMCAT_HOME/webapps/claim-processing/
```

**Expected Folder Structure After Deployment**:
```
%TOMCAT_HOME%/webapps/claim-processing/
├── WEB-INF/
│   ├── classes/                # Compiled Java classes
│   ├── lib/                    # JAR dependencies
│   └── web.xml                 # Web configuration
├── META-INF/
└── index.html                  # Welcome page
```

---

## Configuration

### Tomcat Configuration

**File**: `src/main/webapp/WEB-INF/web.xml`

**Key Configurations**:

```xml
<!-- Application Display Name -->
<display-name>Insurance Claim Processing</display-name>

<!-- Jersey REST Servlet -->
<servlet>
    <servlet-name>Jersey REST Service</servlet-name>
    <servlet-class>org.glassfish.jersey.servlet.ServletContainer</servlet-class>
    <init-param>
        <param-name>jersey.config.server.provider.packages</param-name>
        <param-value>com.insurance.service</param-value>
    </init-param>
</servlet>

<!-- REST URL Pattern -->
<servlet-mapping>
    <servlet-name>Jersey REST Service</servlet-name>
    <url-pattern>/api/*</url-pattern>
</servlet-mapping>

<!-- GraphQL Servlet -->
<servlet>
    <servlet-name>GraphQL</servlet-name>
    <servlet-class>com.insurance.graphql.GraphQLServlet</servlet-class>
</servlet>

<!-- GraphQL URL Pattern -->
<servlet-mapping>
    <servlet-name>GraphQL</servlet-name>
    <url-pattern>/graphql</url-pattern>
</servlet-mapping>
```

**Modify if needed**:
- URL patterns
- Port numbers (in batch files)
- Service packages

---

### gRPC Server Configuration

**File**: `src/main/java/com/insurance/grpc/FraudDetectionServer.java`

**Key Configuration**:
```java
public class FraudDetectionServer {
    private static final int PORT = 50051;  // Change if needed

    public void start() throws IOException {
        server = ServerBuilder.forPort(PORT)
                .addService(new FraudDetectionServiceImpl())
                .build()
                .start();
    }
}
```

**To Change Port**:
1. Edit `FraudDetectionServer.java` → Change `PORT` constant
2. Edit `FraudDetectionClient.java` → Update connection URL
3. Rebuild: `mvn clean package`

---

### GraphQL Schema Configuration

**File**: `src/main/resources/schema.graphql`

**Schema Location**:
- Source: `src/main/resources/schema.graphql`
- Deployed: `%TOMCAT_HOME%/webapps/claim-processing/WEB-INF/classes/schema.graphql`

**Modify Schema**:
1. Edit `src/main/resources/schema.graphql`
2. Rebuild and redeploy: `build-and-deploy.bat`

---

## Starting Services

### Start All Services

**Complete Startup Sequence**:

**Terminal 1: Start Tomcat**
```bash
start-tomcat.bat
```

**Wait for**:
```
[INFO] Server startup in [xxxx] milliseconds
```

**Terminal 2: Start gRPC Server**
```bash
start-grpc-server.bat
```

**Wait for**:
```
gRPC Server started on port 50051
```

**Services Now Running**:
- ✅ REST API: `http://localhost:8080/claim-processing/api/claims`
- ✅ GraphQL: `http://localhost:8080/claim-processing/graphql`
- ✅ SOAP: `http://localhost:8080/claim-processing/soap/identity`
- ✅ gRPC: `localhost:50051`

---

### Start Services Individually

#### Start Tomcat Only

```bash
start-tomcat.bat
```

**Services Available**:
- ✅ REST API
- ✅ GraphQL
- ✅ SOAP
- ❌ gRPC (requires separate server)

#### Start gRPC Server Only

```bash
start-grpc-server.bat
```

**Services Available**:
- ✅ gRPC
- ❌ REST, GraphQL, SOAP (require Tomcat)

---

### Stop Services

#### Stop Tomcat

```bash
stop-tomcat.bat
```

**Or manually**:
```bash
# Windows
%TOMCAT_HOME%\bin\shutdown.bat

# Linux/Mac
$TOMCAT_HOME/bin/shutdown.sh
```

**Force Stop (if shutdown fails)**:
```bash
# Windows - Find and kill Java process
tasklist | findstr java
taskkill /F /PID <PID>

# Linux/Mac
ps aux | grep tomcat
kill -9 <PID>
```

#### Stop gRPC Server

**Graceful Shutdown**:
- Press `Ctrl+C` in gRPC server terminal

**Force Stop**:
```bash
# Windows
netstat -ano | findstr :50051
taskkill /F /PID <PID>

# Linux/Mac
lsof -i :50051
kill -9 <PID>
```

---

## Verification

### Verify Deployment

#### 1. Check Tomcat Logs

**Location**:
```
%TOMCAT_HOME%\logs\catalina.out         # Main log
%TOMCAT_HOME%\logs\localhost.log        # Deployment log
```

**Look for**:
```
INFO: Deployment of web application archive [...\claim-processing.war] has finished in [xxxx] ms
```

**No Errors**:
```
✓ No "ERROR" messages
✓ No "SEVERE" messages
✓ No "Exception" stack traces
```

#### 2. Check Application Deployed

**Verify WAR extracted**:
```bash
# Windows
dir %TOMCAT_HOME%\webapps\claim-processing\WEB-INF\

# Linux/Mac
ls $TOMCAT_HOME/webapps/claim-processing/WEB-INF/
```

**Expected Contents**:
- `classes/` folder with compiled classes
- `lib/` folder with JAR dependencies
- `web.xml` configuration file

#### 3. Test REST API Health Check

```bash
curl http://localhost:8080/claim-processing/api/claims/health
```

**Expected Response**:
```json
{
  "status": "UP",
  "service": "ClaimSubmissionService",
  "timestamp": "2024-01-20T10:30:00Z"
}
```

**If 404 Error**:
- Wait longer (deployment may still be in progress)
- Check Tomcat logs for errors
- Verify URL is correct

#### 4. Test GraphQL Endpoint

```bash
curl -X POST http://localhost:8080/claim-processing/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ __schema { types { name } } }"}'
```

**Expected Response**:
```json
{
  "data": {
    "__schema": {
      "types": [...]
    }
  }
}
```

#### 5. Test SOAP Service (WSDL)

**Browser or cURL**:
```bash
curl http://localhost:8080/claim-processing/soap/identity?wsdl
```

**Expected Response**:
- XML WSDL document
- Contains service definitions
- No error messages

#### 6. Test gRPC Server

**Check if listening**:
```bash
# Windows
netstat -ano | findstr :50051

# Linux/Mac
lsof -i :50051
```

**Expected Output**:
```
TCP    0.0.0.0:50051    0.0.0.0:0    LISTENING    <PID>
```

**Run gRPC Client Test**:
```bash
mvn exec:java -Dexec.mainClass="com.insurance.client.GrpcClient"
```

**Expected Output**:
```
=== gRPC Fraud Detection Test ===
Fraud Detection Result:
- Risk Level: LOW
✓ gRPC test completed successfully
```

---

### Complete Verification Checklist

Run through this checklist to ensure everything is working:

- ✅ **Build**: WAR file created successfully
- ✅ **Deploy**: WAR file in Tomcat webapps
- ✅ **Extract**: Application folder extracted
- ✅ **Tomcat**: Running on port 8080
- ✅ **gRPC**: Server running on port 50051
- ✅ **REST**: Health check returns 200 OK
- ✅ **GraphQL**: Schema introspection works
- ✅ **SOAP**: WSDL accessible
- ✅ **gRPC**: Client can connect
- ✅ **Logs**: No errors in Tomcat logs
- ✅ **Integration**: Full claim submission works

**Run Full Integration Test**:
```bash
run-demo.bat
# Select option 4 or 5
```

---

## Production Deployment

### Production Environment Setup

For production deployment, consider these additional steps:

#### 1. Security Hardening

**Tomcat Security**:
```xml
<!-- web.xml additions -->
<security-constraint>
    <web-resource-collection>
        <web-resource-name>Protected</web-resource-name>
        <url-pattern>/*</url-pattern>
    </web-resource-collection>
    <auth-constraint>
        <role-name>user</role-name>
    </auth-constraint>
    <user-data-constraint>
        <transport-guarantee>CONFIDENTIAL</transport-guarantee>
    </user-data-constraint>
</security-constraint>
```

**Enable HTTPS**:
1. Generate SSL certificate
2. Configure Tomcat HTTPS connector
3. Update URLs to use HTTPS

**Enable Authentication**:
- Implement JWT/OAuth2
- Add authentication filters
- Secure SOAP/gRPC with credentials

#### 2. Performance Tuning

**Tomcat JVM Options**:
```bash
# Windows: %TOMCAT_HOME%\bin\setenv.bat
set CATALINA_OPTS=-Xms2048m -Xmx4096m -XX:+UseG1GC

# Linux: $TOMCAT_HOME/bin/setenv.sh
export CATALINA_OPTS="-Xms2048m -Xmx4096m -XX:+UseG1GC"
```

**gRPC Server Tuning**:
```java
server = ServerBuilder.forPort(PORT)
    .addService(new FraudDetectionServiceImpl())
    .executor(Executors.newFixedThreadPool(20))  // Thread pool
    .maxInboundMessageSize(10 * 1024 * 1024)     // 10MB max
    .build();
```

#### 3. Database Integration

**Add Database Connection**:
```xml
<!-- web.xml -->
<resource-ref>
    <description>DB Connection</description>
    <res-ref-name>jdbc/ClaimDB</res-ref-name>
    <res-type>javax.sql.DataSource</res-type>
    <res-auth>Container</res-auth>
</resource-ref>
```

**Configure in Tomcat context.xml**:
```xml
<Resource name="jdbc/ClaimDB"
          auth="Container"
          type="javax.sql.DataSource"
          driverClassName="com.mysql.cj.jdbc.Driver"
          url="jdbc:mysql://localhost:3306/claims"
          username="claimuser"
          password="password"
          maxTotal="20"
          maxIdle="10"
          maxWaitMillis="10000"/>
```

#### 4. Load Balancing

**Multi-Server Setup**:
```
                   ┌─────────────┐
                   │  NGINX      │
                   │  Port 80    │
                   └──────┬──────┘
                          │
        ┌─────────────────┼─────────────────┐
        │                 │                 │
   ┌────▼────┐       ┌────▼────┐       ┌────▼────┐
   │ Tomcat  │       │ Tomcat  │       │ Tomcat  │
   │ Server 1│       │ Server 2│       │ Server 3│
   │ :8081   │       │ :8082   │       │ :8083   │
   └─────────┘       └─────────┘       └─────────┘
```

**NGINX Configuration**:
```nginx
upstream claim_backend {
    server localhost:8081;
    server localhost:8082;
    server localhost:8083;
}

server {
    listen 80;

    location /claim-processing {
        proxy_pass http://claim_backend;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```

#### 5. Monitoring & Logging

**Enable Tomcat Access Logging**:
```xml
<!-- server.xml -->
<Valve className="org.apache.catalina.valves.AccessLogValve"
       directory="logs"
       prefix="claim_access_log"
       suffix=".txt"
       pattern="%h %l %u %t &quot;%r&quot; %s %b" />
```

**Application Logging**:
```xml
<!-- logback.xml -->
<configuration>
    <appender name="FILE" class="ch.qos.logback.core.FileAppender">
        <file>logs/claim-processing.log</file>
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <root level="INFO">
        <appender-ref ref="FILE" />
    </root>
</configuration>
```

**Monitoring Tools**:
- JMX monitoring for Tomcat
- Prometheus + Grafana for metrics
- ELK Stack for log aggregation
- APM tools (New Relic, AppDynamics)

#### 6. Backup & Recovery

**Backup Strategy**:
```bash
# Daily backup script
DATE=$(date +%Y%m%d)
tar -czf backup-$DATE.tar.gz \
    claim-processing.war \
    %TOMCAT_HOME%/webapps/claim-processing \
    %TOMCAT_HOME%/logs
```

**Recovery Plan**:
1. Stop services
2. Restore WAR file
3. Clear Tomcat cache
4. Restart services
5. Verify deployment

---

## Maintenance

### Regular Maintenance Tasks

#### Daily
- ✅ Check service health
- ✅ Review error logs
- ✅ Monitor resource usage

#### Weekly
- ✅ Rotate log files
- ✅ Clear temporary files
- ✅ Review performance metrics

#### Monthly
- ✅ Update dependencies
- ✅ Security patches
- ✅ Backup configurations

### Log Rotation

**Tomcat Logs** (automatic):
- Rotated daily by Tomcat
- Format: `catalina.YYYY-MM-DD.log`
- Old logs in `%TOMCAT_HOME%\logs\`

**Clean Old Logs**:
```bash
# Windows
forfiles /p "%TOMCAT_HOME%\logs" /s /m *.log /d -30 /c "cmd /c del @path"

# Linux
find $TOMCAT_HOME/logs -name "*.log" -mtime +30 -delete
```

### Update Deployment

**Zero-Downtime Update**:
1. Build new WAR file
2. Deploy to standby server
3. Test standby server
4. Switch load balancer
5. Update primary server
6. Switch back to both servers

**Quick Update** (with downtime):
```bash
# Stop services
stop-tomcat.bat

# Rebuild
mvn clean package

# Redeploy
del %TOMCAT_HOME%\webapps\claim-processing.war
copy target\claim-processing.war %TOMCAT_HOME%\webapps\

# Start services
start-tomcat.bat
```

---

## Troubleshooting

### Deployment Issues

**Issue: WAR not deploying**

**Symptoms**:
- WAR file in webapps but not extracted
- No application folder created

**Solutions**:
1. Check Tomcat logs for errors
2. Verify WAR file is not corrupted (size > 0)
3. Check file permissions
4. Try manual extraction and folder copy

```bash
# Manual extraction
jar -xf claim-processing.war -d claim-processing

# Copy to webapps
xcopy /E /I claim-processing %TOMCAT_HOME%\webapps\claim-processing
```

---

**Issue: Port already in use**

**Symptoms**:
- Tomcat fails to start
- Error: "Address already in use: bind"

**Solutions**:
```bash
# Find process using port 8080
netstat -ano | findstr :8080

# Kill process
taskkill /F /PID <PID>

# Or change Tomcat port in server.xml
```

---

**Issue: Out of memory**

**Symptoms**:
- `java.lang.OutOfMemoryError`
- Services crash

**Solutions**:
```bash
# Increase heap size
# Windows: %TOMCAT_HOME%\bin\setenv.bat
set CATALINA_OPTS=-Xms2048m -Xmx4096m

# Restart Tomcat
stop-tomcat.bat
start-tomcat.bat
```

---

### Service Issues

**Issue: 404 Not Found**

**Symptoms**:
- All endpoints return 404
- Tomcat is running

**Solutions**:
1. Verify context path: `/claim-processing`
2. Check if application deployed
3. Review servlet mappings in `web.xml`
4. Check Tomcat deployment logs

---

**Issue: gRPC connection refused**

**Symptoms**:
- gRPC client cannot connect
- "Connection refused" error

**Solutions**:
1. Verify gRPC server is running
2. Check port 50051 is not blocked
3. Check firewall settings
4. Verify server address (localhost vs 127.0.0.1)

---

**Issue: GraphQL schema not found**

**Symptoms**:
- GraphQL endpoint returns error
- Schema loading fails

**Solutions**:
1. Verify `schema.graphql` in resources
2. Check WAR file contains schema
3. Verify resource loading in servlet
4. Rebuild and redeploy

```bash
# Check if schema in WAR
jar -tf target\claim-processing.war | findstr schema
```

---

### Performance Issues

**Issue: Slow response times**

**Solutions**:
1. Increase JVM heap size
2. Enable connection pooling
3. Add caching layer
4. Optimize database queries
5. Profile with JProfiler/VisualVM

---

**Issue: High CPU usage**

**Solutions**:
1. Check for infinite loops in code
2. Optimize resource-intensive operations
3. Add request throttling
4. Scale horizontally (more servers)

---

## Support & Resources

### Documentation
- [Tomcat Documentation](https://tomcat.apache.org/tomcat-9.0-doc/)
- [Maven Documentation](https://maven.apache.org/guides/)
- [gRPC Java Guide](https://grpc.io/docs/languages/java/)
- [GraphQL Java](https://www.graphql-java.com/)

### Project Documentation
- `docs/readme.txt` - Complete project documentation
- `docs/Architecture_Overview.md` - System architecture
- `docs/Service_Endpoints.md` - API reference
- `docs/Testing_Guide.md` - Testing instructions

### Logs Location
- Tomcat: `%TOMCAT_HOME%\logs\`
- Application: Check Tomcat logs for application output

### Getting Help
1. Check documentation files
2. Review Tomcat logs
3. Test individual services
4. Verify all prerequisites

---

**Document Version**: 1.0
**Last Updated**: January 2026
**Author**: Insurance Claim Processing Team
**Course**: Service Oriented Computing
**Institution**: Télécom SudParis

---

## Quick Reference

### Essential Commands

```bash
# Build
mvn clean package

# Deploy (automated)
build-and-deploy.bat

# Start services
start-tomcat.bat         # Terminal 1
start-grpc-server.bat    # Terminal 2

# Test
run-demo.bat             # Terminal 3

# Stop services
stop-tomcat.bat
Ctrl+C (in gRPC terminal)

# Logs
%TOMCAT_HOME%\logs\catalina.out
```

### Service URLs

```
REST:     http://localhost:8080/claim-processing/api/claims/submit
GraphQL:  http://localhost:8080/claim-processing/graphql
SOAP:     http://localhost:8080/claim-processing/soap/identity?wsdl
gRPC:     localhost:50051
Health:   http://localhost:8080/claim-processing/api/claims/health
```

---

**Ready to Deploy!** Follow the steps in order, and your services will be up and running.
