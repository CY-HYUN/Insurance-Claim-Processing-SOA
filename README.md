# Insurance Claim Processing - Service Oriented Architecture

**End-to-End Insurance Claim Processing System with Multi-Protocol Service Integration**

A comprehensive demonstration of Service Oriented Architecture implementing **four different communication protocols** for automated insurance claim processing. This project showcases professional-grade microservices design, workflow orchestration, and modern distributed system architecture.

## 🎯 Project Overview

### Business Context

An enterprise insurance platform offering a **digital service** that allows customers to submit, process, and track insurance claims through an automated workflow. The system orchestrates multiple microservices using different communication protocols to demonstrate SOA principles and modern distributed system patterns.

### Technology Highlights

This project implements **4 core services** using **4 different service technologies**:

- **REST (Jersey 2.35)** - Claim Submission Service with JSON
- **SOAP (JAX-WS)** - Identity Verification Service with WSDL
- **gRPC (1.58.0)** - Fraud Detection Service with Protocol Buffers
- **GraphQL (19.2)** - Policy Validation Service with flexible queries

**Additional Features:**
- Complete workflow orchestration with XOR gateway logic
- 4 Java application clients for service testing
- Comprehensive API documentation (Postman, WSDL, Proto, GraphQL schema)
- End-to-end demonstration with approval and rejection scenarios

### Service Technology Selection Rationale

| Service | Technology | Justification |
|---------|-----------|---------------|
| **Claim Submission** | REST (Jersey 2.35, JSON) | Lightweight, stateless, HTTP-based. Ideal for CRUD operations and web client integration. JSON format provides human-readable data exchange. |
| **Identity Verification** | SOAP (JAX-WS, XML) | Enterprise-grade security with WS-Security support. Formal WSDL contract ensures strict type safety. Standard for banking/insurance identity verification. |
| **Fraud Detection** | gRPC (Protocol Buffers) | High-performance binary protocol with low latency. Efficient for computation-intensive fraud analysis. Strongly-typed schema with backward compatibility. |
| **Policy Validation** | GraphQL | Flexible query capabilities allowing clients to request exactly the data needed. Reduces over-fetching. Single endpoint for complex policy data relationships. |

All services are orchestrated through `InsuranceClaimOrchestrator.java` to process insurance claims through a complete verification pipeline with **XOR gateway logic** for decision-based flow control.

## 🏗️ System Architecture

