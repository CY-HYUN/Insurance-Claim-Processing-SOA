# 🎬 Live Demo Guide - Insurance Claim Processing SOA

**Live Demo Only (10 minutes) - After PPT Presentation**

---

## 🌐 온라인 발표 정보

| 항목 | 내용 |
|------|------|
| **날짜** | 내일 아침 |
| **방식** | 온라인 (Web Conference) |
| **접속 링크** | https://webconf.imt.fr/frontend/rooms/wal-sdy-iyf-j9h/join |
| **총 시간** | 20분 (PPT 10분 + Live Demo 10분) |

---

## 📋 발표 구조 (20분)

### **Part 1: PPT Presentation (10분) - Thijmen**
- Introduction & Problem Statement
- Architecture Overview
- Service Technologies Explanation
- BPMN Workflow Design
- Gateway Logic (XOR, AND, OR)
- Why each technology was chosen

### **Part 2: Live Demo (10분) - Changyong (You)** ⭐
- Server startup demonstration
- Service testing (SOAP, gRPC, GraphQL, REST)
- Complete workflow execution
- Approval case demo
- Rejection case demo

---

## ⚙️ 발표 전 준비 (30분 전)

### **1. 온라인 환경 설정**
- [ ] **유선 인터넷 연결** (Wi-Fi보다 안정적)
- [ ] **노트북 충전 100%**
- [ ] **조용한 환경** 확보
- [ ] **알림/메시지 끄기** (방해 금지 모드)

### **2. 접속 테스트 (필수!)**
```
🔗 https://webconf.imt.fr/frontend/rooms/wal-sdy-iyf-j9h/join

테스트 항목:
✅ 링크 클릭 → 회의실 입장
✅ 마이크 테스트 → "Can you hear me?" 녹음 재생
✅ 화면 공유 테스트 → 전체 화면 선택 연습
✅ 카메라 테스트 (선택)
```

### **3. 서버 시작 (발표 10분 전)**

#### **Terminal 1: Tomcat**
```powershell
cd "d:\Study\Github\TSP\Service Oriented Computing\Project\Insurance-Claim-Processing-SOA"
.\start-tomcat.bat
```

**예상 출력:**
```
Using JAVA_HOME: C:\Program Files\Microsoft\jdk-11.0.16.101-hotspot
Server startup in [xxxx] milliseconds
```

#### **Terminal 2: gRPC Server**
```powershell
cd "d:\Study\Github\TSP\Service Oriented Computing\Project\Insurance-Claim-Processing-SOA"
.\start-grpc-java.bat
```

**예상 출력:**
```
gRPC Fraud Detection Server started
Listening on port: 50051
```

#### **Terminal 3: Demo 준비**
```powershell
cd "d:\Study\Github\TSP\Service Oriented Computing\Project\Insurance-Claim-Processing-SOA"
# 대기 상태 (명령어 입력 준비)
```

---

## 🎯 Live Demo Script (10분)

### **[0:00-0:30] Introduction (Handoff from Thijmen)**

```
[Thijmen finishes PPT presentation]

Thijmen: "Now I will hand over to Changyong for the live demonstration."

You: "Thank you, Thijmen. Good morning, Professor.

I will now demonstrate our Insurance Claim Processing System
with live service calls.

As you can see, I have already started all servers:
- Terminal 1: Tomcat for REST, SOAP, and GraphQL
- Terminal 2: gRPC server for fraud detection

[화면 공유 - Terminal 화면 보여주기]

Let me show you the servers are running..."

[Terminal 1 가리키며]
"Tomcat is running on port 8080"

[Terminal 2 가리키며]
"gRPC server is listening on port 50051"
```

---

### **[0:30-6:00] Main Demo - All Services Test (5.5분)**

```
[Terminal 3으로 전환]

"I will now run all service tests to demonstrate each technology
individually, and then show the complete integration.

As Professor mentioned in the guidelines,
I'm using a Java Application Client - not Postman or Swagger -
to invoke all the services."

[명령어 입력]
.\run-demo-java.bat

[선택 화면이 나타남]
"I will select option 5 to run all tests."

[5 입력]
```

#### **Test 1: SOAP Service (1분)**

