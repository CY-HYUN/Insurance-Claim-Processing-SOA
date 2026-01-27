================================================================================
Insurance Claim Processing - Service Oriented Architecture (SOA) Project
================================================================================

Project Overview
----------------
This project demonstrates a complete Service Oriented Architecture (SOA)
implementation for insurance claim processing using four different service
communication protocols:

1. REST - Claim Submission Service
2. SOAP - Identity Verification Service
3. gRPC - Fraud Detection Service
4. GraphQL - Policy Validation Service

All services are orchestrated together to process insurance claims through
a complete verification pipeline.

================================================================================
Architecture
================================================================================

Service Communication Protocols:
--------------------------------
REST (Representational State Transfer)
  - Endpoint: /api/claims/submit
  - Protocol: HTTP/JSON
  - Purpose: Primary claim submission interface
  - Technology: JAX-RS (Jersey)

SOAP (Simple Object Access Protocol)
  - Service: IdentityVerificationService
  - Protocol: XML-based web service
  - Purpose: User identity verification
  - Technology: JAX-WS

gRPC (Google Remote Procedure Call)
  - Service: FraudDetectionService
  - Protocol: Protocol Buffers (binary)
  - Purpose: Real-time fraud analysis
  - Technology: gRPC + Protobuf
  - Port: 50051

GraphQL
  - Endpoint: /graphql
  - Protocol: HTTP/JSON
  - Purpose: Flexible policy data queries and validation
  - Technology: graphql-java

Processing Pipeline:
-------------------
Client Request (REST)
    ↓
Claim Submission Service
    ↓
Orchestrator
    ├── Identity Verification (SOAP)
    ├── Fraud Detection (gRPC)
    └── Policy Validation (GraphQL)
    ↓
Response (Approved/Rejected)

================================================================================
Project Structure
================================================================================

Insurance-Claim-Processing-SOA/
├── src/
│   ├── main/
│   │   ├── java/com/insurance/
│   │   │   ├── dto/              # Data Transfer Objects
│   │   │   │   ├── ClaimRequest.java
│   │   │   │   └── ClaimResponse.java
│   │   │   ├── service/          # REST Services
│   │   │   │   └── ClaimSubmissionService.java
│   │   │   ├── soap/             # SOAP Services
│   │   │   │   ├── IdentityVerificationService.java
│   │   │   │   └── VerificationResult.java
│   │   │   ├── grpc/             # gRPC Services
│   │   │   │   ├── FraudDetectionServer.java
│   │   │   │   ├── FraudDetectionServiceImpl.java
│   │   │   │   └── FraudDetectionClient.java
│   │   │   ├── graphql/          # GraphQL Services
│   │   │   │   ├── GraphQLServlet.java
│   │   │   │   ├── PolicyDataFetcher.java
│   │   │   │   ├── Policy.java
│   │   │   │   └── ValidationResult.java
│   │   │   ├── client/           # Test Clients
│   │   │   │   ├── RestClient.java
│   │   │   │   ├── SoapClient.java
│   │   │   │   ├── GrpcClient.java
│   │   │   │   └── GraphQLClient.java
│   │   │   └── orchestrator/     # Service Orchestration
│   │   │       └── InsuranceClaimOrchestrator.java
│   │   ├── proto/                # Protocol Buffer definitions
│   │   │   └── fraud_detection.proto
│   │   ├── resources/
│   │   │   ├── schema.graphql    # GraphQL schema
│   │   │   └── META-INF/
│   │   │       └── services.xml  # SOAP service configuration
│   │   └── webapp/
│   │       └── WEB-INF/
│   │           └── web.xml       # Web application configuration
├── pom.xml                       # Maven configuration
├── build-and-deploy.bat          # Build and deploy script
├── start-tomcat.bat              # Start Tomcat server
├── stop-tomcat.bat               # Stop Tomcat server
├── start-grpc-server.bat         # Start gRPC server
├── run-demo.bat                  # Run demo tests
└── docs/
    ├── readme.txt                # This file
    └── API_Documentation/
        └── Insurance_Claim_Processing.postman_collection.json

================================================================================
Prerequisites
================================================================================

Required Software:
-----------------
1. Java Development Kit (JDK) 11 or higher
2. Apache Maven 3.6+
3. Apache Tomcat 9.0+ (for REST and GraphQL)
4. Internet connection (for Maven dependencies)

Optional:
---------
- Postman (for API testing)
- SoapUI (for SOAP testing)
- gRPC GUI client (for gRPC testing)

================================================================================
Setup Instructions
================================================================================