### High-Level Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                     Insurance Company Platform                   │
├─────────────────────────────────────────────────────────────────┤
│                                                                   │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │ Layer 1: Customer-Facing Services (REST)                 │  │
│  │  ┌────────────────────────────────────────────────────┐  │  │
│  │  │ Claim Submission Service (REST - Jersey 2.35)      │  │  │
│  │  │ • POST /api/claims/submit                          │  │  │
│  │  │ • GET /api/claims/{claimId}                        │  │  │
│  │  │ • JSON Request/Response                            │  │  │
│  │  └────────────────────────────────────────────────────┘  │  │
│  └──────────────────────────────────────────────────────────┘  │
│                              ↓                                    │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │ Layer 2: Orchestration Layer (Java Application)         │  │
│  │  ┌────────────────────────────────────────────────────┐  │  │
│  │  │ InsuranceClaimOrchestrator.java                    │  │  │
│  │  │ • Sequential Service Coordination                  │  │  │
│  │  │ • XOR Gateway Logic Implementation                 │  │  │
│  │  │ • Error Handling & Response Aggregation           │  │  │
│  │  └────────────────────────────────────────────────────┘  │  │
│  └──────────────────────────────────────────────────────────┘  │
│                              ↓                                    │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │ Layer 3: Verification Services (Multi-Protocol)         │  │
│  │                                                           │  │
│  │  ┌─────────────────────────────────────────┐            │  │
│  │  │ Identity Verification (SOAP - JAX-WS)   │            │  │
│  │  │ • WSDL: /services/IdentityVerification  │            │  │
│  │  │ • verifyIdentity(userId, name, docId)   │            │  │
│  │  └─────────────────────────────────────────┘            │  │
│  │                                                           │  │
│  │  ┌─────────────────────────────────────────┐            │  │
│  │  │ Fraud Detection (gRPC - Port 50051)     │            │  │
│  │  │ • AnalyzeClaim(FraudRequest)            │            │  │
│  │  │ • RiskAssessment: LOW/MEDIUM/HIGH       │            │  │
│  │  └─────────────────────────────────────────┘            │  │
│  │                                                           │  │
│  │  ┌─────────────────────────────────────────┐            │  │
│  │  │ Policy Validation (GraphQL)             │            │  │
│  │  │ • POST /graphql                         │            │  │
│  │  │ • validatePolicy(policyId, amount)      │            │  │
│  │  └─────────────────────────────────────────┘            │  │
│  └──────────────────────────────────────────────────────────┘  │
│                              ↓                                    │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │ Layer 4: Decision & Response                             │  │
│  │  • XOR Gateway 1: Identity Failed → REJECT               │  │
│  │  • XOR Gateway 2: High Fraud Risk → REJECT               │  │
│  │  • XOR Gateway 3: Invalid Policy → REJECT                │  │
│  │  • All Pass → APPROVE                                    │  │
│  └──────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
```

### Workflow Description

**Complete Insurance Claim Processing Workflow:**

1. **Claim Submission** (REST) - Customer submits claim with personal details, policy number, claim type, amount, description
2. **Identity Verification** (SOAP) - Customer identity verified against database
   - **XOR Gateway:** If verification fails → Claim REJECTED, customer notified
3. **Fraud Detection** (gRPC) - Claim analyzed for fraud risk (LOW/MEDIUM/HIGH)
   - **XOR Gateway:** If high risk detected → Claim REJECTED
4. **Policy Validation** (GraphQL) - Insurance policy checked for validity and coverage
   - **XOR Gateway:** If policy invalid or insufficient coverage → Claim REJECTED
5. **Final Decision** - If all checks pass → Claim APPROVED
6. **Customer Notification** - Customer informed of decision with detailed results

**Gateway Implementation:**
- **XOR Gateways** implemented as conditional logic in `InsuranceClaimOrchestrator.java`
- **Sequential Execution:** Services called in order with early termination on failure
- **Error Handling:** Comprehensive try-catch blocks with meaningful error messages

## 📋 Prerequisites

- Java Development Kit (JDK) 11+
- Apache Maven 3.6+
- Apache Tomcat 9.0+
- Internet connection (for Maven dependencies)

## 🚀 Quick Start

### 1. Configure Tomcat Path

Edit these batch files and set your Tomcat installation path:
- `build-and-deploy.bat`
- `start-tomcat.bat`
- `stop-tomcat.bat`

```batch
set TOMCAT_HOME=C:\apache-tomcat-9.0.89
```

### 2. Build the Project

**Option 1: Command Line (Maven)**
```bash
compile-classes.bat
```

This will:
- Clean previous builds
- Compile all Java sources with Maven
- Generate gRPC classes from .proto files
- Create compiled classes in `target/classes/`

**Option 2: IntelliJ IDEA**

1. Open the project in IntelliJ IDEA
2. Configure Java SDK (File → Project Structure → Project → SDK: Java 11)
3. Open Maven tool window (View → Tool Windows → Maven)
4. Execute Maven goals in order:
   - **clean** → Wait for completion
   - **compile** → Compiles Java sources and generates gRPC classes
   - **package** → Creates WAR file in `target/claim-processing.war`

### 3. Start Services

**Start Tomcat** (for REST, SOAP, and GraphQL):
```bash
start-tomcat.bat
```

**Start gRPC Server** (for Fraud Detection):
```bash
start-grpc-java.bat
```

### 4. Test the Services

```bash
run-demo-java.bat
```

**Menu Options:**
1. Test SOAP Identity Service
2. Test gRPC Fraud Service
3. Test GraphQL Policy Service
4. Test REST Claim Service
5. Run All Tests (Complete Workflow)
6. Exit

## 🔧 Service Details & Implementation

### 1. REST - Claim Submission Service

**Implementation:** `com.insurance.service.ClaimSubmissionService.java`

**Technology Stack:**
- JAX-RS (Jersey 2.35) - REST framework
- JSON format (Gson 2.10.1)
- HTTP methods: POST, GET
- Comprehensive error handling

**Endpoints:**
- **POST** `/api/claims/submit` - Submit new insurance claim
- **GET** `/api/claims/{claimId}` - Retrieve claim status
- **GET** `/api/claims/health` - Health check endpoint

**Base URL:** `http://localhost:8080/claim-processing/api/claims`

---

### 2. SOAP - Identity Verification Service