```
=== Running SOAP Client ===
=== Testing SOAP Identity Verification Service ===

Test Case 1: Valid Identity
--------------------------------------------------

[출력이 나타남]

"First, the SOAP service for Identity Verification.

[출력 가리키며]
The SOAP client runs THREE test cases to demonstrate different scenarios:

Test Case 1 - Valid Identity:
- User ID: USR-123
- Name: John Doe
- Verification Result: ✓ PASSED
- Confidence Score: 95%
- This shows successful identity verification

Test Case 2 - Invalid Document:
- User ID: USR-456
- Verification Result: ✗ FAILED
- Confidence Score: 45%
- Invalid document detected

Test Case 3 - Missing Information:
- User ID: USR-789
- Verification Result: ✗ FAILED
- Confidence Score: 0%
- Missing required fields

I chose SOAP for identity verification because:
1. Enterprise-level security with WS-Security standards
2. Strict protocols required for handling personal data
3. WSDL contract ensures type safety
4. Compatible with legacy banking and government systems

The WSDL is available at:
http://localhost:8080/claim-processing/services/IdentityVerification?wsdl"

[Press any key to continue... → Enter]
```

#### **Test 2: gRPC Service (1분)**

```
=== Running gRPC Client ===
=== Testing Fraud Detection ===

[출력이 나타남]

"Next, the gRPC service for Fraud Detection.

[출력 가리키며]
The fraud detection analysis shows:
- Claim ID: CLM-001
- Is Fraudulent: false
- Risk Score: 0.3
- Risk Level: MEDIUM
- Recommendation: MANUAL_REVIEW
- Explanation: Medium fraud risk detected
- Red Flags: High claim amount (> $50,000)

The service also provides statistics:
- Total Claims Analyzed: 1,000
- Fraud Detected: 45 cases
- Fraud Rate: 4.5%
- Total Amount Saved: $125,000

This demonstrates real-time fraud analysis capabilities.

I chose gRPC for fraud detection because:
1. Real-time, low-latency analysis is critical
2. Binary Protocol Buffers format is 5-10x faster than JSON
3. High performance needed for processing thousands of claims per second
4. Supports bidirectional streaming for continuous monitoring

The .proto file defines the service contract.

[Optional: 서버 터미널 가리키기]
And if you look at Terminal 2, the gRPC server logs show
the incoming requests in real-time:
- Claim ID: CLM-001, Amount: $75,000, Risk: MEDIUM
- This demonstrates real-time client-server communication."

[Press any key to continue... → Enter]
```

#### **Test 3: GraphQL Service (1분)**

```
=== Running GraphQL Client ===
=== Testing GraphQL Policy Service ===

[출력이 나타남]

"Then, the GraphQL service for Policy Validation.

[출력 가리키며]
The GraphQL client demonstrates FIVE different test cases:

Test Case 1 - Get Policy by ID:
- Policy ID: POL-001
- Policy Type: AUTO
- Status: ACTIVE
- Coverage Amount: $50,000

Test Case 2 - Get Policies by User:
- Returns multiple policies for user USR-123
- Shows both ACTIVE and EXPIRED policies

Test Case 3 - Validate Policy (Valid Claim):
- Policy ID: POL-001
- Is Valid: true
- Status: VALID
- Message: Policy is valid for claim

Test Case 4 - Validate Policy (Exceeds Coverage):
- Claim amount: $75,000
- Is Valid: false
- Status: EXCEEDS_COVERAGE
- Error: Claim exceeds coverage limit

Test Case 5 - Validate Policy (Inactive Policy):
- Policy ID: POL-003
- Is Valid: false
- Status: INACTIVE
- Error: Policy is expired

This demonstrates GraphQL's flexible query capabilities.

I chose GraphQL because:
1. Flexible queries - client requests only needed fields
2. Reduces over-fetching compared to REST
3. Single endpoint for all policy-related queries
4. Strongly typed schema provides self-documentation
5. Perfect for complex policy validation logic

GraphQL schema introspection is available at:
http://localhost:8080/claim-processing/graphql"

[Press any key to continue... → Enter]
```

#### **Test 4: REST Complete Workflow (2.5분)**

```
=== Running REST Client ===

[출력이 나타남]

"Finally, the REST service orchestrates the complete workflow.

Watch how it calls all three services in sequence:

[Step 1이 나타남]
Step 1: SOAP Identity Verification
✓ Identity verified successfully

[Step 2가 나타남]
Step 2: gRPC Fraud Detection
✓ Fraud check passed - Risk Level: LOW

[Step 3이 나타남]
Step 3: GraphQL Policy Validation
✓ Policy validated successfully

[최종 결과]
✓ CLAIM APPROVED - All validation checks passed

I chose REST for claim submission because:
1. Simple CRUD operations
2. JSON format is web-friendly
3. Stateless protocol - perfect for independent submissions
4. Industry standard for web APIs

This demonstrates the complete service orchestration.
The REST service acts as the coordinator,
calling SOAP, gRPC, and GraphQL in sequence.

This also demonstrates the XOR gateway logic from our BPMN:
- If identity fails → REJECT immediately
- If fraud detected → REJECT immediately
- All checks pass → APPROVE"
```