1. Configure Tomcat Path
   ----------------------
   Edit the following batch files and set your Tomcat installation path:
   - build-and-deploy.bat
   - start-tomcat.bat
   - stop-tomcat.bat

   Example:
   set TOMCAT_HOME=C:\apache-tomcat-9.0.89

2. Build the Project
   -----------------
   Run: build-and-deploy.bat

   This will:
   - Clean and compile Java sources
   - Generate gRPC classes from .proto files
   - Package WAR file
   - Deploy to Tomcat (if configured)

3. Start Services
   --------------
   a) Start Tomcat (for REST and GraphQL):
      Run: start-tomcat.bat

   b) Start gRPC Server (for Fraud Detection):
      Run: start-grpc-server.bat

================================================================================
Testing the Services
================================================================================

Method 1: Using Demo Script
---------------------------
Run: run-demo.bat

This interactive script lets you test individual services or run all tests.

Method 2: Using Java Clients
----------------------------
Each service has a dedicated test client:

SOAP Client:
  mvn exec:java -Dexec.mainClass="com.insurance.client.SoapClient"

gRPC Client (requires gRPC server running):
  mvn exec:java -Dexec.mainClass="com.insurance.client.GrpcClient"

GraphQL Client (requires Tomcat running):
  mvn exec:java -Dexec.mainClass="com.insurance.client.GraphQLClient"

REST Client (requires Tomcat running):
  mvn exec:java -Dexec.mainClass="com.insurance.client.RestClient"

Method 3: Using Postman
-----------------------
Import: docs/API_Documentation/Insurance_Claim_Processing.postman_collection.json

This collection includes pre-configured requests for all REST endpoints.

Method 4: Manual Testing
------------------------
REST API:
  POST http://localhost:8080/claim-processing/api/claims/submit
  Content-Type: application/json

  {
    "claimId": "CLM-001",
    "userId": "USR-123",
    "claimType": "AUTO",
    "claimAmount": 5000.0,
    "description": "Car accident",
    "incidentDate": "2024-01-15"
  }

GraphQL:
  POST http://localhost:8080/claim-processing/graphql
  Content-Type: application/json

  {
    "query": "query { policy(policyId: \"POL-001\") { policyId policyType status } }"
  }

================================================================================
Service Details
================================================================================

1. REST - Claim Submission Service
   --------------------------------
   Base URL: http://localhost:8080/claim-processing/api/claims

   Endpoints:
   - POST /submit          Submit new claim
   - GET /{claimId}        Get claim status
   - GET /health           Health check

   Technology: JAX-RS (Jersey)
   Data Format: JSON

2. SOAP - Identity Verification Service
   -------------------------------------
   Service Class: com.insurance.soap.IdentityVerificationService

   Methods:
   - verifyIdentity(userId, name, documentId) -> VerificationResult
   - getServiceStatus() -> String

   Technology: JAX-WS
   Data Format: XML

3. gRPC - Fraud Detection Service
   -------------------------------
   Server: localhost:50051
   Proto File: src/main/proto/fraud_detection.proto

   Methods:
   - AnalyzeClaim(FraudRequest) -> FraudResponse
   - GetStatistics(StatisticsRequest) -> StatisticsResponse

   Technology: gRPC + Protocol Buffers
   Data Format: Binary (Protobuf)

4. GraphQL - Policy Validation Service
   ------------------------------------
   Endpoint: http://localhost:8080/claim-processing/graphql
   Schema: src/main/resources/schema.graphql

   Queries:
   - policy(policyId)
   - policiesByUser(userId)
   - allPolicies
   - validatePolicy(policyId, claimAmount)

   Technology: graphql-java
   Data Format: JSON

================================================================================
Orchestration Flow
================================================================================

When a claim is submitted via REST API:

1. REST Service receives ClaimRequest
2. Orchestrator coordinates the following checks:

   a) Identity Verification (SOAP)
      - Verifies user identity
      - Checks government-issued ID
      - Returns confidence score
      → If failed: Claim REJECTED

   b) Fraud Detection (gRPC)
      - Analyzes claim for fraud indicators
      - Calculates risk score
      - Checks claim history
      → If high risk: Claim REJECTED

   c) Policy Validation (GraphQL)
      - Validates policy exists and is active
      - Checks claim amount vs. coverage limit
      - Verifies policy status
      → If invalid: Claim REJECTED

3. If all checks pass: Claim APPROVED
4. Response sent back to client with detailed results

================================================================================
Sample Test Cases
================================================================================