**Implementation:** `com.insurance.soap.IdentityVerificationService.java`

**Technology Stack:**
- JAX-WS (Java API for XML Web Services)
- WSDL auto-generation
- XML messaging format

**Web Service Methods:**
- **verifyIdentity(String userId, String customerName, String documentId)**
  - Verifies customer identity against database
  - Returns: VerificationResult (verified: boolean, message: String)

**WSDL URL:** `http://localhost:8080/claim-processing/services/IdentityVerification?wsdl`

---

### 3. gRPC - Fraud Detection Service

**Implementation:**
- Server: `com.insurance.grpc.FraudDetectionServer.java`
- Service: `com.insurance.grpc.FraudDetectionServiceImpl.java`
- Protocol Buffers: `src/main/proto/fraud_detection.proto`

**Technology Stack:**
- gRPC Java 1.58.0
- Protocol Buffers 3.24.0
- HTTP/2 protocol
- Port: 50051

**gRPC Methods:**
- **AnalyzeClaim(FraudRequest) → FraudResponse**
  - Analyzes claim data for fraud indicators
  - Returns: riskLevel (LOW/MEDIUM/HIGH), riskScore (0.0-1.0), reason

**Business Logic:**
- Amount > $100,000 → HIGH risk
- Amount $20,000-$100,000 → MEDIUM risk
- Amount < $20,000 → LOW risk

---

### 4. GraphQL - Policy Validation Service

**Implementation:**
- Schema: `src/main/resources/schema.graphql`
- Data Fetcher: `com.insurance.graphql.PolicyDataFetcher.java`
- Servlet: `com.insurance.graphql.GraphQLServlet.java`

**Technology Stack:**
- graphql-java 19.2
- Schema-first approach
- JSON request/response

**GraphQL Endpoint:** `http://localhost:8080/claim-processing/graphql`

**Query Example:**
```graphql
query {
  validatePolicy(policyId: "POL-001", claimAmount: 5000.0) {
    isValid
    coverageAmount
    coveragePercentage
    message
  }
}
```

---

### Service Integration Summary

| Service | Protocol | Port | Data Format | Lines of Code |
|---------|----------|------|-------------|---------------|
| Claim Submission | REST | 8080 | JSON | ~150 |
| Identity Verification | SOAP | 8080 | XML | ~120 |
| Fraud Detection | gRPC | 50051 | Protobuf | ~200 |
| Policy Validation | GraphQL | 8080 | JSON | ~180 |

**Total Implementation:** 23 Java files, 6 configuration files, ~1,200 lines of production code

## 🧑‍💻 Application Clients

This project includes **4 Java application clients** for service testing and demonstration:

### 1. REST Client - `RestClient.java`

**Technology:** Jersey Client API 2.35

**Functionality:**
- Connects to REST API endpoint
- Sends POST request with JSON payload
- Parses JSON response

**Execution:**
```bash
mvn exec:java -Dexec.mainClass="com.insurance.client.RestClient"
```

---

### 2. SOAP Client - `SoapClient.java`

**Technology:** JAX-WS Client API

**Functionality:**
- Dynamically discovers SOAP service from WSDL
- Invokes `verifyIdentity()` method
- Parses XML SOAP response

**Execution:**
```bash
mvn exec:java -Dexec.mainClass="com.insurance.client.SoapClient"
```

---

### 3. gRPC Client - `GrpcClient.java`

**Technology:** gRPC Java Client

**Functionality:**
- Creates ManagedChannel to gRPC server
- Builds FraudRequest using Protocol Buffers
- Calls AnalyzeClaim() RPC method
- Deserializes binary FraudResponse

**Execution:**
```bash
mvn exec:java -Dexec.mainClass="com.insurance.client.GrpcClient"
```

---

### 4. GraphQL Client - `GraphQLClient.java`

**Technology:** HTTP Client (java.net.HttpURLConnection)

**Functionality:**
- Sends POST request to GraphQL endpoint
- Constructs GraphQL query string
- Parses JSON response

**Execution:**
```bash
mvn exec:java -Dexec.mainClass="com.insurance.client.GraphQLClient"
```

---

### 5. Orchestrator - `InsuranceClaimOrchestrator.java`

**Complete Workflow Integration**

**Functionality:**
- Coordinates all 4 services in sequence
- Implements XOR gateway logic
- Handles errors and aggregates responses