---

### **[6:00-8:30] Rejection Case Demo (2.5분)**

```
[Demo Complete 화면]

"Now let me demonstrate a rejection case.

I will change the claim amount to trigger high-risk fraud detection."

[VSCode 화면으로 전환]
[RestClient.java 파일 열기 - Line 102]

"I'm modifying the claim amount from $5,000 to $500,000 -
a very high amount that will trigger fraud detection."

[5000.0 → 500000.0 수정]
[Ctrl+S 저장]

"Now I need to recompile the class."

[Terminal 3으로 전환]
.\recompile-restclient.bat

[컴파일 완료]

"Compilation successful. Now I'll run the demo again."

.\run-demo-java.bat

[4 입력 - REST만 실행]

[출력이 나타남]

"Notice the amount is now $500,000 - a very high amount.

[출력 확인]

Submitting claim: CLM-REST-001
Amount: $500,000.0

[Step 1]
Step 1: Identity Verification - ✓ PASSED
Identity Verified: true

[Step 2]
Step 2: Fraud Detection...
Look here - the fraud detection triggered:
- Fraud Check Passed: false
- Risk Level: HIGH (from gRPC service)
- Explanation: High fraud risk detected. Thorough investigation required.

[최종 결과]
❌ Status: REJECTED
Message: Fraud detected: High fraud risk detected

The workflow stops immediately.
Notice that Policy Status is 'null' -
Step 3 (Policy Validation) was NOT executed.

This demonstrates the XOR gateway logic:
When fraud is detected at Step 2, we don't proceed to Step 3.
The claim is rejected immediately to prevent fraudulent payments.

[Optional: If Thijmen has BPMN ready]
This is exactly what Thijmen designed in the BPMN diagram -
the XOR gateway after fraud detection splits the flow:
- If fraud detected → REJECT (our case)
- If no fraud → Continue to policy validation

[If BPMN not ready - Show Code Implementation]
Although we don't have a visual BPMN diagram ready,
I implemented the gateway logic in the orchestrator code.

[VSCode로 전환 - InsuranceClaimOrchestrator.java]
Let me show you the actual implementation:

[Line 82-87 가리키기]
Here's the XOR gateway for identity verification:
'if (!verificationResult.isVerified()) { return REJECTED; }'
This stops the workflow immediately if identity check fails.

[Line 108-114 가리키기]
And here's the XOR gateway for fraud detection:
'if (fraudResult.getIsFraudulent()) { return REJECTED; }'
This is what triggered in our demo - high fraud risk stops the workflow.

The gateway logic is implemented as conditional statements
that control the execution flow - exactly like BPMN XOR gateways."
```

---

### **[8:30-10:00] Summary & Q&A (1.5분)**

```
"To summarize what we just demonstrated:

1. Individual Service Tests
   - SOAP for secure identity verification
   - gRPC for high-performance fraud detection
   - GraphQL for flexible policy queries

2. Complete Workflow Integration
   - REST orchestrates all services
   - Sequential execution: Identity → Fraud → Policy
   - XOR gateway logic in code

3. Two Cases Demonstrated
   - Approval case: $5,000 → APPROVED
   - Rejection case: $50,000 → REJECTED (High fraud risk)

All services are testable and documented:
- SOAP: WSDL contract
- gRPC: Protocol Buffers .proto file
- GraphQL: Schema introspection
- REST: Java client demonstrations

As the professor required in the guidelines,
we used an Application Client - not Swagger or Postman -
to demonstrate the workflow.

The workflow can execute from start to end,
handling both approval and rejection cases correctly.

Are there any questions about the implementation
or the technology choices?

Thank you."
```

---

## 📋 발표 직전 최종 체크리스트

### **10분 전:**
- [ ] **회의실 입장**: https://webconf.imt.fr/frontend/rooms/wal-sdy-iyf-j9h/join
- [ ] **마이크 테스트**: "Can you hear me?"
- [ ] **화면 공유 테스트**: 전체 화면 선택 연습
- [ ] **Terminal 1**: Tomcat 실행 중
- [ ] **Terminal 2**: gRPC 서버 실행 중
- [ ] **Terminal 3**: 프로젝트 폴더 위치
- [ ] **VSCode**: RestClient.java 파일 열어두기 (Line 102)
- [ ] **브라우저**: WSDL 탭 열어두기 (선택)
- [ ] **터미널 폰트 크기 확대** (16-18pt) - 원격 시청자 가독성
- [ ] **물 준비**
- [ ] **심호흡 3회** 🧘

