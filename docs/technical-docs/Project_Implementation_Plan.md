# Service Oriented Computing - Project Implementation Plan

> **Date**: January 22, 2025
> **Status**: 95% Complete (Services Implemented, BPMN/Workflow Remaining)
> **Strategy**: Java Orchestrator + BPMN Diagrams (No Bonita/Activiti)
> **Team**: Changyong Hyun (Services) + Thijmen Welberg (BPMN/Workflow)

---

## 📋 Project Overview

### Project Title
**End-to-End Insurance Claim Processing System**

A comprehensive digital service for insurance companies enabling customers to submit, process, and track insurance claims through multiple service technologies.

### 🎯 Core Strategy Decision

**✅ We are NOT using Bonita/Activiti workflow engines!**

**Reasons:**
1. ✅ **Time Savings**: Bonita learning (3-4h) + Connector setup (3-4h) = **6-8 hours saved**
2. ✅ **Guaranteed Completion**: Implementation using only Java (familiar technology)
3. ✅ **Sufficient Score**: Expected 170-210 points / 250 total (68-84%)
4. ✅ **Lower Risk**: Guaranteed functionality, easy debugging

**Instead, we use:**
- **Workflow Design**: BPMN diagrams (draw.io, Lucidchart, PowerPoint)
- **Workflow Execution**: Java Orchestrator class (sequential service calls)
- **Gateway Logic**: if/else, CompletableFuture (parallel processing)

### Important Notes
- ⚠️ **All partner interactions and payments are simulated**
- ✅ **Professor's instruction**: "Start by implementing the services first. Then you can develop the workflow."
- ⚠️ **Independent work required**: Similar source code = 0 points
- ⏱️ **Presentation time**: Exactly 20 minutes
- 📦 **Submission**: Upload .zip to Moodle immediately after presentation

---

## 🎯 Requirements & Expected Score (Total: 250 points)

### Core Requirements (200 points base)

| Requirement | Points | Implementation | Expected | Time |
|------------|--------|----------------|----------|------|
| **1. Workflow Design** (Pools + 2 partners) | 15 | BPMN diagrams | **10-15** ⚠️ | 2-3h |
| **2. Gateway Usage** (OR, AND, XOR) | 15 | Diagram + Java | **10-15** ⚠️ | 1-2h |
| **3. REST Service Implementation** | 30 | Jersey + Client | **30** ✅ | 3-4h |
| **4. SOAP Service Implementation** | 30 | JAX-WS + Client | **30** ✅ | 3-4h |
| **5. gRPC API Implementation** | 20 | gRPC + Client | **20** ✅ | 6-7h |
| **6. GraphQL API Implementation** | 20 | graphql-java + Client | **20** ✅ | 6-7h |
| **7. API Testing & Documentation** | 30 | Postman | **30** ✅ | 3-4h |
| **8. Correct Execution & Demo** | 40 | Orchestrator | **40** ✅ | 4-6h |
| **Base Total** | **200** | | **160-190** | **29-37h** |

### Optional Requirements

| Requirement | Points | Status |
|------------|--------|--------|
| **Microservice Deployment** | 50 | ❌ **Excluded** (too complex) |

### Bonus Points

| Bonus Item | Expected | Difficulty | Time | Choice |
|-----------|----------|------------|------|--------|
| **JSON Format REST** | +5-10 | ⭐ | **30min** | ✅ **Done** |
| **Error Handling** (try-catch, status codes) | +5-10 | ⭐⭐ | **1-2h** | ✅ **Done** |

### 🎯 Target Score: 170-210 points
- **Base Score**: 160-190 (possible deduction on workflow)
- **Bonus**: 10-20 (JSON + error handling)
- **Total Time**: 30-40 hours
- **Success Probability**: 85-90%

---

## 🎯 Current Implementation Status (95% Complete)

### ✅ Completed (Changyong's Part)

#### 1. REST API - Claim Submission
- **Technology**: Jersey 2.35
- **Endpoint**: `POST /api/claims/submit`
- **Why REST**: Simple CRUD operations, JSON format, web-friendly, widely supported
- **Status**: ✅ Fully implemented and tested

#### 2. SOAP Service - Identity Verification
- **Technology**: JAX-WS (javax.jws-api 1.1, jaxws-rt 2.3.5)
- **Endpoint**: `/services/IdentityVerification`
- **WSDL**: Available at `?wsdl` parameter
- **Why SOAP**: Enterprise-grade security (WS-Security), formal contract (WSDL), transaction support
- **Status**: ✅ Fully implemented and tested

