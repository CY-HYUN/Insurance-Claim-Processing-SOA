# Insurance Claim Processing SOA - Service Endpoints

## Table of Contents
- [REST API Endpoints](#rest-api-endpoints)
- [SOAP Service Methods](#soap-service-methods)
- [gRPC Service Methods](#grpc-service-methods)
- [GraphQL Queries](#graphql-queries)
- [Request/Response Examples](#requestresponse-examples)
- [Error Codes](#error-codes)

---

## REST API Endpoints

### Base URL
```
http://localhost:8080/claim-processing/api
```

### 1. Submit Claim

**Endpoint**: `POST /claims/submit`

**Description**: Submit a new insurance claim for processing

**Request Headers**:
```
Content-Type: application/json
Accept: application/json
```

**Request Body**:
```json
{
  "claimId": "string (optional, auto-generated if not provided)",
  "userId": "string (required)",
  "claimType": "string (required) - AUTO, HOME, HEALTH, LIFE",
  "claimAmount": "number (required) - positive value",
  "description": "string (required)",
  "incidentDate": "string (required) - format: YYYY-MM-DD"
}
```

**Response** (200 OK):
```json
{
  "claimId": "CLM-001",
  "status": "APPROVED | REJECTED | MANUAL_REVIEW",
  "message": "Detailed processing result",
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

**Response** (400 Bad Request):
```json
{
  "error": "Invalid request",
  "message": "Missing required field: userId",
  "timestamp": "2024-01-20T10:30:00Z"
}
```

**Response** (500 Internal Server Error):
```json
{
  "error": "Internal server error",
  "message": "Service temporarily unavailable",
  "timestamp": "2024-01-20T10:30:00Z"
}
```

---

### 2. Get Claim Status

**Endpoint**: `GET /claims/{claimId}`

**Description**: Retrieve the status of a specific claim

**Path Parameters**:
- `claimId` (string, required) - The unique claim identifier

**Request Headers**:
```
Accept: application/json
```

**Response** (200 OK):
```json
{
  "claimId": "CLM-001",
  "status": "APPROVED",
  "submittedDate": "2024-01-15T14:30:00Z",
  "processedDate": "2024-01-15T14:30:05Z",
  "amount": 5000.0
}
```

**Response** (404 Not Found):
```json
{
  "error": "Claim not found",
  "message": "No claim found with ID: CLM-999",
  "timestamp": "2024-01-20T10:30:00Z"
}
```

---

### 3. Health Check

**Endpoint**: `GET /claims/health`

**Description**: Check if the REST service is running

**Request Headers**:
```
Accept: application/json
```

**Response** (200 OK):
```json
{
  "status": "UP",
  "service": "ClaimSubmissionService",
  "timestamp": "2024-01-20T10:30:00Z",
  "uptime": "2 hours 15 minutes"
}
```

---

## SOAP Service Methods

### Service Endpoint

**Service Name**: `IdentityVerificationService`

**WSDL URL**: `http://localhost:8080/claim-processing/soap/identity?wsdl`

**Namespace**: `http://soap.insurance.com/`

**Service Class**: `com.insurance.soap.IdentityVerificationService`

---

### 1. verifyIdentity

**Method Signature**:
```java
@WebMethod
public VerificationResult verifyIdentity(
    @WebParam(name = "userId") String userId,
    @WebParam(name = "name") String name,
    @WebParam(name = "documentId") String documentId
)
```

**Description**: Verify user identity using government-issued ID

**Parameters**:
- `userId` (String, required) - Unique user identifier
- `name` (String, required) - Full name of the user
- `documentId` (String, required) - Government ID number

**SOAP Request**:
```xml
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

**SOAP Response**:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
  <soap:Body>
    <verifyIdentityResponse xmlns="http://soap.insurance.com/">
      <VerificationResult>
        <verified>true</verified>
        <confidence>95.5</confidence>
        <message>Identity verified successfully. High confidence match.</message>
      </VerificationResult>
    </verifyIdentityResponse>
  </soap:Body>
</soap:Envelope>
```

**Return Object** (VerificationResult):
```java
{
  "verified": boolean,        // true if identity verified
  "confidence": double,        // 0-100 confidence score
  "message": String            // Detailed verification message
}
```

**Verification Logic**:
- **High Confidence (90-100%)**: All details match perfectly
- **Medium Confidence (70-89%)**: Minor discrepancies
- **Low Confidence (< 70%)**: Significant mismatches → Failed verification

---

### 2. getServiceStatus

**Method Signature**:
```java
@WebMethod
public String getServiceStatus()
```

**Description**: Get the current status of the identity verification service

**Parameters**: None

**SOAP Request**:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
               xmlns:iden="http://soap.insurance.com/">
  <soap:Header/>
  <soap:Body>
    <iden:getServiceStatus/>
  </soap:Body>
</soap:Envelope>
```

**SOAP Response**:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
  <soap:Body>
    <getServiceStatusResponse xmlns="http://soap.insurance.com/">
      <return>Identity Verification Service is ACTIVE</return>
    </getServiceStatusResponse>
  </soap:Body>
</soap:Envelope>
```

**Return**: String - Service status message

---

## gRPC Service Methods

### Service Configuration

**Server Address**: `localhost:50051`

**Proto File**: `src/main/proto/fraud_detection.proto`

**Service Name**: `FraudDetectionService`

**Package**: `com.insurance.grpc`

---

### Proto Definition

```protobuf
syntax = "proto3";

package fraud;

option java_multiple_files = true;
option java_package = "com.insurance.grpc";
option java_outer_classname = "FraudDetectionProto";

service FraudDetectionService {
  rpc AnalyzeClaim (FraudRequest) returns (FraudResponse);
  rpc GetStatistics (StatisticsRequest) returns (StatisticsResponse);
}

message FraudRequest {
  string claim_id = 1;
  string user_id = 2;
  string claim_type = 3;
  double claim_amount = 4;
  string description = 5;
}

message FraudResponse {
  string claim_id = 1;
  string risk_level = 2;      // LOW, MEDIUM, HIGH
  double risk_score = 3;       // 0-100
  repeated string indicators = 4;
  string recommendation = 5;   // APPROVE, REVIEW, REJECT
  string analysis_details = 6;
}

message StatisticsRequest {
  string time_period = 1;      // DAILY, WEEKLY, MONTHLY
}

message StatisticsResponse {
  int32 total_claims_analyzed = 1;
  int32 high_risk_claims = 2;
  int32 medium_risk_claims = 3;
  int32 low_risk_claims = 4;
  double average_risk_score = 5;
}
```

---

### 1. AnalyzeClaim

**RPC Method**:
```protobuf
rpc AnalyzeClaim (FraudRequest) returns (FraudResponse);
```

**Description**: Analyze claim for fraud risk and suspicious patterns

**Request (FraudRequest)**:
```json
{
  "claim_id": "CLM-001",
  "user_id": "USR-123",
  "claim_type": "AUTO",
  "claim_amount": 5000.0,
  "description": "Car accident"
}
```

**Response (FraudResponse)**:
```json
{
  "claim_id": "CLM-001",
  "risk_level": "LOW",
  "risk_score": 15.2,
  "indicators": [],
  "recommendation": "APPROVE",
  "analysis_details": "Claim amount within normal range. No fraud indicators detected."
}
```

**Risk Levels**:
- **LOW**: Risk score < 30
  - Amount < $10,000
  - No suspicious patterns
  - Normal claim characteristics
  - **Recommendation**: APPROVE

- **MEDIUM**: Risk score 30-70
  - Amount $10,000 - $75,000
  - 1-2 minor indicators
  - Slightly unusual patterns
  - **Recommendation**: REVIEW

- **HIGH**: Risk score > 70
  - Amount > $75,000
  - Multiple red flags
  - Suspicious patterns detected
  - **Recommendation**: REJECT

**Fraud Indicators**:
- Very high claim amount (> $75,000)
- Recent similar claims by same user
- Claim amount close to policy limit
- Unusual claim timing
- Incomplete documentation
- Inconsistent claim details

---

### 2. GetStatistics

**RPC Method**:
```protobuf
rpc GetStatistics (StatisticsRequest) returns (StatisticsResponse);
```

**Description**: Get fraud detection statistics for a time period

**Request (StatisticsRequest)**:
```json
{
  "time_period": "DAILY"  // DAILY, WEEKLY, MONTHLY
}
```

**Response (StatisticsResponse)**:
```json
{
  "total_claims_analyzed": 150,
  "high_risk_claims": 5,
  "medium_risk_claims": 25,
  "low_risk_claims": 120,
  "average_risk_score": 22.5
}
```

---

## GraphQL Queries

### Endpoint

**URL**: `http://localhost:8080/claim-processing/graphql`

**Method**: POST

**Headers**:
```
Content-Type: application/json
Accept: application/json
```

---

### Schema Definition

```graphql
type Policy {
  policyId: String!
  userId: String!
  policyType: String!      # AUTO, HOME, HEALTH, LIFE
  coverageAmount: Float!
  deductible: Float!
  status: String!          # ACTIVE, EXPIRED, CANCELLED
  startDate: String!
  endDate: String!
}

type ValidationResult {
  isValid: Boolean!
  message: String!
  coverageAmount: Float
}

type Query {
  policy(policyId: String!): Policy
  policiesByUser(userId: String!): [Policy]
  allPolicies: [Policy]
  validatePolicy(policyId: String!, claimAmount: Float!): ValidationResult
}
```

---

### 1. Get Single Policy

**Query**:
```graphql
query {
  policy(policyId: "POL-001") {
    policyId
    userId
    policyType
    coverageAmount
    deductible
    status
    startDate
    endDate
  }
}
```

**HTTP Request**:
```json
{
  "query": "query { policy(policyId: \"POL-001\") { policyId userId policyType coverageAmount status } }"
}
```

**Response**:
```json
{
  "data": {
    "policy": {
      "policyId": "POL-001",
      "userId": "USR-123",
      "policyType": "AUTO",
      "coverageAmount": 100000.0,
      "deductible": 1000.0,
      "status": "ACTIVE",
      "startDate": "2023-01-01",
      "endDate": "2025-12-31"
    }
  }
}
```

---

### 2. Get Policies by User

**Query**:
```graphql
query {
  policiesByUser(userId: "USR-123") {
    policyId
    policyType
    coverageAmount
    status
  }
}
```

**HTTP Request**:
```json
{
  "query": "query { policiesByUser(userId: \"USR-123\") { policyId policyType coverageAmount status } }"
}
```

**Response**:
```json
{
  "data": {
    "policiesByUser": [
      {
        "policyId": "POL-001",
        "policyType": "AUTO",
        "coverageAmount": 100000.0,
        "status": "ACTIVE"
      },
      {
        "policyId": "POL-002",
        "policyType": "HOME",
        "coverageAmount": 250000.0,
        "status": "ACTIVE"
      }
    ]
  }
}
```

---

### 3. Get All Policies

**Query**:
```graphql
query {
  allPolicies {
    policyId
    userId
    policyType
    status
  }
}
```

**HTTP Request**:
```json
{
  "query": "query { allPolicies { policyId userId policyType status } }"
}
```

**Response**:
```json
{
  "data": {
    "allPolicies": [
      {
        "policyId": "POL-001",
        "userId": "USR-123",
        "policyType": "AUTO",
        "status": "ACTIVE"
      },
      {
        "policyId": "POL-002",
        "userId": "USR-123",
        "policyType": "HOME",
        "status": "ACTIVE"
      },
      {
        "policyId": "POL-003",
        "userId": "USR-456",
        "policyType": "HEALTH",
        "status": "ACTIVE"
      }
    ]
  }
}
```

---

### 4. Validate Policy for Claim

**Query**:
```graphql
query {
  validatePolicy(policyId: "POL-001", claimAmount: 5000.0) {
    isValid
    message
    coverageAmount
  }
}
```

**HTTP Request**:
```json
{
  "query": "query { validatePolicy(policyId: \"POL-001\", claimAmount: 5000.0) { isValid message coverageAmount } }"
}
```

**Response (Valid)**:
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

**Response (Invalid - Amount Exceeds Coverage)**:
```json
{
  "data": {
    "validatePolicy": {
      "isValid": false,
      "message": "Claim amount ($150,000.00) exceeds policy coverage ($100,000.00)",
      "coverageAmount": 100000.0
    }
  }
}
```

**Response (Invalid - Policy Not Active)**:
```json
{
  "data": {
    "validatePolicy": {
      "isValid": false,
      "message": "Policy POL-999 is not active (Status: EXPIRED)",
      "coverageAmount": 100000.0
    }
  }
}
```

**Response (Invalid - Policy Not Found)**:
```json
{
  "data": {
    "validatePolicy": {
      "isValid": false,
      "message": "Policy not found: POL-999",
      "coverageAmount": null
    }
  }
}
```

---

## Request/Response Examples

### Complete Claim Submission Flow

#### Step 1: Submit Claim (REST)

**Request**:
```bash
curl -X POST http://localhost:8080/claim-processing/api/claims/submit \
  -H "Content-Type: application/json" \
  -d '{
    "claimId": "CLM-001",
    "userId": "USR-123",
    "claimType": "AUTO",
    "claimAmount": 5000.0,
    "description": "Car accident on highway",
    "incidentDate": "2024-01-15"
  }'
```

**Response**:
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

---

#### Step 2: Verify Identity (SOAP)

**Request** (via Java Client):
```java
VerificationResult result = soapService.verifyIdentity(
    "USR-123",
    "John Doe",
    "ID-789456123"
);
```

**Raw SOAP Request**:
```xml
<soap:Envelope>
  <soap:Body>
    <iden:verifyIdentity>
      <userId>USR-123</userId>
      <name>John Doe</name>
      <documentId>ID-789456123</documentId>
    </iden:verifyIdentity>
  </soap:Body>
</soap:Envelope>
```

**Raw SOAP Response**:
```xml
<soap:Envelope>
  <soap:Body>
    <verifyIdentityResponse>
      <VerificationResult>
        <verified>true</verified>
        <confidence>95.5</confidence>
        <message>Identity verified successfully</message>
      </VerificationResult>
    </verifyIdentityResponse>
  </soap:Body>
</soap:Envelope>
```

---

#### Step 3: Detect Fraud (gRPC)

**Request** (via gRPC Client):
```java
FraudRequest request = FraudRequest.newBuilder()
    .setClaimId("CLM-001")
    .setUserId("USR-123")
    .setClaimType("AUTO")
    .setClaimAmount(5000.0)
    .setDescription("Car accident on highway")
    .build();

FraudResponse response = grpcStub.analyzeClaim(request);
```

**Protobuf Request** (binary format):
```
claim_id: "CLM-001"
user_id: "USR-123"
claim_type: "AUTO"
claim_amount: 5000.0
description: "Car accident on highway"
```

**Protobuf Response**:
```
claim_id: "CLM-001"
risk_level: "LOW"
risk_score: 15.2
indicators: []
recommendation: "APPROVE"
analysis_details: "Claim amount within normal range"
```

---

#### Step 4: Validate Policy (GraphQL)

**Request**:
```bash
curl -X POST http://localhost:8080/claim-processing/graphql \
  -H "Content-Type: application/json" \
  -d '{
    "query": "query { validatePolicy(policyId: \"POL-001\", claimAmount: 5000.0) { isValid message coverageAmount } }"
  }'
```

**Response**:
```json
{
  "data": {
    "validatePolicy": {
      "isValid": true,
      "message": "Policy is active and covers the claim amount",
      "coverageAmount": 100000.0
    }
  }
}
```

---

## Error Codes

### HTTP Status Codes

| Code | Status | Description |
|------|--------|-------------|
| 200 | OK | Request successful |
| 201 | Created | Resource created successfully |
| 400 | Bad Request | Invalid request parameters |
| 401 | Unauthorized | Authentication required |
| 403 | Forbidden | Insufficient permissions |
| 404 | Not Found | Resource not found |
| 500 | Internal Server Error | Server error occurred |
| 503 | Service Unavailable | Service temporarily unavailable |

---

### Application Error Codes

| Code | Message | Description |
|------|---------|-------------|
| ERR_001 | Invalid claim request | Required fields missing or invalid |
| ERR_002 | Identity verification failed | User identity could not be verified |
| ERR_003 | High fraud risk detected | Claim flagged for fraud review |
| ERR_004 | Policy validation failed | Policy not active or insufficient coverage |
| ERR_005 | Service unavailable | External service not responding |
| ERR_006 | Invalid policy ID | Policy does not exist |
| ERR_007 | Insufficient coverage | Claim amount exceeds policy coverage |
| ERR_008 | Claim amount invalid | Claim amount must be positive |
| ERR_009 | Invalid date format | Date must be in YYYY-MM-DD format |
| ERR_010 | Unknown claim type | Claim type must be AUTO, HOME, HEALTH, or LIFE |

---

### SOAP Fault Codes

```xml
<soap:Fault>
  <faultcode>soap:Server</faultcode>
  <faultstring>Identity verification service error</faultstring>
  <detail>
    <errorCode>ERR_002</errorCode>
    <message>User ID not found in system</message>
  </detail>
</soap:Fault>
```

---

### gRPC Status Codes

| Code | Status | Description |
|------|--------|-------------|
| 0 | OK | Success |
| 2 | UNKNOWN | Unknown error |
| 3 | INVALID_ARGUMENT | Invalid request parameters |
| 5 | NOT_FOUND | Resource not found |
| 14 | UNAVAILABLE | Service unavailable |

---

## Testing Tools

### Postman Collection

Import the Postman collection for easy testing:
```
docs/API_Documentation/Insurance_Claim_Processing.postman_collection.json
```

Includes pre-configured requests for:
- ✅ Submit claim (REST)
- ✅ Get claim status (REST)
- ✅ Health check (REST)
- ✅ GraphQL queries (all 4 query types)

---

### cURL Examples

**Submit Claim**:
```bash
curl -X POST http://localhost:8080/claim-processing/api/claims/submit \
  -H "Content-Type: application/json" \
  -d '{"userId":"USR-123","claimType":"AUTO","claimAmount":5000,"description":"Test","incidentDate":"2024-01-15"}'
```

**GraphQL Query**:
```bash
curl -X POST http://localhost:8080/claim-processing/graphql \
  -H "Content-Type: application/json" \
  -d '{"query":"{ policy(policyId:\"POL-001\") { policyId status } }"}'
```

---

### Java Test Clients

Run individual service tests:

```bash
# SOAP Test
mvn exec:java -Dexec.mainClass="com.insurance.client.SoapClient"

# gRPC Test (requires gRPC server running)
mvn exec:java -Dexec.mainClass="com.insurance.client.GrpcClient"

# GraphQL Test (requires Tomcat running)
mvn exec:java -Dexec.mainClass="com.insurance.client.GraphQLClient"

# REST Test (requires Tomcat running)
mvn exec:java -Dexec.mainClass="com.insurance.client.RestClient"
```

---

**Document Version**: 1.0
**Last Updated**: January 2026
**Author**: Insurance Claim Processing Team
**Course**: Service Oriented Computing
**Institution**: Télécom SudParis