Test Case 1: Successful Claim (Low Risk)
----------------------------------------
{
  "claimId": "CLM-001",
  "userId": "USR-123",
  "claimType": "AUTO",
  "claimAmount": 5000.0,
  "description": "Minor car accident",
  "incidentDate": "2024-01-15"
}

Expected: APPROVED
- Identity verified
- Low fraud risk
- Within policy coverage

Test Case 2: High-Value Claim (Medium Risk)
-------------------------------------------
{
  "claimId": "CLM-002",
  "userId": "USR-456",
  "claimType": "ACCIDENT",
  "claimAmount": 75000.0,
  "description": "Major accident claim",
  "incidentDate": "2024-01-10"
}

Expected: MANUAL_REVIEW or APPROVED (depending on fraud rules)
- Identity verified
- Medium fraud risk (high amount)
- May require manual review

Test Case 3: Suspicious Claim (High Risk)
-----------------------------------------
{
  "claimId": "CLM-003",
  "userId": "USR-789",
  "claimType": "ACCIDENT",
  "claimAmount": 150000.0,
  "description": "Very high value claim",
  "incidentDate": "2024-01-05"
}

Expected: REJECTED or MANUAL_REVIEW
- Very high claim amount triggers fraud detection
- Multiple red flags
- High risk score

================================================================================
Troubleshooting
================================================================================

Issue: Tomcat not starting
--------------------------
Solution:
- Check if port 8080 is available
- Verify TOMCAT_HOME path in batch files
- Check Tomcat logs in %TOMCAT_HOME%\logs\

Issue: gRPC server connection failed
------------------------------------
Solution:
- Ensure gRPC server is running (start-grpc-server.bat)
- Check if port 50051 is available
- Verify firewall settings

Issue: Maven build fails
------------------------
Solution:
- Check internet connection (Maven needs to download dependencies)
- Clear Maven cache: mvn clean
- Update Maven: mvn -version

Issue: WAR deployment fails
---------------------------
Solution:
- Verify Tomcat is not running during deployment
- Check Tomcat webapps folder permissions
- Review Tomcat logs for errors

Issue: GraphQL schema not found
-------------------------------
Solution:
- Ensure schema.graphql is in src/main/resources/
- Rebuild project: mvn clean package
- Check WAR file contains schema.graphql

================================================================================
Technology Stack
================================================================================

Backend:
--------
- Java 11
- Maven 3.x
- JAX-RS (Jersey 2.35) - REST
- JAX-WS - SOAP
- gRPC 1.58.0 - Remote Procedure Calls
- Protocol Buffers 3.24.0 - Data serialization
- GraphQL Java 19.2 - GraphQL implementation

Web Server:
-----------
- Apache Tomcat 9.0.89

Libraries:
----------
- Gson 2.10.1 - JSON processing
- SLF4J 2.0.9 - Logging
- javax.servlet-api 4.0.1
- javax.annotation-api 1.3.2

Build Tools:
------------
- Maven Compiler Plugin 3.11.0
- Maven WAR Plugin 3.3.2
- Protobuf Maven Plugin 0.6.1
- Maven Exec Plugin 3.1.0

================================================================================
Project Features
================================================================================

✓ Multi-protocol service integration (REST, SOAP, gRPC, GraphQL)
✓ Service orchestration pattern
✓ Comprehensive error handling
✓ Detailed logging and monitoring
✓ Test clients for all services
✓ Automated build and deployment scripts
✓ Sample data and test cases
✓ Postman collection for API testing
✓ Production-ready project structure

================================================================================
Learning Objectives
================================================================================

This project demonstrates:

1. REST API design with JAX-RS
2. SOAP web service implementation
3. gRPC with Protocol Buffers
4. GraphQL schema design and queries
5. Service orchestration patterns
6. Maven multi-module project structure
7. Servlet-based web application deployment
8. Error handling across different protocols
9. Testing strategies for SOA applications
10. Production deployment workflows

================================================================================
Contact & Support
================================================================================

Project: Insurance Claim Processing - SOA
Course: Service Oriented Computing
Institution: Télécom SudParis

For questions or issues:
1. Check this documentation
2. Review service logs
3. Test individual services before orchestration
4. Verify all prerequisites are met

================================================================================
Version History
================================================================================

Version 1.0 (January 2026)
- Initial release
- REST, SOAP, gRPC, GraphQL implementation
- Service orchestration
- Complete test suite
- Documentation and deployment scripts

================================================================================
License
================================================================================

This project is for educational purposes as part of the Service Oriented
Computing course at Télécom SudParis.

================================================================================