#### 3. gRPC Service - Fraud Detection
- **Technology**: Protocol Buffers 3.24.0, gRPC 1.58.0
- **Port**: 50051
- **Proto File**: `fraud_detection.proto`
- **Why gRPC**: High performance, low latency, efficient binary protocol, real-time analysis
- **Status**: ✅ Fully implemented and tested

#### 4. GraphQL API - Policy Validation
- **Technology**: graphql-java 19.2
- **Endpoint**: `POST /graphql`
- **Schema**: `schema.graphql`
- **Why GraphQL**: Flexible queries, client-defined data needs, single endpoint, prevents over-fetching
- **Status**: ✅ Fully implemented and tested

#### 5. Java Orchestrator
- **File**: `InsuranceClaimOrchestrator.java`
- **Features**:
  - Integration of all 4 service types
  - Gateway logic implementation (XOR, AND, OR)
  - Complete workflow execution
  - Error handling and logging
- **Status**: ✅ Fully implemented

#### 6. Documentation
- **Architecture Overview**: 26 KB, 676 lines
- **Service Endpoints**: 20 KB, 958 lines
- **Testing Guide**: 25 KB, 1,178 lines
- **Deployment Guide**: 27 KB, 1,217 lines
- **Postman Collection**: Complete API test examples
- **Status**: ✅ Complete

### ⏳ Remaining (Thijmen's Part - 5%)

#### 7. BPMN/Workflow Component (30 points)

**Option A: Workflow Engine Integration** (Best - Full 30 points)
- Integrate services with Bonita/Flowable/Activiti
- Deploy services as BPMN service tasks
- Visual workflow execution

**Option B: BPMN Diagrams Only** (Good - 20-25 points)
- Create detailed BPMN diagrams using draw.io
- Document workflow with Pools, Lanes, Gateways
- Include all 3 gateway types (XOR, AND, OR)
- Map services to BPMN activities

**Option C: Minimal** (Acceptable - 10-15 points)
- Simple workflow diagram in PowerPoint
- Basic gateway illustrations

**Recommended**: Option B (balance of time/points)

#### 8. Presentation Slides (PPT)
- Project overview
- Architecture diagrams
- Service demonstrations
- Technology justification
- Demo script

---

## 🔄 Workflow Description (Insurance Claim Processing)

### Service Flow (11 Activities)

| Order | Service | Description | Technology | Implemented |
|-------|---------|-------------|------------|-------------|
| 1 | **Claim Submission** | Customer submits claim | **REST** | ✅ |
| 2 | **Identity Verification** | Verify customer identity | **SOAP** | ✅ |
| 3 | **Policy Validation** | Validate insurance policy | **GraphQL** | ✅ |
| 4 | **Fraud Detection** | Assess fraud risk | **gRPC** | ✅ |
| 5 | **Eligibility Evaluation** | Check claim eligibility | Java method | ✅ |
| 6 | **Document Review** | Review submitted documents | Java method | ✅ |
| 7 | **Expert Assessment** | Manual review for high risk | Java method | ✅ |
| 8 | **Compensation Calculation** | Calculate payout amount | Java method | ✅ |
| 9 | **Payment Authorization** | Authorize payment | Java method | ✅ |
| 10 | **Customer Notification** | Notify customer of result | Logging | ✅ |
| 11 | **Claim Tracking** | Track claim status | Logging | ✅ |

**Note**: Services 5-11 are simple Java methods (no separate servers needed)

### Gateway Types

#### XOR Gateway (Exclusive)
- **Location**: After Identity Verification
- **Logic**: If identity verified → continue, else → reject claim
- **Implementation**: `if-else` statement in Orchestrator

#### AND Gateway (Parallel)
- **Location**: After Policy Validation
- **Logic**: Execute Fraud Detection and Document Review simultaneously
- **Implementation**: `CompletableFuture.allOf()` in Java

#### OR Gateway (Inclusive)
- **Location**: After Fraud Detection
- **Logic**: Route based on risk level (LOW/MEDIUM/HIGH)
- **Implementation**: `switch` statement in Orchestrator

---

## 📊 Technology Justification

### Why These Technologies?

| Service | Technology | Justification |
|---------|-----------|---------------|
| **Claim Submission** | REST | • Stateless operations<br>• JSON format (web-friendly)<br>• Simple CRUD<br>• Wide tool support (Postman, browsers) |
| **Identity Verification** | SOAP | • Enterprise security (WS-Security)<br>• Formal contract (WSDL)<br>• Transaction support<br>• Critical authentication requires strict protocol |
| **Fraud Detection** | gRPC | • High performance needed for real-time analysis<br>• Low latency (binary protocol)<br>• Efficient for complex calculations<br>• Streaming support for continuous monitoring |
| **Policy Validation** | GraphQL | • Flexible queries (client defines needed fields)<br>• Single endpoint for complex policy data<br>• Prevents over-fetching/under-fetching<br>• Insurance policies have nested complex data |

