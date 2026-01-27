# Insurance Claim Processing SOA - Testing Guide

## Table of Contents
- [Prerequisites](#prerequisites)
- [Quick Start Testing](#quick-start-testing)
- [Testing Methods](#testing-methods)
- [Test Scenarios](#test-scenarios)
- [Individual Service Testing](#individual-service-testing)
- [Integration Testing](#integration-testing)
- [Troubleshooting](#troubleshooting)

---

## Prerequisites

### Required Software

1. **Java Development Kit (JDK)** 11 or higher
   ```bash
   java -version
   # Should show: java version "11.0.x" or higher
   ```

2. **Apache Maven** 3.6+
   ```bash
   mvn -version
   # Should show: Apache Maven 3.6.x or higher
   ```

3. **Apache Tomcat** 9.0+ (configured in batch files)
   - Installation path: `C:\apache-tomcat-9.0.89` (or your path)
   - Verify in batch files: `build-and-deploy.bat`, `start-tomcat.bat`

4. **Internet Connection** (for Maven dependencies)

### Optional Tools

- **Postman** - For REST and GraphQL API testing
- **SoapUI** - For SOAP service testing
- **BloomRPC / gRPC GUI** - For gRPC service testing
- **cURL** - Command-line HTTP client

---

## Quick Start Testing

### Method 1: Automated Demo Script (Recommended)

The easiest way to test all services:

```bash
# 1. Build and deploy
build-and-deploy.bat

# 2. Start services
start-tomcat.bat         # Terminal 1
start-grpc-server.bat    # Terminal 2

# 3. Run interactive demo
run-demo.bat             # Terminal 3
```

**Demo Script Menu**:
```
============================================
Insurance Claim Processing - Test Demo
============================================

Select a test to run:

1. Test SOAP Service (Identity Verification)
2. Test gRPC Service (Fraud Detection)
3. Test GraphQL Service (Policy Validation)
4. Test REST Service (Complete Claim Submission)
5. Run All Tests
6. Exit

Enter your choice (1-6):
```

**What It Tests**:
- ✅ SOAP: Identity verification with sample user data
- ✅ gRPC: Fraud detection analysis
- ✅ GraphQL: Policy validation queries
- ✅ REST: End-to-end claim submission with all services
- ✅ Integration: Full orchestration pipeline

---

### Method 2: Individual Service Testing

Test each service independently using Maven:

```bash
# SOAP Service Test
mvn exec:java -Dexec.mainClass="com.insurance.client.SoapClient"

# gRPC Service Test (requires gRPC server running)
mvn exec:java -Dexec.mainClass="com.insurance.client.GrpcClient"

# GraphQL Service Test (requires Tomcat running)
mvn exec:java -Dexec.mainClass="com.insurance.client.GraphQLClient"

# REST Service Test (requires Tomcat running)
mvn exec:java -Dexec.mainClass="com.insurance.client.RestClient"
```

---

## Testing Methods

### 1. Java Test Clients (Built-in)

**Advantages**:
- ✅ Pre-configured with sample data
- ✅ No additional tools needed
- ✅ Demonstrates service integration
- ✅ Complete code examples

**Location**: `src/main/java/com/insurance/client/`

**Files**:
- `RestClient.java` - REST API testing
- `SoapClient.java` - SOAP service testing
- `GrpcClient.java` - gRPC service testing
- `GraphQLClient.java` - GraphQL testing

---

### 2. Postman Collection

**Import Collection**:
```
File: docs/API_Documentation/Insurance_Claim_Processing.postman_collection.json
```

**Steps**:
1. Open Postman
2. Click **Import**
3. Select the JSON file
4. Collection will be imported with all requests

**Included Requests**:
- ✅ Submit Claim (REST)
- ✅ Get Claim Status (REST)
- ✅ Health Check (REST)
- ✅ Get Policy (GraphQL)
- ✅ Get Policies by User (GraphQL)
- ✅ Validate Policy (GraphQL)
- ✅ Get All Policies (GraphQL)

**Advantages**:
- ✅ Visual interface
- ✅ Easy to modify requests
- ✅ Save responses
- ✅ Environment variables support

---

### 3. cURL Commands

**Advantages**:
- ✅ Command-line testing
- ✅ Scriptable
- ✅ Works on any platform
- ✅ Easy to share

**Example Commands**: See [Individual Service Testing](#individual-service-testing) section

---

### 4. Browser Testing

**GraphQL Queries** can be tested directly in browser:

1. Navigate to: `http://localhost:8080/claim-processing/graphql`
2. Use browser's developer tools (F12)
3. Go to Console tab
4. Paste and execute fetch commands

**Example**:
```javascript
fetch('http://localhost:8080/claim-processing/graphql', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    query: '{ policy(policyId: "POL-001") { policyId status coverageAmount } }'
  })
})
.then(r => r.json())
.then(data => console.log(data));
```

---

## Test Scenarios

### Test Case 1: Low-Risk Claim (Expected: APPROVED)

**Scenario**: Standard car accident claim, low amount, verified user

**Test Data**:
```json
{
  "claimId": "CLM-001",
  "userId": "USR-123",
  "claimType": "AUTO",
  "claimAmount": 5000.0,
  "description": "Minor car accident - rear bumper damage",
  "incidentDate": "2024-01-15"
}
```

**Expected Results**:
- **Identity Verification**: ✅ PASSED (confidence > 90%)
- **Fraud Detection**: ✅ LOW RISK (score < 30)
- **Policy Validation**: ✅ VALID (active policy, sufficient coverage)
- **Final Status**: ✅ **APPROVED**

**Testing Steps**:

1. **Using REST Client (Java)**:
```bash
mvn exec:java -Dexec.mainClass="com.insurance.client.RestClient"
```

2. **Using cURL**:
```bash
curl -X POST http://localhost:8080/claim-processing/api/claims/submit \
  -H "Content-Type: application/json" \
  -d '{
    "claimId": "CLM-001",
    "userId": "USR-123",
    "claimType": "AUTO",
    "claimAmount": 5000.0,
    "description": "Minor car accident",
    "incidentDate": "2024-01-15"
  }'
```

3. **Using Postman**:
   - Select: `POST Submit Claim`
   - Body: Use the JSON above
   - Click **Send**

**Expected Response**:
```json
{
  "claimId": "CLM-001",
  "status": "APPROVED",
  "message": "Claim approved successfully. All validation checks passed.",
  "timestamp": "2024-01-20T10:30:00Z",
  "processingDetails": {
    "identityVerification": {
      "verified": true,
      "confidence": 95.5,
      "message": "Identity verified successfully"
    },
    "fraudDetection": {
      "riskLevel": "LOW",
      "riskScore": 15.2,
      "indicators": [],
      "recommendation": "APPROVE"
    },
    "policyValidation": {
      "isValid": true,
      "message": "Policy is active and covers claim amount",
      "coverageAmount": 100000.0
    }
  }
}
```

---

### Test Case 2: Medium-Risk Claim (Expected: MANUAL_REVIEW)

**Scenario**: Higher-value claim requiring manual review

**Test Data**:
```json
{
  "claimId": "CLM-002",
  "userId": "USR-456",
  "claimType": "ACCIDENT",
  "claimAmount": 45000.0,
  "description": "Major accident with significant damage",
  "incidentDate": "2024-01-10"
}
```

**Expected Results**:
- **Identity Verification**: ✅ PASSED
- **Fraud Detection**: ⚠️ MEDIUM RISK (score 30-70)
- **Policy Validation**: ✅ VALID
- **Final Status**: ⚠️ **MANUAL_REVIEW**

**Why Manual Review**:
- Claim amount is substantial ($45,000)
- Requires human verification
- Multiple validation factors need review

**Testing Steps**:
```bash
curl -X POST http://localhost:8080/claim-processing/api/claims/submit \
  -H "Content-Type: application/json" \
  -d '{
    "claimId": "CLM-002",
    "userId": "USR-456",
    "claimType": "ACCIDENT",
    "claimAmount": 45000.0,
    "description": "Major accident",
    "incidentDate": "2024-01-10"
  }'
```

**Expected Response**:
```json
{
  "claimId": "CLM-002",
  "status": "MANUAL_REVIEW",
  "message": "Claim requires manual review due to medium fraud risk",
  "timestamp": "2024-01-20T10:30:00Z",
  "processingDetails": {
    "identityVerification": {
      "verified": true,
      "confidence": 92.0,
      "message": "Identity verified"
    },
    "fraudDetection": {
      "riskLevel": "MEDIUM",
      "riskScore": 52.3,
      "indicators": ["HIGH_CLAIM_AMOUNT"],
      "recommendation": "REVIEW"
    },
    "policyValidation": {
      "isValid": true,
      "message": "Policy active",
      "coverageAmount": 100000.0
    }
  }
}
```

---

### Test Case 3: High-Risk Claim (Expected: REJECTED)

**Scenario**: Suspicious high-value claim flagged for fraud

**Test Data**:
```json
{
  "claimId": "CLM-003",
  "userId": "USR-789",
  "claimType": "ACCIDENT",
  "claimAmount": 150000.0,
  "description": "Very high value claim",
  "incidentDate": "2024-01-05"
}
```

**Expected Results**:
- **Identity Verification**: ✅ PASSED
- **Fraud Detection**: ❌ HIGH RISK (score > 70)
- **Policy Validation**: ✅ VALID (but claim too high)
- **Final Status**: ❌ **REJECTED**

**Why Rejected**:
- Very high claim amount ($150,000)
- Exceeds policy coverage ($100,000)
- Multiple fraud indicators
- High risk score

**Testing Steps**:
```bash
curl -X POST http://localhost:8080/claim-processing/api/claims/submit \
  -H "Content-Type: application/json" \
  -d '{
    "claimId": "CLM-003",
    "userId": "USR-789",
    "claimType": "ACCIDENT",
    "claimAmount": 150000.0,
    "description": "Very high value claim",
    "incidentDate": "2024-01-05"
  }'
```

**Expected Response**:
```json
{
  "claimId": "CLM-003",
  "status": "REJECTED",
  "message": "Claim rejected due to high fraud risk and insufficient coverage",
  "timestamp": "2024-01-20T10:30:00Z",
  "processingDetails": {
    "identityVerification": {
      "verified": true,
      "confidence": 88.0,
      "message": "Identity verified"
    },
    "fraudDetection": {
      "riskLevel": "HIGH",
      "riskScore": 85.7,
      "indicators": [
        "VERY_HIGH_AMOUNT",
        "EXCEEDS_COVERAGE",
        "SUSPICIOUS_PATTERN"
      ],
      "recommendation": "REJECT"
    },
    "policyValidation": {
      "isValid": false,
      "message": "Claim amount ($150,000) exceeds policy coverage ($100,000)",
      "coverageAmount": 100000.0
    }
  }
}
```

---

### Test Case 4: Identity Verification Failure (Expected: REJECTED)

**Scenario**: User identity cannot be verified

**Test Data**:
```json
{
  "claimId": "CLM-004",
  "userId": "USR-999",
  "claimType": "AUTO",
  "claimAmount": 3000.0,
  "description": "Small claim",
  "incidentDate": "2024-01-18"
}
```

**Expected Results**:
- **Identity Verification**: ❌ FAILED (confidence < 70%)
- **Fraud Detection**: Not executed (early rejection)
- **Policy Validation**: Not executed (early rejection)
- **Final Status**: ❌ **REJECTED**

**Why Rejected**:
- Identity verification failed immediately
- No further processing needed
- User credentials not valid

---

### Test Case 5: Invalid Policy (Expected: REJECTED)

**Scenario**: Policy is expired or inactive

**Test Data**:
```json
{
  "claimId": "CLM-005",
  "userId": "USR-555",
  "claimType": "AUTO",
  "claimAmount": 2000.0,
  "description": "Minor claim",
  "incidentDate": "2024-01-19"
}
```

**Expected Results**:
- **Identity Verification**: ✅ PASSED
- **Fraud Detection**: ✅ LOW RISK
- **Policy Validation**: ❌ INVALID (policy expired/inactive)
- **Final Status**: ❌ **REJECTED**

---

## Individual Service Testing

### 1. REST API Testing

**Prerequisites**:
- ✅ Tomcat must be running (`start-tomcat.bat`)

#### Test 1: Submit Claim

**cURL Command**:
```bash
curl -X POST http://localhost:8080/claim-processing/api/claims/submit \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "USR-123",
    "claimType": "AUTO",
    "claimAmount": 5000.0,
    "description": "Car accident",
    "incidentDate": "2024-01-15"
  }'
```

**PowerShell Command**:
```powershell
$body = @{
    userId = "USR-123"
    claimType = "AUTO"
    claimAmount = 5000.0
    description = "Car accident"
    incidentDate = "2024-01-15"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/claim-processing/api/claims/submit" `
  -Method Post `
  -ContentType "application/json" `
  -Body $body
```

**Java Client**:
```bash
mvn exec:java -Dexec.mainClass="com.insurance.client.RestClient"
```

#### Test 2: Health Check

**cURL Command**:
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

---

### 2. SOAP Service Testing

**Prerequisites**:
- ✅ No additional services needed (SOAP uses in-memory implementation)

#### Test: Identity Verification

**Java Client** (Recommended):
```bash
mvn exec:java -Dexec.mainClass="com.insurance.client.SoapClient"
```

**Expected Output**:
```
=== SOAP Identity Verification Test ===

Testing with User: USR-123
Name: John Doe
Document ID: ID-789456123

Verification Result:
- Verified: true
- Confidence: 95.5%
- Message: Identity verified successfully. High confidence match.

✓ SOAP test completed successfully
```

**Raw SOAP Request** (using SoapUI or similar):
```xml
POST http://localhost:8080/claim-processing/soap/identity
Content-Type: text/xml

<?xml version="1.0" encoding="UTF-8"?>
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
               xmlns:iden="http://soap.insurance.com/">
  <soap:Header/>
  <soap:Body>
    <iden:verifyIdentity>
      <userId>USR-123</userId>
      <name>John Doe</name>
      <documentId>ID-789456123</documentId>
    </iden:verifyIdentity>
  </soap:Body>
</soap:Envelope>
```

---

### 3. gRPC Service Testing

**Prerequisites**:
- ✅ gRPC server must be running (`start-grpc-server.bat`)

#### Test: Fraud Detection

**Java Client** (Recommended):
```bash
# Terminal 1: Start gRPC server
start-grpc-server.bat

# Terminal 2: Run test
mvn exec:java -Dexec.mainClass="com.insurance.client.GrpcClient"
```

**Expected Output**:
```
=== gRPC Fraud Detection Test ===

Starting gRPC server on port 50051...
gRPC Server started successfully

Testing Claim Analysis...
Claim ID: CLM-001
User ID: USR-123
Claim Type: AUTO
Amount: $5000.00

Fraud Detection Result:
- Risk Level: LOW
- Risk Score: 15.2
- Indicators: []
- Recommendation: APPROVE
- Details: Claim amount within normal range. No fraud indicators detected.

✓ gRPC test completed successfully
```

**Using BloomRPC** (GUI tool):
1. Import proto file: `src/main/proto/fraud_detection.proto`
2. Connect to: `localhost:50051`
3. Select method: `FraudDetectionService.AnalyzeClaim`
4. Input:
```json
{
  "claim_id": "CLM-001",
  "user_id": "USR-123",
  "claim_type": "AUTO",
  "claim_amount": 5000.0,
  "description": "Car accident"
}
```
5. Click **Send**

---

### 4. GraphQL Service Testing

**Prerequisites**:
- ✅ Tomcat must be running (`start-tomcat.bat`)

#### Test 1: Get Single Policy

**cURL Command**:
```bash
curl -X POST http://localhost:8080/claim-processing/graphql \
  -H "Content-Type: application/json" \
  -d '{
    "query": "{ policy(policyId: \"POL-001\") { policyId userId policyType coverageAmount status } }"
  }'
```

**PowerShell Command**:
```powershell
$query = @{
    query = "{ policy(policyId: `"POL-001`") { policyId userId policyType coverageAmount status } }"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/claim-processing/graphql" `
  -Method Post `
  -ContentType "application/json" `
  -Body $query
```

**Java Client**:
```bash
mvn exec:java -Dexec.mainClass="com.insurance.client.GraphQLClient"
```

**Expected Output**:
```json
{
  "data": {
    "policy": {
      "policyId": "POL-001",
      "userId": "USR-123",
      "policyType": "AUTO",
      "coverageAmount": 100000.0,
      "status": "ACTIVE"
    }
  }
}
```

#### Test 2: Validate Policy

**cURL Command**:
```bash
curl -X POST http://localhost:8080/claim-processing/graphql \
  -H "Content-Type: application/json" \
  -d '{
    "query": "{ validatePolicy(policyId: \"POL-001\", claimAmount: 5000.0) { isValid message coverageAmount } }"
  }'
```

**Expected Output**:
```json
{
  "data": {
    "validatePolicy": {
      "isValid": true,
      "message": "Policy is active and covers the claim amount.",
      "coverageAmount": 100000.0
    }
  }
}
```

#### Test 3: Get All Policies

**cURL Command**:
```bash
curl -X POST http://localhost:8080/claim-processing/graphql \
  -H "Content-Type: application/json" \
  -d '{
    "query": "{ allPolicies { policyId userId policyType status } }"
  }'
```

---

## Integration Testing

### Full End-to-End Test

**Purpose**: Test complete claim processing pipeline with all services

**Prerequisites**:
- ✅ Tomcat running (`start-tomcat.bat`)
- ✅ gRPC server running (`start-grpc-server.bat`)

**Test Execution**:

1. **Using Demo Script** (Easiest):
```bash
run-demo.bat
# Select option 5: Run All Tests
```

2. **Using REST Client**:
```bash
mvn exec:java -Dexec.mainClass="com.insurance.client.RestClient"
```

3. **Using cURL**:
```bash
curl -X POST http://localhost:8080/claim-processing/api/claims/submit \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "USR-123",
    "claimType": "AUTO",
    "claimAmount": 5000.0,
    "description": "Car accident",
    "incidentDate": "2024-01-15"
  }'
```

**What Gets Tested**:
```
1. REST API receives claim request
   ↓
2. Orchestrator coordinates services:
   ├── SOAP: Identity verification
   ├── gRPC: Fraud detection
   └── GraphQL: Policy validation
   ↓
3. Results aggregated and decision made
   ↓
4. Response returned with all details
```

**Expected Complete Response**:
```json
{
  "claimId": "CLM-001",
  "status": "APPROVED",
  "message": "Claim approved successfully",
  "timestamp": "2024-01-20T10:30:00Z",
  "processingDetails": {
    "identityVerification": {
      "verified": true,
      "confidence": 95.5,
      "message": "Identity verified successfully"
    },
    "fraudDetection": {
      "riskLevel": "LOW",
      "riskScore": 15.2,
      "indicators": [],
      "recommendation": "APPROVE"
    },
    "policyValidation": {
      "isValid": true,
      "message": "Policy is active and covers claim amount",
      "coverageAmount": 100000.0
    }
  }
}
```

**Verification Checklist**:
- ✅ All three services were called
- ✅ Identity verification passed
- ✅ Fraud detection completed
- ✅ Policy validation successful
- ✅ Final decision is APPROVED
- ✅ All processing details included in response

---

## Troubleshooting

### Issue 1: Tomcat Not Starting

**Symptoms**:
- `start-tomcat.bat` fails
- Error: "Port 8080 already in use"

**Solutions**:

1. **Check if port 8080 is already in use**:
```bash
netstat -ano | findstr :8080
```

2. **Kill process using port 8080**:
```bash
# Find PID from netstat output, then:
taskkill /PID <PID> /F
```

3. **Verify TOMCAT_HOME path**:
- Edit `start-tomcat.bat`
- Ensure path matches your Tomcat installation
- Example: `set TOMCAT_HOME=C:\apache-tomcat-9.0.89`

4. **Check Tomcat logs**:
```
%TOMCAT_HOME%\logs\catalina.out
%TOMCAT_HOME%\logs\localhost.log
```

---

### Issue 2: gRPC Server Connection Failed

**Symptoms**:
- gRPC client cannot connect
- Error: "Connection refused" or "UNAVAILABLE"

**Solutions**:

1. **Verify gRPC server is running**:
```bash
# Check if server started successfully
# Look for: "gRPC Server started on port 50051"
```

2. **Check if port 50051 is available**:
```bash
netstat -ano | findstr :50051
```

3. **Restart gRPC server**:
```bash
# Close existing server (Ctrl+C)
start-grpc-server.bat
```

4. **Firewall check**:
- Ensure Windows Firewall allows port 50051
- Or temporarily disable firewall for testing

---

### Issue 3: Maven Build Fails

**Symptoms**:
- `mvn clean compile` fails
- Dependency download errors

**Solutions**:

1. **Check internet connection**:
- Maven needs to download dependencies
- Ensure proxy settings if behind corporate network

2. **Clear Maven cache**:
```bash
mvn clean
# Or delete: C:\Users\<username>\.m2\repository
```

3. **Update Maven**:
```bash
mvn -version
# Ensure Maven 3.6+ is installed
```

4. **Check pom.xml**:
- Ensure all dependencies are valid
- Verify repository URLs

---

### Issue 4: WAR Deployment Fails

**Symptoms**:
- `build-and-deploy.bat` succeeds but service not accessible
- 404 error when accessing endpoints

**Solutions**:

1. **Verify WAR file exists**:
```bash
# Check target folder
dir target\claim-processing.war
```

2. **Check Tomcat webapps folder**:
```bash
dir %TOMCAT_HOME%\webapps\claim-processing.war
```

3. **Check Tomcat deployment logs**:
```
%TOMCAT_HOME%\logs\localhost.log
# Look for deployment success/failure messages
```

4. **Redeploy manually**:
```bash
# Stop Tomcat
stop-tomcat.bat

# Delete old deployment
del %TOMCAT_HOME%\webapps\claim-processing.war
rmdir /s /q %TOMCAT_HOME%\webapps\claim-processing

# Copy new WAR
copy target\claim-processing.war %TOMCAT_HOME%\webapps\

# Start Tomcat
start-tomcat.bat
```

---

### Issue 5: REST API Returns 404

**Symptoms**:
- cURL/Postman returns 404 Not Found
- Tomcat is running

**Solutions**:

1. **Verify correct URL**:
```
✓ Correct: http://localhost:8080/claim-processing/api/claims/submit
✗ Wrong:   http://localhost:8080/api/claims/submit
```

2. **Check if WAR is deployed**:
```bash
# Look for claim-processing folder in webapps
dir %TOMCAT_HOME%\webapps\claim-processing
```

3. **Check servlet mapping in web.xml**:
- Verify URL patterns match
- Ensure Jersey servlet is configured

4. **Restart Tomcat**:
```bash
stop-tomcat.bat
start-tomcat.bat
```

---

### Issue 6: GraphQL Schema Not Found

**Symptoms**:
- GraphQL endpoint returns error
- "Schema not found" message

**Solutions**:

1. **Verify schema.graphql exists**:
```bash
# Should be in: src/main/resources/schema.graphql
dir src\main\resources\schema.graphql
```

2. **Rebuild project**:
```bash
mvn clean package
```

3. **Check WAR contents**:
```bash
# Extract and verify schema.graphql is included
jar -tf target\claim-processing.war | findstr schema.graphql
```

4. **Verify resource loading in GraphQLServlet.java**:
- Check if schema file path is correct
- Ensure resource loading code is correct

---

### Issue 7: SOAP Service Returns Fault

**Symptoms**:
- SOAP requests return SOAP Fault
- XML parsing errors

**Solutions**:

1. **Verify SOAP request format**:
- Ensure proper XML structure
- Check namespace declarations
- Verify element names match WSDL

2. **Check WSDL**:
```
http://localhost:8080/claim-processing/soap/identity?wsdl
```

3. **Validate XML**:
- Use XML validator
- Ensure no special characters unescaped

4. **Check server logs**:
- Look for detailed error messages
- Check stack traces

---

### Issue 8: Protocol Buffer Compilation Fails

**Symptoms**:
- gRPC classes not generated
- Import errors for protobuf classes

**Solutions**:

1. **Verify proto file exists**:
```bash
dir src\main\proto\fraud_detection.proto
```

2. **Run protobuf compilation**:
```bash
mvn clean compile
# This should generate gRPC classes
```

3. **Check generated files**:
```bash
dir target\generated-sources\protobuf\java\
dir target\generated-sources\protobuf\grpc-java\
```

4. **Verify Maven plugin**:
- Check pom.xml has protobuf-maven-plugin
- Ensure correct version

---

### Issue 9: Test Client Hangs

**Symptoms**:
- Maven exec command hangs
- No output displayed

**Solutions**:

1. **Check if required services are running**:
- gRPC client needs gRPC server
- GraphQL/REST clients need Tomcat

2. **Increase timeout**:
- Modify client code to add timeout
- Or wait longer for initial connection

3. **Check network connectivity**:
```bash
# Test if services are reachable
curl http://localhost:8080/claim-processing/api/claims/health
```

4. **Run with debug**:
```bash
mvn exec:java -Dexec.mainClass="com.insurance.client.RestClient" -X
```

---

### Issue 10: Permission Denied Errors

**Symptoms**:
- Cannot write to Tomcat directories
- Batch files fail with access denied

**Solutions**:

1. **Run as Administrator**:
- Right-click batch file
- Select "Run as administrator"

2. **Check folder permissions**:
- Ensure write access to Tomcat directories
- Check target folder permissions

3. **Disable antivirus temporarily**:
- Some antivirus software blocks file operations
- Whitelist project folders

---

## Test Checklist

Before considering testing complete, verify:

### Individual Services
- ✅ SOAP service responds correctly
- ✅ gRPC server starts and accepts connections
- ✅ GraphQL queries return expected data
- ✅ REST API accepts and processes requests

### Integration
- ✅ All services can be called together
- ✅ Orchestrator coordinates correctly
- ✅ Error handling works as expected
- ✅ Response format is correct

### Test Scenarios
- ✅ Low-risk claim approved
- ✅ Medium-risk claim sent to manual review
- ✅ High-risk claim rejected
- ✅ Invalid identity rejected
- ✅ Expired policy rejected

### Performance
- ✅ Response time < 5 seconds
- ✅ All services respond within timeout
- ✅ No memory leaks during multiple requests

### Error Handling
- ✅ Invalid input handled gracefully
- ✅ Service unavailable handled properly
- ✅ Error messages are clear and helpful

---

## Next Steps

After successful testing:

1. **Review logs** for any warnings or errors
2. **Document test results** for your report
3. **Take screenshots** of successful tests
4. **Prepare demo** for presentation
5. **Create test report** with metrics

---

**Document Version**: 1.0
**Last Updated**: January 2026
**Author**: Insurance Claim Processing Team
**Course**: Service Oriented Computing
**Institution**: Télécom SudParis