---

## 🎯 화면 공유 순서

```
1. Terminal 1, 2 (서버 실행 확인) - 30초
2. Terminal 3 (Demo 실행) - 5.5분
   - Test 1: SOAP
   - Test 2: gRPC
   - Test 3: GraphQL
   - Test 4: REST (Complete Workflow)
3. VSCode (RestClient.java 수정) - 30초
4. Terminal 3 (재컴파일) - 30초
5. Terminal 3 (Rejection Demo) - 1분
6. Summary - 1.5분
```

---

## 💡 온라인 발표 필수 팁

### **말하기 속도**
- 🐢 **20% 더 천천히** - 온라인은 딜레이가 있음
- 🔊 **명확한 발음** - "SOAP", "gRPC", "GraphQL" 또렷하게
- ⏸️ **문장 끝에 1초 휴지** - 청취자가 이해할 시간

### **화면 공유**
- 🖱️ **마우스로 가리키며 설명** - "As you can see **here**..."
- 👀 **"Can you see my screen?"** - 화면 공유 시작 시 필수
- 🔍 **터미널 폰트 크기** - 16-18pt (원격 시청자 가독성)

### **에러 대처**
- 😌 **침착하게**: "Let me quickly restart the service."
- 💬 **설명으로 대체**: "The expected output shows APPROVED..."
- 📷 **백업 스크린샷**: 예상 출력 미리 캡처

### **질문 대응**
- ⏳ **2-3초 대기** - 교수님 음소거 해제 시간
- 🔁 **질문 반복**: "You asked about why we chose gRPC...?"
- 🤝 **팀원에게 패스**: "Thijmen designed the BPMN, so..."

---

## 🔧 트러블슈팅

### **서버 실행 안 될 때:**
```powershell
# Tomcat 재시작
.\stop-tomcat.bat
.\start-tomcat.bat

# gRPC 재시작
[Ctrl+C in Terminal 2]
.\start-grpc-java.bat
```

### **Demo 실행 안 될 때:**
```powershell
# 재컴파일
.\recompile-restclient.bat

# 다시 실행
.\run-demo-java.bat
```

### **화면 공유 문제:**
- 특정 창 선택보다 **전체 화면 공유** 권장
- 여러 터미널 전환 시 미리 배치 확인

---

## 📐 BPMN 다이어그램 시연 (팀원이 준비한 경우)

### **Option 1: Thijmen이 PPT에서 설명 (추천)**

Thijmen이 PPT 발표 중에 BPMN 다이어그램을 설명하고,
당신은 Live Demo에서 "This is what Thijmen showed in the BPMN" 만 언급

```
[Rejection Case 설명 중]

"As you can see, the workflow stopped at Step 2.
This is exactly what Thijmen showed in the BPMN diagram -
the XOR gateway after fraud detection."
```

### **Option 2: 당신이 간단히 설명 (시간 여유 있으면)**

만약 Thijmen이 BPMN 이미지 파일을 준비했다면:

```
[Summary 전에 BPMN 이미지 화면 공유]

"Let me quickly show you the BPMN workflow that Thijmen designed.

[BPMN 다이어그램 가리키며]

Here you can see:
1. Start Event → Claim Submission
2. Task: Identity Verification (SOAP)
3. XOR Gateway: Identity Check
   - If failed → Reject (End)
   - If passed → Continue
4. Task: Fraud Detection (gRPC)
5. XOR Gateway: Fraud Check ← This is where we are
   - If fraud detected → Reject (our demo)
   - If no fraud → Continue to Policy Validation
6. Task: Policy Validation (GraphQL)
7. End Event: Approved

The gateway logic we just demonstrated in code
is represented by these XOR diamond symbols in the BPMN.

Thijmen designed this workflow, and I implemented
the orchestration logic in the REST service."
```

### **Option 3: BPMN 준비 안 된 경우 (백업)**

BPMN 다이어그램이 없어도 괜찮습니다:

```
[Rejection Case 설명 중]

"The workflow implements gateway logic:

[손으로 공중에 그리거나 화이트보드처럼 설명]

Claim Submission
    ↓
Identity Check (XOR Gateway)
    ↓ (if passed)
Fraud Detection (XOR Gateway) ← We are here
    ↓ (if no fraud)
Policy Validation
    ↓
Approval

In our rejection demo, the fraud detection gateway
detected high risk and rejected the claim immediately.
This XOR gateway ensures we don't waste resources
validating policies for fraudulent claims."
```