---

## 🚀 Deployment Architecture

### Servers

1. **Tomcat 9.0.113** (Port 8080)
   - REST API (Jersey servlet)
   - SOAP Service (JAX-WS servlet)
   - GraphQL API (GraphQL servlet)

2. **gRPC Server** (Port 50051)
   - Standalone Java application
   - FraudDetectionServer

### Directory Structure

```
Insurance-Claim-Processing-SOA/
├── src/
│   ├── main/
│   │   ├── java/com/insurance/
│   │   │   ├── dto/                    # Data Transfer Objects
│   │   │   │   ├── ClaimRequest.java
│   │   │   │   └── ClaimResponse.java
│   │   │   ├── service/                # REST Service
│   │   │   │   └── ClaimSubmissionService.java
│   │   │   ├── soap/                   # SOAP Service
│   │   │   │   ├── IdentityVerificationService.java
│   │   │   │   └── VerificationResult.java
│   │   │   ├── grpc/                   # gRPC Service
│   │   │   │   ├── FraudDetectionServer.java
│   │   │   │   ├── FraudDetectionServiceImpl.java
│   │   │   │   └── FraudDetectionClient.java
│   │   │   ├── graphql/                # GraphQL Service
│   │   │   │   ├── GraphQLServlet.java
│   │   │   │   ├── PolicyDataFetcher.java
│   │   │   │   ├── Policy.java
│   │   │   │   └── ValidationResult.java
│   │   │   ├── orchestrator/           # Integration
│   │   │   │   └── InsuranceClaimOrchestrator.java
│   │   │   └── client/                 # Test Clients
│   │   │       ├── RestClient.java
│   │   │       ├── SoapClient.java
│   │   │       ├── GrpcClient.java
│   │   │       └── GraphQLClient.java
│   │   ├── proto/                      # Protocol Buffers
│   │   │   └── fraud_detection.proto
│   │   ├── resources/
│   │   │   ├── schema.graphql          # GraphQL Schema
│   │   │   └── META-INF/
│   │   │       └── services.xml        # SOAP Configuration
│   │   └── webapp/WEB-INF/
│   │       ├── web.xml                 # Servlet Configuration
│   │       └── sun-jaxws.xml           # JAX-WS Configuration
├── docs/                               # Documentation
│   ├── Architecture_Overview.md
│   ├── Service_Endpoints.md
│   ├── Testing_Guide.md
│   ├── Deployment_Guide.md
│   └── API_Documentation/
│       └── Insurance_Claim_Processing.postman_collection.json
├── pom.xml                             # Maven Configuration
└── *.bat                               # Startup Scripts
```

---

## 🎤 Demonstration Plan (20 minutes)

### Professor's Critical Requirements

From `guideline2.txt`:

> **"The most important thing, demo of your presentation"**
> **"don't use Swagger or browser to test them... build the client, it should be an application client"**
> **"explain to me why you use this service technology for this kind of service"**

### Presentation Structure

#### 1. Introduction (2 minutes)
- Project overview
- Team members and roles
- Technology stack

#### 2. Architecture (3 minutes)
- BPMN workflow diagram
- Service communication flow
- Gateway explanations

#### 3. Services Overview (5 minutes)
- **REST** (Claim Submission): Why REST for this service
- **SOAP** (Identity): Why SOAP for authentication
- **gRPC** (Fraud): Why gRPC for real-time analysis
- **GraphQL** (Policy): Why GraphQL for flexible queries

#### 4. Live Demonstration (8 minutes) 🔥
**CRITICAL: Must use Java Client, NOT Postman/Swagger**

```bash
# Execute Java Orchestrator
mvn exec:java -Dexec.mainClass="com.insurance.orchestrator.InsuranceClaimOrchestrator"
```

**Show on screen:**
```
============================================================
[1] Submitting claim via REST API...
✅ Claim submitted: CLM-001

[2] Verifying identity via SOAP...
✅ Identity verified: CUST-12345

[3] Analyzing fraud risk via gRPC...
✅ Risk level: LOW (score: 0.25)

[4] Validating policy via GraphQL...
✅ Policy valid: POL-12345

[RESULT] Claim APPROVED ✅
============================================================
```

**Scenarios to demonstrate:**
1. Normal approval (LOW risk, valid policy, identity OK)
2. Identity failure (XOR Gateway - immediate rejection)
3. High risk (Manual review required)

