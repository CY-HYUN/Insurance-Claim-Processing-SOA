# Insurance Claim Processing — One Workflow, Four Protocols (REST + SOAP + gRPC + GraphQL)

A Java 11 Service-Oriented Architecture demo: a single insurance-claim workflow orchestrated across **four service protocols**, with three XOR fail-fast gateways deciding APPROVED vs REJECTED.

![Java](https://img.shields.io/badge/Java-11-orange)
![Maven](https://img.shields.io/badge/Build-Maven-blue)
![Protocols](https://img.shields.io/badge/Protocols-REST%20%7C%20SOAP%20%7C%20gRPC%20%7C%20GraphQL-green)

## Verified Results

- **4 protocols in one build**: REST (Jersey 2.35), SOAP (JAX-WS), gRPC (1.58.0 / Protobuf 3.24.0), GraphQL (graphql-java 19.2) — one Maven WAR on Tomcat plus a standalone gRPC server
- **3 XOR gateways** (identity, fraud, policy) with early termination — one `POST` triggers the full pipeline
- **17 Java source files, ~1,900 lines** (hand-written; gRPC stubs generated at build time), 50-line protobuf schema, 36-line GraphQL schema
- **4 demo clients** (REST, SOAP, gRPC, GraphQL) plus a 263-line orchestrator

Real output — one approved and one rejected claim through the same endpoint (captured from a live run):

```bash
$ curl -X POST http://localhost:8080/claim-processing/api/claims/submit \
  -H "Content-Type: application/json" \
  -d '{"claimId":"CLM-001","userId":"USR-123","claimType":"AUTO","claimAmount":5000.0,
       "description":"Minor car accident","incidentDate":"2024-01-15"}'

{"claimId":"CLM-001","fraudCheckPassed":true,"identityVerified":true,
 "message":"Claim approved successfully","policyStatus":"VALID","status":"APPROVED",
 "timestamp":"2026-07-02 12:32:02"}

$ curl -X POST http://localhost:8080/claim-processing/api/claims/submit \
  -H "Content-Type: application/json" \
  -d '{"claimId":"CLM-002","userId":"USR-456","claimType":"ACCIDENT","claimAmount":500000.0,
       "description":"Very high value accident claim","incidentDate":"2024-01-10"}'

{"claimId":"CLM-002","fraudCheckPassed":false,"identityVerified":true,
 "message":"Fraud detected: Critical fraud risk detected. Claim should be rejected.",
 "status":"REJECTED","timestamp":"2026-07-02 12:32:09"}
```

Server-side orchestrator trace for the rejected claim, excerpt — the XOR gateway stops the pipeline at step 2 and step 3 never runs:

```
ORCHESTRATOR: Starting Claim Processing Pipeline
[Step 1/3] Identity Verification (SOAP Service)
Verification Result: PASSED
Confidence Score: 0.95
✓ Identity verified successfully
[Step 2/3] Fraud Detection (gRPC Service)
❌ Claim rejected: Fraud detected (Risk: CRITICAL)
```

## Quick Start

Prerequisites: JDK 11+ (pom targets Java 11; full build and demo verified on JDK 21), Maven 3.6+, Apache Tomcat 9.

```bash
git clone https://github.com/CY-HYUN/Insurance-Claim-Processing-SOA.git
cd Insurance-Claim-Processing-SOA

# 1. Build (package is required — the demo scripts load jars from the exploded WAR)
mvn clean package
```

Then start the two servers and run the demo (Windows):

```bash
# 2. Edit TOMCAT_HOME in start-tomcat.bat / stop-tomcat.bat / build-and-deploy.bat
#    to your Tomcat install path (default: C:\apache-tomcat-9.0.113)

# 3. Deploy the WAR and start Tomcat (REST + SOAP + GraphQL on :8080)
build-and-deploy.bat
start-tomcat.bat

# 4. Start the gRPC fraud-detection server (:50051) in a second terminal
start-grpc-java.bat

# 5. Run the demo clients in a third terminal (menu: 1=SOAP 2=gRPC 3=GraphQL 4=REST 5=all)
run-demo-java.bat
```

Or skip the clients and drive the whole workflow with the `curl` calls shown above.

Setup notes (honest):

- `run-demo-java.bat` and `start-grpc-java.bat` prepend `C:\Program Files\Microsoft\jdk-11.0.16.101-hotspot` to `PATH` if it exists; otherwise they fall back to whatever `java` is on your `PATH` (JDK 11+ required).
- `mvn clean package` alone is enough to build without Tomcat; the WAR lands in `target/claim-processing.war` and can be copied to any Tomcat `webapps/` folder by hand.
- `settings.xml` in the repo root is optional — use `mvn -s settings.xml clean package` if your Windows username is non-ASCII (it moves the local Maven repo to `D:/maven-repository`).
- Ports used: 8080 (Tomcat), 50051 (gRPC).

## Architecture

```
Client (curl / RestClient)
      |  POST /api/claims/submit  (REST, JSON)
      v
ClaimSubmissionService  ->  InsuranceClaimOrchestrator
      |
      |-- [1] Identity Verification -- SOAP service class      -- XOR: fail -> REJECT
      |-- [2] Fraud Detection ------- gRPC call to :50051      -- XOR: fraud -> REJECT
      |-- [3] Policy Validation ----- GraphQL engine           -- XOR: invalid -> REJECT
      v
  All pass -> APPROVED
```

- **REST** (`ClaimSubmissionService`) receives the claim and returns the aggregated decision.
- **SOAP** (`IdentityVerificationService`, JAX-WS) verifies identity; also exposed at `/services/IdentityVerification?wsdl` for wire-level SOAP clients.
- **gRPC** (`FraudDetectionServer` / `FraudDetectionServiceImpl`) scores fraud risk over Protocol Buffers on port 50051 — this hop crosses the network in every run.
- **GraphQL** (`PolicyDataFetcher` + `GraphQLServlet`) validates the policy; also exposed at `/graphql` for external queries.

Fraud scoring is rule-based (see `FraudDetectionServiceImpl`): +0.3 if amount > $50,000, +0.4 if > $100,000, +0.2 for accident claims > $75,000, +0.25 for multi-claim history. Score >= 0.6 (HIGH/CRITICAL) trips the XOR gateway and rejects the claim.

### Why four protocols

| Service | Protocol | Rationale |
|---|---|---|
| Claim Submission | REST (JSON) | Stateless CRUD entry point, easiest for web/mobile clients |
| Identity Verification | SOAP (XML/WSDL) | Formal contract + type safety, the classic enterprise/insurance integration style |
| Fraud Detection | gRPC (Protobuf) | Binary, low-latency internal call for the compute-style service |
| Policy Validation | GraphQL | Client selects exactly the policy fields it needs, single endpoint |

## Endpoints (all verified live)

| Endpoint | Protocol | Verified behavior |
|---|---|---|
| `POST /claim-processing/api/claims/submit` | REST | Runs the full 3-gateway orchestration, returns decision JSON |
| `GET /claim-processing/api/claims/health` | REST | `{"status":"UP","service":"ClaimSubmissionService"}` |
| `GET /claim-processing/services/IdentityVerification?wsdl` | SOAP | Returns generated WSDL (HTTP 200) |
| `POST /claim-processing/graphql` | GraphQL | `validatePolicy` / `policy` / `policiesByUser` queries return mock policy data |
| `AnalyzeClaim` on `localhost:50051` | gRPC | Returns risk score, level, red flags |

Example GraphQL call (verified):

```graphql
query { validatePolicy(policyId: "POL-001", claimAmount: 5000.0) {
  policyId isValid status message coverageLimit } }
# -> {"isValid":true,"status":"VALID","coverageLimit":50000.0}
```

## Demo Clients

`run-demo-java.bat` runs these directly with `java -cp` (no Maven needed after packaging):

| Menu | Class | What it exercises |
|---|---|---|
| 1 | `client.SoapClient` | Identity service logic (direct in-process call; use SoapUI/Postman against the WSDL for wire-level SOAP) |
| 2 | `grpc.FraudDetectionClient` | Real gRPC call to :50051 — fraud analysis + statistics RPC |
| 3 | `client.GraphQLClient` | HTTP POST to `/graphql` — policy queries |
| 4 | `client.RestClient` | HTTP POST to `/api/claims/submit` — full orchestration |
| 5 | All four in sequence | |

## Scope and Limitations (read before judging the code)

This is a university SOA course project (Télécom SudParis MSc), built to demonstrate protocol integration and orchestration patterns — not a production claims system:

- Business logic is intentionally mock: identity passes when the document ID is 8+ characters; fraud is a fixed rule table; policies are hard-coded in `PolicyDataFetcher`. No database — everything is in-memory.
- Inside the orchestrator, the SOAP service and GraphQL engine are invoked in-process; the SOAP/GraphQL wire endpoints exist and are verified, but the orchestrator itself only crosses the network for gRPC and the inbound REST call.
- The fraud gateway fails open: if the gRPC server on :50051 is not running, the orchestrator logs a warning and skips the fraud check (`InsuranceClaimOrchestrator.java`, lines 117-123). Start `start-grpc-java.bat` first.
- `GET /api/claims/{claimId}` is a stub that always returns `PENDING`.
- Windows-oriented tooling (`.bat` scripts). On Linux/macOS use `mvn clean package`, copy the WAR to Tomcat, and run the same client classes with `java -cp`.

## Tech Stack

Java 11 · Maven (WAR packaging, protobuf-maven-plugin for codegen) · Apache Tomcat 9 · Jersey 2.35 (JAX-RS) · JAX-WS RI 2.3.5 · gRPC 1.58.0 + Protocol Buffers 3.24.0 · graphql-java 19.2 · Gson 2.10.1

## Documentation

Detailed docs live in [`docs/`](docs/):

- [Architecture Overview](docs/technical-docs/Architecture_Overview.md) — design patterns, component diagram
- [Deployment Guide](docs/technical-docs/Deployment_Guide.md) — step-by-step environment setup
- [Service Endpoints](docs/technical-docs/Service_Endpoints.md) — full API reference with request/response examples
- [Testing Guide](docs/technical-docs/Testing_Guide.md) — test scenarios per service
- [Postman collection](docs/API_Documentation/Insurance_Claim_Processing.postman_collection.json)

## Project Structure

```
src/main/java/com/insurance/
├── service/        REST claim submission (Jersey)
├── soap/           SOAP identity verification (JAX-WS)
├── grpc/           gRPC fraud detection server + client
├── graphql/        GraphQL policy validation (servlet + data fetcher)
├── orchestrator/   InsuranceClaimOrchestrator — 3 XOR gateways
├── client/         Demo clients (REST / SOAP / gRPC / GraphQL)
└── dto/            ClaimRequest / ClaimResponse
src/main/proto/     fraud_detection.proto
src/main/resources/ schema.graphql
```