### **BPMN 관련 질문 대응**

**교수님: "Where is the BPMN diagram?"**

**Option A (Thijmen이 준비함):**
```
"Thijmen showed the BPMN diagram in his presentation.
I implemented the gateway logic in the code.
Would you like me to show the code implementation?"
```

**Option B (준비 안 됨):**
```
"We implemented the workflow logic in code.
The XOR gateways are implemented as conditional statements:
- If identity fails → return REJECTED
- If fraud detected → return REJECTED
Let me show you the code..."

[VSCode → InsuranceClaimOrchestrator.java 열기]
[Line 82-87, 108-114 보여주기]
```

---

## 📊 타임라인 요약 (10분)

| 시간 | 활동 | 소요 시간 |
|------|------|----------|
| 0:00-0:30 | Introduction & Server 확인 | 30초 |
| 0:30-1:30 | Test 1: SOAP | 1분 |
| 1:30-2:30 | Test 2: gRPC | 1분 |
| 2:30-3:30 | Test 3: GraphQL | 1분 |
| 3:30-6:00 | Test 4: REST (Complete Workflow) | 2.5분 |
| 6:00-6:30 | 코드 수정 (VSCode) | 30초 |
| 6:30-7:00 | 재컴파일 | 30초 |
| 7:00-8:00 | Rejection Demo 실행 | 1분 |
| 8:00-8:30 | 결과 설명 | 30초 |
| 8:30-10:00 | Summary & Q&A | 1.5분 |

---

## ✅ 요구사항 충족 확인

### **Professor's Requirements (from guideline2.txt):**
- [x] **Demo of implementation** ✅
- [x] **Show implemented services** ✅ (SOAP, gRPC, GraphQL, REST)
- [x] **How to call them** ✅ (Java Application Client)
- [x] **NOT using Swagger/Postman** ✅ (run-demo-java.bat)
- [x] **Application Client** ✅ (Java clients for each service)
- [x] **Explain why each technology** ✅ (발표 대본에 포함)

### **Project Requirements (from guideline.txt):**
- [x] **REST resource** ✅ (ClaimSubmissionService)
- [x] **SOAP service** ✅ (IdentityVerificationService + WSDL)
- [x] **gRPC API** ✅ (FraudDetectionServer + .proto)
- [x] **GraphQL API** ✅ (PolicyValidationService + Schema)
- [x] **APIs test and documentation** ✅ (Java clients + WSDL/Proto/Schema)
- [x] **Correct procedures, complete execution** ✅ (Start to finish demo)
- [x] **Gateway logic (XOR)** ✅ (코드에 구현, 발표 시 설명)
- [x] **Approval case** ✅ ($5,000 → APPROVED)
- [x] **Rejection case** ✅ ($50,000 → REJECTED)

---

## 🎓 핵심 메시지

**Professor가 보고 싶어 하는 것:**
1. ✅ **Working system** - 실제 작동하는 시스템
2. ✅ **All 4 technologies** - REST, SOAP, gRPC, GraphQL
3. ✅ **Technology justification** - 왜 각 기술을 선택했는지
4. ✅ **Application Client** - Swagger/Postman 아닌 실제 클라이언트
5. ✅ **Complete workflow** - 시작부터 끝까지 실행
6. ✅ **Approval & Rejection** - 두 가지 케이스 모두

**모두 준비되었습니다!** 🎉

---

## 🚀 최종 점검

**내일 아침 발표 순서:**
1. **Thijmen**: PPT 발표 (10분) - Architecture, BPMN, Gateway 설명
2. **You**: Live Demo (10분) - 실제 서비스 실행 시연

**당신의 역할:**
- ✅ 서버 시작 보여주기
- ✅ 4개 서비스 개별 테스트 (SOAP, gRPC, GraphQL)
- ✅ 통합 워크플로우 시연 (REST)
- ✅ 기술 선택 이유 간단히 설명 (각 서비스당 30초)
- ✅ Rejection case 시연 (금액 수정)
- ✅ Q&A 대응

**Thijmen의 역할:**
- ✅ PPT 발표
- ✅ BPMN 다이어그램 설명
- ✅ Architecture overview
- ✅ Gateway 로직 이론 설명

---

**작성일**: 2026-01-27
**버전**: 2.0 (Live Demo Only)
**작성자**: Changyong Hyun with Claude Sonnet 4.5

**준비 완료! 발표 화이팅!** 🎓🚀
