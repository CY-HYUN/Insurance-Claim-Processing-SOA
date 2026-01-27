# Insurance Claim Processing SOA - Architecture Overview

## Table of Contents
- [System Architecture](#system-architecture)
- [Service Communication Protocols](#service-communication-protocols)
- [Processing Pipeline](#processing-pipeline)
- [Component Diagram](#component-diagram)
- [Technology Stack](#technology-stack)
- [Design Patterns](#design-patterns)

---

## System Architecture

The Insurance Claim Processing system implements a **Service-Oriented Architecture (SOA)** that integrates four different communication protocols to demonstrate modern microservices patterns.

### High-Level Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                         Client Layer                             │
│  (Web Browser, Mobile App, Postman, API Gateway)                │
└───────────────────────┬─────────────────────────────────────────┘
                        │
                        ↓
┌─────────────────────────────────────────────────────────────────┐
│                    API Gateway / Load Balancer                   │
│              (Apache Tomcat 9.0 - Port 8080)                     │
└───────────────────────┬─────────────────────────────────────────┘
                        │
                        ↓
┌─────────────────────────────────────────────────────────────────┐
│                  REST API - Entry Point                          │
│           ClaimSubmissionService (JAX-RS/Jersey)                 │
│                  /api/claims/submit                              │
└───────────────────────┬─────────────────────────────────────────┘
                        │
                        ↓
┌─────────────────────────────────────────────────────────────────┐
│              Service Orchestrator (Core Logic)                   │
│          InsuranceClaimOrchestrator.java                         │
│                                                                   │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │  Coordinates 3 parallel service calls:                    │  │
│  │  1. Identity Verification (SOAP)                          │  │
│  │  2. Fraud Detection (gRPC)                                │  │
│  │  3. Policy Validation (GraphQL)                           │  │
│  └──────────────────────────────────────────────────────────┘  │
└──────────┬─────────────────┬─────────────────┬─────────────────┘
           │                 │                 │
           ↓                 ↓                 ↓
┌──────────────────┐ ┌──────────────┐ ┌──────────────────┐
│  SOAP Service    │ │ gRPC Service │ │ GraphQL Service  │
│                  │ │              │ │                  │
│ Identity         │ │ Fraud        │ │ Policy           │
│ Verification     │ │ Detection    │ │ Validation       │
│                  │ │              │ │                  │
│ JAX-WS           │ │ Port: 50051  │ │ /graphql         │
│ XML-based        │ │ Protobuf     │ │ HTTP/JSON        │
└──────────────────┘ └──────────────┘ └──────────────────┘
```

---

## Service Communication Protocols

### 1. REST (Representational State Transfer)

**Purpose**: Primary interface for claim submission

**Technology Stack**:
- JAX-RS (Jersey 2.35)
- JSON data format
- HTTP/HTTPS protocol

**Endpoints**:
- `POST /api/claims/submit` - Submit new insurance claim
- `GET /api/claims/{claimId}` - Get claim status
- `GET /api/claims/health` - Health check endpoint

**Key Features**:
- Stateless communication
- Resource-based URL structure
- Standard HTTP methods (GET, POST, PUT, DELETE)
- JSON request/response format
- RESTful best practices

**Example Request**:
```json
POST /api/claims/submit
Content-Type: application/json

{
  "claimId": "CLM-001",
  "userId": "USR-123",
  "claimType": "AUTO",
  "claimAmount": 5000.0,
  "description": "Car accident",
  "incidentDate": "2024-01-15"
}
```

---

### 2. SOAP (Simple Object Access Protocol)

**Purpose**: Identity verification and user authentication

**Technology Stack**:
- JAX-WS (Java API for XML Web Services)
- XML data format
- WSDL (Web Services Description Language)

**Service**: `IdentityVerificationService`

**Methods**:
- `verifyIdentity(userId, name, documentId)` → Returns VerificationResult
- `getServiceStatus()` → Returns service health status

**Key Features**:
- Contract-first design with WSDL
- Strongly-typed XML messages
- Built-in error handling with SOAP Faults
- Enterprise-grade security support
- SOAP 1.1/1.2 compliant

**Verification Process**:
1. Receives user identity information
2. Validates government-issued ID
3. Checks identity against database
4. Calculates confidence score (0-100%)
5. Returns verification result with confidence level

---

### 3. gRPC (Google Remote Procedure Call)

**Purpose**: Real-time fraud detection and risk analysis

**Technology Stack**:
- gRPC 1.58.0
- Protocol Buffers 3.24.0
- Binary data serialization
- HTTP/2 transport

**Service**: `FraudDetectionService`

**Port**: 50051

**Methods**:
- `AnalyzeClaim(FraudRequest)` → FraudResponse
- `GetStatistics(StatisticsRequest)` → StatisticsResponse

**Key Features**:
- High-performance binary protocol
- Bi-directional streaming support
- Strong typing with Protocol Buffers
- Language-agnostic contracts
- Low latency communication

**Fraud Detection Logic**:
1. Analyzes claim amount vs. historical patterns
2. Checks user claim history
3. Detects anomalies and suspicious patterns
4. Calculates risk score (LOW, MEDIUM, HIGH)
5. Returns fraud indicators and recommendations

**Risk Calculation**:
- **LOW**: Amount < $10,000, no red flags
- **MEDIUM**: Amount $10,000-$75,000 or minor indicators
- **HIGH**: Amount > $75,000 or multiple red flags

---

### 4. GraphQL

**Purpose**: Flexible policy data queries and validation

**Technology Stack**:
- graphql-java 19.2
- JSON over HTTP
- Schema-first design

**Endpoint**: `POST /graphql`

**Schema Operations**:

**Queries**:
- `policy(policyId: String!)` - Get single policy details
- `policiesByUser(userId: String!)` - Get all policies for a user
- `allPolicies` - List all policies (admin)
- `validatePolicy(policyId: String!, claimAmount: Float!)` - Validate coverage

**Key Features**:
- Single endpoint for all queries
- Client-specified response structure
- Strongly-typed schema
- Efficient data fetching (no over-fetching)
- Real-time validation capabilities

**Schema Definition**:
```graphql
type Policy {
  policyId: String!
  userId: String!
  policyType: String!
  coverageAmount: Float!
  deductible: Float!
  status: String!
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

## Processing Pipeline

### Complete Claim Processing Flow

```
Step 1: Client Submission
────────────────────────────────────────────────────────
Client sends POST request to REST API
  ↓
ClaimSubmissionService receives ClaimRequest
  ↓
Validates input data (required fields, data types)
  ↓
Generates unique claim ID if not provided
  ↓
Passes to Orchestrator


Step 2: Service Orchestration
────────────────────────────────────────────────────────
InsuranceClaimOrchestrator coordinates three services:

┌─────────────────────────────────────────────────────┐
│  Parallel Service Calls (Concurrent Execution)      │
│                                                      │
│  Thread 1: SOAP - Identity Verification             │
│  Thread 2: gRPC - Fraud Detection                   │
│  Thread 3: GraphQL - Policy Validation              │
└─────────────────────────────────────────────────────┘


Step 3: Identity Verification (SOAP)
────────────────────────────────────────────────────────
SOAP Client → IdentityVerificationService
  ↓
Verify user identity
  ↓
Check government-issued ID
  ↓
Calculate confidence score
  ↓
Return VerificationResult {
  verified: true/false,
  confidence: 0-100,
  message: "verification details"
}

❌ If verification fails → REJECT claim immediately


Step 4: Fraud Detection (gRPC)
────────────────────────────────────────────────────────
gRPC Client → FraudDetectionService (port 50051)
  ↓
Analyze claim amount
  ↓
Check claim history
  ↓
Detect fraud patterns
  ↓
Calculate risk score
  ↓
Return FraudResponse {
  riskLevel: LOW/MEDIUM/HIGH,
  riskScore: 0-100,
  indicators: [...],
  recommendation: "APPROVE/REVIEW/REJECT"
}

❌ If HIGH risk → REJECT or MANUAL_REVIEW


Step 5: Policy Validation (GraphQL)
────────────────────────────────────────────────────────
GraphQL Client → PolicyDataFetcher
  ↓
Query: validatePolicy(policyId, claimAmount)
  ↓
Check policy exists and is ACTIVE
  ↓
Verify claim amount ≤ coverage amount
  ↓
Check policy expiration
  ↓
Return ValidationResult {
  isValid: true/false,
  message: "validation details",
  coverageAmount: $$$
}

❌ If validation fails → REJECT claim


Step 6: Decision Making
────────────────────────────────────────────────────────
Orchestrator aggregates all results:

IF (identity verified == false)
  → Status: REJECTED
  → Reason: "Identity verification failed"

ELSE IF (fraud risk == HIGH)
  → Status: REJECTED or MANUAL_REVIEW
  → Reason: "High fraud risk detected"

ELSE IF (policy validation == false)
  → Status: REJECTED
  → Reason: "Policy validation failed"

ELSE IF (fraud risk == MEDIUM)
  → Status: MANUAL_REVIEW
  → Reason: "Requires manual review"

ELSE
  → Status: APPROVED
  → Reason: "All validations passed"


Step 7: Response Generation
────────────────────────────────────────────────────────
Build ClaimResponse {
  claimId,
  status: APPROVED/REJECTED/MANUAL_REVIEW,
  message: "detailed explanation",
  timestamp,
  identityVerification: {...},
  fraudDetection: {...},
  policyValidation: {...}
}
  ↓
Return to client via REST API


Step 8: Logging & Monitoring
────────────────────────────────────────────────────────
Log all service calls
Log decision rationale
Store audit trail
Update monitoring metrics
```

---

## Component Diagram

### Service Components

```
┌──────────────────────────────────────────────────────────────┐
│                    Presentation Layer                         │
├──────────────────────────────────────────────────────────────┤
│  - REST API Endpoints (Jersey)                               │
│  - GraphQL Endpoint                                          │
│  - Static Web Pages (index.html)                             │
└────────────────────┬─────────────────────────────────────────┘
                     │
                     ↓
┌──────────────────────────────────────────────────────────────┐
│                    Business Logic Layer                       │
├──────────────────────────────────────────────────────────────┤
│  ┌─────────────────────────────────────────────────────┐    │
│  │  ClaimSubmissionService                             │    │
│  │  - validateClaimRequest()                           │    │
│  │  - processClaimSubmission()                         │    │
│  │  - getClaimStatus()                                 │    │
│  └─────────────────────────────────────────────────────┘    │
│                                                              │
│  ┌─────────────────────────────────────────────────────┐    │
│  │  InsuranceClaimOrchestrator                         │    │
│  │  - orchestrateClaimProcessing()                     │    │
│  │  - coordinateServices()                             │    │
│  │  - makeDecision()                                   │    │
│  └─────────────────────────────────────────────────────┘    │
└────────────────────┬─────────────────────────────────────────┘
                     │
                     ↓
┌──────────────────────────────────────────────────────────────┐
│                    Service Layer                              │
├──────────────────────────────────────────────────────────────┤
│                                                              │
│  ┌────────────────────┐  ┌────────────────────┐            │
│  │ SOAP Service       │  │ gRPC Service       │            │
│  ├────────────────────┤  ├────────────────────┤            │
│  │ Identity           │  │ Fraud Detection    │            │
│  │ Verification       │  │ Service            │            │
│  │                    │  │                    │            │
│  │ JAX-WS            │  │ Protobuf           │            │
│  └────────────────────┘  └────────────────────┘            │
│                                                              │
│  ┌────────────────────┐  ┌────────────────────┐            │
│  │ GraphQL Service    │  │ Client Services    │            │
│  ├────────────────────┤  ├────────────────────┤            │
│  │ Policy             │  │ - RestClient       │            │
│  │ Validation         │  │ - SoapClient       │            │
│  │                    │  │ - GrpcClient       │            │
│  │ graphql-java      │  │ - GraphQLClient    │            │
│  └────────────────────┘  └────────────────────┘            │
└──────────────────────────────────────────────────────────────┘
                     │
                     ↓
┌──────────────────────────────────────────────────────────────┐
│                    Data Layer                                 │
├──────────────────────────────────────────────────────────────┤
│  ┌─────────────────────────────────────────────────────┐    │
│  │  Data Transfer Objects (DTOs)                       │    │
│  │  - ClaimRequest.java                                │    │
│  │  - ClaimResponse.java                               │    │
│  │  - VerificationResult.java                          │    │
│  │  - Policy.java                                      │    │
│  │  - ValidationResult.java                            │    │
│  └─────────────────────────────────────────────────────┘    │
│                                                              │
│  ┌─────────────────────────────────────────────────────┐    │
│  │  Protocol Definitions                               │    │
│  │  - fraud_detection.proto (gRPC)                     │    │
│  │  - schema.graphql (GraphQL)                         │    │
│  │  - services.xml (SOAP)                              │    │
│  └─────────────────────────────────────────────────────┘    │
└──────────────────────────────────────────────────────────────┘
```

---

## Technology Stack

### Backend Technologies

| Component | Technology | Version | Purpose |
|-----------|-----------|---------|---------|
| **Language** | Java | 11+ | Core programming language |
| **Build Tool** | Maven | 3.6+ | Dependency management & build |
| **REST Framework** | JAX-RS (Jersey) | 2.35 | REST API implementation |
| **SOAP Framework** | JAX-WS | 2.3.1 | SOAP web services |
| **RPC Framework** | gRPC | 1.58.0 | High-performance RPC |
| **Serialization** | Protocol Buffers | 3.24.0 | Binary data format |
| **GraphQL** | graphql-java | 19.2 | GraphQL implementation |
| **JSON Processing** | Gson | 2.10.1 | JSON serialization |
| **Logging** | SLF4J + Logback | 2.0.9 | Application logging |
| **Web Server** | Apache Tomcat | 9.0.89 | Servlet container |

### Dependencies (pom.xml)

```xml
<!-- REST (JAX-RS) -->
<dependency>
    <groupId>org.glassfish.jersey.containers</groupId>
    <artifactId>jersey-container-servlet</artifactId>
    <version>2.35</version>
</dependency>

<!-- SOAP (JAX-WS) -->
<dependency>
    <groupId>com.sun.xml.ws</groupId>
    <artifactId>jaxws-rt</artifactId>
    <version>2.3.5</version>
</dependency>

<!-- gRPC -->
<dependency>
    <groupId>io.grpc</groupId>
    <artifactId>grpc-netty-shaded</artifactId>
    <version>1.58.0</version>
</dependency>

<!-- GraphQL -->
<dependency>
    <groupId>com.graphql-java</groupId>
    <artifactId>graphql-java</artifactId>
    <version>19.2</version>
</dependency>
```

---

## Design Patterns

### 1. Service Orchestration Pattern

**Implementation**: `InsuranceClaimOrchestrator.java`

**Purpose**: Coordinate multiple services to complete a business process

**Key Features**:
- Central coordination logic
- Parallel service invocation
- Error handling and fallback
- Transaction management
- Result aggregation

**Benefits**:
- Single point of control
- Easier to maintain business logic
- Consistent error handling
- Better monitoring and logging

---

### 2. Adapter Pattern

**Implementation**: Client classes (RestClient, SoapClient, GrpcClient, GraphQLClient)

**Purpose**: Provide uniform interface to different service protocols

**Key Features**:
- Protocol-specific adapters
- Common interface abstraction
- Error translation
- Response normalization

**Benefits**:
- Decoupled service consumers
- Easy to add new protocols
- Consistent error handling
- Testable service interactions

---

### 3. Data Transfer Object (DTO) Pattern

**Implementation**: DTO package (ClaimRequest, ClaimResponse, etc.)

**Purpose**: Transfer data between layers without exposing internal structures

**Key Features**:
- Simple data containers
- No business logic
- Serializable
- Validation annotations

**Benefits**:
- Clear data contracts
- Reduced coupling
- Easy serialization
- Validation support

---

### 4. Facade Pattern

**Implementation**: Service layer facades

**Purpose**: Simplify complex subsystem interactions

**Key Features**:
- Simplified interface
- Hides implementation complexity
- Single entry point per service
- Consistent API

**Benefits**:
- Easier to use services
- Reduced dependencies
- Better encapsulation
- Improved maintainability

---

## Security Considerations

### Current Implementation

1. **Input Validation**: All service inputs validated
2. **Error Handling**: No sensitive data in error messages
3. **Logging**: Audit trail for all operations
4. **Service Isolation**: Each service runs independently

### Production Recommendations

1. **Authentication**: Implement JWT/OAuth2
2. **Authorization**: Role-based access control (RBAC)
3. **Encryption**: TLS/SSL for all communications
4. **API Gateway**: Rate limiting and throttling
5. **Data Protection**: Encrypt sensitive data at rest
6. **Audit Logging**: Comprehensive audit trail

---

## Scalability Considerations

### Horizontal Scaling

- Each service can scale independently
- Load balancer for REST/GraphQL endpoints
- Multiple gRPC server instances
- Stateless service design

### Performance Optimization

- Connection pooling for service clients
- Caching for frequently accessed data
- Asynchronous processing for long-running operations
- Database read replicas

### Monitoring

- Service health checks
- Performance metrics (latency, throughput)
- Error rate tracking
- Resource utilization monitoring

---

## Deployment Architecture

### Development Environment

```
Single Server Setup:
- Tomcat (REST + GraphQL): Port 8080
- gRPC Server: Port 50051
- All services on localhost
```

### Production Environment

```
Multi-Server Setup:
- Load Balancer: nginx/HAProxy
- REST/GraphQL Cluster: 3+ Tomcat instances
- gRPC Cluster: 3+ gRPC servers
- SOAP Service: Dedicated app servers
- Database: Primary + Read replicas
- Caching: Redis cluster
- Message Queue: RabbitMQ/Kafka (for async processing)
```

---

## Conclusion

This architecture demonstrates a modern SOA implementation integrating four major service communication protocols. The design emphasizes:

- **Modularity**: Each service is independent
- **Flexibility**: Multiple protocol support
- **Scalability**: Designed for horizontal scaling
- **Maintainability**: Clear separation of concerns
- **Testability**: Comprehensive test clients

The orchestration pattern provides a robust framework for complex business processes while maintaining service independence and reusability.

---

**Document Version**: 1.0
**Last Updated**: January 2026
**Author**: Insurance Claim Processing Team
**Course**: Service Oriented Computing
**Institution**: Télécom SudParis