**Execution Flow:**
1. Submit claim via REST client
2. Verify identity via SOAP client → **XOR Gateway:** Fail → REJECT
3. Analyze fraud via gRPC client → **XOR Gateway:** HIGH risk → REJECT
4. Validate policy via GraphQL client → **XOR Gateway:** Invalid → REJECT
5. If all pass → APPROVE claim

## 📁 Project Structure

```
Insurance-Claim-Processing-SOA/
├── src/
│   ├── main/
│   │   ├── java/com/insurance/
│   │   │   ├── dto/              # Data Transfer Objects
│   │   │   ├── service/          # REST Services
│   │   │   ├── soap/             # SOAP Services
│   │   │   ├── grpc/             # gRPC Services
│   │   │   ├── graphql/          # GraphQL Services
│   │   │   ├── client/           # Test Clients
│   │   │   └── orchestrator/     # Service Orchestration
│   │   ├── proto/                # Protocol Buffer definitions
│   │   ├── resources/
│   │   │   ├── schema.graphql
│   │   │   └── META-INF/services.xml
│   │   └── webapp/
│   │       ├── WEB-INF/web.xml
│   │       └── index.html
├── docs/
│   ├── technical-docs/           # Architecture and deployment guides
│   ├── API_Documentation/        # Postman collection
│   └── README.md
├── pom.xml
├── build-and-deploy.bat
├── start-tomcat.bat
├── stop-tomcat.bat
├── start-grpc-java.bat
└── run-demo-java.bat
```

## 🧪 Test Cases

### Test Case 1: APPROVED - Low Risk Claim ✅

```json
{
  "claimId": "CLM-001",
  "userId": "USR-123",
  "claimType": "AUTO",
  "claimAmount": 5000.0,
  "description": "Minor car accident",
  "incidentDate": "2024-01-15"
}
```

**Workflow Execution:**
1. Identity Verification (SOAP): ✅ Verified
2. Fraud Detection (gRPC): ✅ LOW risk ($5,000 < $20,000 threshold)
3. Policy Validation (GraphQL): ✅ Valid policy (50% coverage)
4. **Final Decision:** ✅ **APPROVED**

---

### Test Case 2: REJECTED - High Fraud Risk ❌

```json
{
  "claimId": "CLM-002",
  "userId": "USR-456",
  "claimType": "ACCIDENT",
  "claimAmount": 500000.0,
  "description": "Very high value accident claim",
  "incidentDate": "2024-01-10"
}
```

**Workflow Execution:**
1. Identity Verification (SOAP): ✅ Verified
2. Fraud Detection (gRPC): ❌ **HIGH risk** ($500,000 > $100,000) → **XOR Gateway triggered**
3. Policy Validation (GraphQL): ⏭️ Skipped (early termination)
4. **Final Decision:** ❌ **REJECTED** - Fraud detected

---

### Test Case 3: REJECTED - Identity Verification Failed ❌

```json
{
  "claimId": "CLM-003",
  "userId": "USR-999",
  "claimType": "HEALTH",
  "claimAmount": 15000.0,
  "description": "Medical expenses claim",
  "incidentDate": "2024-01-20"
}
```

**Workflow Execution:**
1. Identity Verification (SOAP): ❌ **Failed** (User not found) → **XOR Gateway triggered**
2. Fraud Detection (gRPC): ⏭️ Skipped
3. Policy Validation (GraphQL): ⏭️ Skipped
4. **Final Decision:** ❌ **REJECTED** - Identity verification failed

## 💻 Technology Stack

**Core Technologies:**
- **Java 11** - Development platform
- **Apache Maven 3.6+** - Build automation
- **Apache Tomcat 9.0.113** - Web application server

**Service Implementation:**
- **JAX-RS (Jersey 2.35)** - REST API framework
- **JAX-WS** - SOAP web services
- **gRPC Java 1.58.0** - High-performance RPC
- **Protocol Buffers 3.24.0** - Binary serialization
- **graphql-java 19.2** - GraphQL implementation

**Data Handling:**
- **Gson 2.10.1** - JSON parsing
- **XML** - SOAP message format
- **Protobuf** - gRPC binary format

## 🔍 Workflow Orchestration & Gateway Logic

### XOR Gateway Implementation