#### 5. Q&A (2 minutes)
- Technical questions
- Technology justification
- Implementation challenges

---

## 📦 Deployment Instructions

### Quick Start

#### Option 1: Automated (Recommended)
```bash
# Start all servers
start-all-servers.bat

# Wait 10 seconds for servers to initialize
# Then run demo
run-demo.bat
```

#### Option 2: Manual
```bash
# Terminal 1: Start Tomcat
C:\apache-tomcat-9.0.113\bin\startup.bat

# Terminal 2: Start gRPC Server (VSCode terminal recommended)
cd "D:\Study\Github\Insurance-Claim-Processing-SOA-NEW"
start-grpc-server.bat

# Terminal 3: Run Java Orchestrator Demo
mvn exec:java -Dexec.mainClass="com.insurance.orchestrator.InsuranceClaimOrchestrator"
```

### Verification

**Check servers are running:**
```bash
# Tomcat (REST, SOAP, GraphQL)
curl http://localhost:8080/claim-processing/api/claims/health

# gRPC
netstat -ano | findstr :50051
```

**Expected Response:**
```json
{
  "status": "UP",
  "service": "ClaimSubmissionService"
}
```

---

## 🤝 Team Collaboration

### Role Division

#### Changyong Hyun (95% complete)
- ✅ REST API implementation
- ✅ SOAP Service implementation
- ✅ gRPC Service implementation
- ✅ GraphQL API implementation
- ✅ Java Orchestrator
- ✅ Complete documentation
- ✅ Server setup and deployment
- ⏳ README integration
- ⏳ Demo execution

#### Thijmen Welberg (5% remaining)
- ⏳ BPMN/Workflow component (30 points)
  - Option A: Bonita/Flowable integration (best)
  - Option B: Detailed BPMN diagrams (recommended)
  - Option C: Simple diagrams (minimal)
- ⏳ Presentation slides (PPT)
- ⏳ Demo script preparation
- ⏳ README section for workflow

### Workflow Process

1. **Changyong**: Write README for services implementation → `MY_README_Changyong.md`
2. **Thijmen**: Create BPMN/Workflow and write README section → `THIJMEN_README_Workflow.md`
3. **Changyong**: Merge both READMEs → `FINAL_README.md`
4. **Thijmen**: Create presentation slides using final README → `Presentation.pptx`
5. **Both**: Final demo rehearsal

---

## 📚 Additional Resources

### Documentation Files
- [Architecture Overview](Architecture_Overview.md) - System design
- [Service Endpoints](Service_Endpoints.md) - API specifications
- [Testing Guide](Testing_Guide.md) - How to test all services
- [Deployment Guide](Deployment_Guide.md) - Deployment instructions
- [Postman Collection](API_Documentation/Insurance_Claim_Processing.postman_collection.json) - API tests

### Professor's Guidelines
- [guideline.txt](guideline.txt) - Original project requirements
- [guideline2.txt](guideline2.txt) - Professor's verbal instructions transcript

### Key Takeaways from Professor

1. **Services are the priority** (220 points / 250 = 88%)
2. **Workflow is secondary** (30 points / 250 = 12%)
3. **Mock implementations are acceptable** (don't need real databases)
4. **Java client is mandatory** for demo (no Swagger/browser)
5. **Technology justification is critical** (explain WHY for each choice)

---

## 🎯 Success Metrics

### Completion Status
- [x] REST Service (30 points)
- [x] SOAP Service (30 points)
- [x] gRPC Service (20 points)
- [x] GraphQL Service (20 points)
- [x] Java Orchestrator (40 points)
- [x] Documentation (30 points)
- [ ] BPMN/Workflow (30 points) ← **Thijmen's task**
- [ ] Presentation (Required for demo)

**Current Score**: 170 / 250 (68%)
**With BPMN**: 200-210 / 250 (80-84%)

### Risk Assessment
- **Technical Risk**: ✅ **LOW** (all services working)
- **Time Risk**: ✅ **LOW** (95% complete)
- **Demo Risk**: ⚠️ **MEDIUM** (requires practice)
- **Team Risk**: ✅ **LOW** (clear role division)

---

## 📞 Contact & Repository

**GitHub Repository**: https://github.com/CY-HYUN/Insurance-Claim-Processing-SOA

**Team Members**:
- Changyong Hyun (Services Implementation)
- Thijmen Welberg (BPMN/Workflow & Presentation)

**Last Updated**: January 22, 2025

---

**Note**: This document is for team collaboration. For technical implementation details, see individual documentation files in the `/docs` folder.

🤖 Generated with Claude Code
Co-Authored-By: Claude Sonnet 4.5 <noreply@anthropic.com>