**XOR Gateway 1: Identity Verification**
```java
// File: InsuranceClaimOrchestrator.java
VerificationResult verificationResult = soapClient.verifyIdentity(userId, customerName, documentId);

if (!verificationResult.isVerified()) {
    // Identity Failed → REJECT immediately
    return new ClaimResponse("REJECTED", "Identity verification failed", claimId);
}
// Identity OK → Continue to next step
```

**XOR Gateway 2: Fraud Detection**
```java
FraudResponse fraudResult = grpcClient.analyzeClaim(fraudRequest);

if (fraudResult.getIsFraudulent() || fraudResult.getRiskLevel() == RiskLevel.HIGH) {
    // HIGH risk → REJECT immediately
    return new ClaimResponse("REJECTED", "High fraud risk detected", claimId);
}
// LOW/MEDIUM risk → Continue to next step
```

**XOR Gateway 3: Policy Validation**
```java
PolicyValidation policyValidation = graphqlClient.validatePolicy(policyId, claimAmount);

if (!policyValidation.isValid()) {
    // Invalid policy → REJECT
    return new ClaimResponse("REJECTED", policyValidation.getMessage(), claimId);
}
// Valid policy → APPROVE claim
return new ClaimResponse("APPROVED", "All verifications passed", claimId);
```

## 🛠️ Troubleshooting

### Tomcat not starting
- Check if port 8080 is available
- Verify TOMCAT_HOME path in batch files
- Check Tomcat logs in `%TOMCAT_HOME%\logs\`

### gRPC server connection failed
- Ensure gRPC server is running (`start-grpc-java.bat`)
- Check if port 50051 is available
- Verify firewall settings

### Maven build fails
- Check internet connection (Maven downloads dependencies)
- Clear Maven cache: `mvn clean`
- Update Maven: `mvn -version`

## 🎓 Key Learnings & Takeaways

### 1. Multi-Protocol Service Implementation
- REST API design with JAX-RS
- SOAP web services with WSDL
- gRPC with Protocol Buffers
- GraphQL schema design

### 2. Service Orchestration Patterns
- Sequential service coordination
- XOR gateway decision-based routing
- Early termination optimization (fail-fast)
- Error propagation and aggregation

### 3. Service Technology Selection
- **REST:** Simple CRUD operations, web/mobile clients
- **SOAP:** Enterprise security, formal contracts
- **gRPC:** High-performance, low-latency, internal microservices
- **GraphQL:** Complex data relationships, flexible queries

### 4. SOA Design Principles
- **Service Reusability:** Each service callable independently
- **Service Autonomy:** Services manage own data and logic
- **Service Loose Coupling:** Protocol-agnostic orchestration
- **Service Contract:** WSDL, .proto, schema.graphql define interfaces

### Real-World Applications

**Insurance Industry:**
- Multi-channel claim submission (web, mobile, partner APIs)
- Real-time fraud detection
- Legacy system integration (SOAP for mainframe systems)
- Modern API gateway patterns (GraphQL for mobile apps)

**Enterprise Architecture:**
- Microservices communication patterns
- API gateway and service mesh concepts
- Event-driven architecture foundations
- Cloud-native application design

## 📊 Project Statistics

- **Development Time:** 5 weeks
- **Lines of Code:** ~1,200 (23 Java files)
- **Documentation:** 10+ markdown files
- **Automation Scripts:** 16 batch files
- **Test Coverage:** 4 application clients, Postman collection

**Technologies Demonstrated:**
- REST (Jersey 2.35), SOAP (JAX-WS), gRPC (1.58.0), GraphQL (19.2)
- Java 11, Maven, Apache Tomcat 9.0.113
- Protocol Buffers 3.24.0, JSON (Gson 2.10.1), XML

## 📄 License

This project is for educational and portfolio purposes, demonstrating professional-grade Service Oriented Architecture implementation.

**Acknowledgments:**
- Official documentation: Oracle Java EE, gRPC, GraphQL, Apache Jersey
- Service-oriented computing best practices

---

**🎉 Complete SOA Implementation demonstrating REST, SOAP, gRPC, and GraphQL integration 🎉**

**Quick Start:**
```bash
# 1. Start servers
.\start-tomcat.bat        # Terminal 1
.\start-grpc-java.bat     # Terminal 2

# 2. Run demo
.\run-demo-java.bat       # Terminal 3 → Option 5: Run All Tests
```

---

*Comprehensive demonstration of Service Oriented Architecture principles, multi-protocol service implementation, and professional software development practices.*
