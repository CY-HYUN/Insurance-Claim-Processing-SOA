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

### **3. 컴파일 (발표 15분 전) ⚠️ 중요!**

#### **방법 1: 전체 빌드 (추천 - 가장 확실함)**
```powershell
cd "D:\Study\Github\Insurance-Claim-Processing-SOA"
.\build-and-deploy.bat
```

**예상 출력:**
```
[Step 1/3] Cleaning and compiling project...
✓ Compilation successful
[Step 2/3] Generating gRPC classes...
✓ gRPC classes generated
[Step 3/3] Packaging WAR file...
✓ WAR file created
```

#### **방법 2: 컴파일만 (Maven 경로 자동 감지)**
```powershell
.\compile-classes.bat
```

**⚠️ 이 단계를 건너뛰면 "ClassNotFoundException" 에러 발생!**
**💡 Tip: 방법 1이 안 되면 방법 2를 시도하거나, IntelliJ에서 Maven Compile 실행**

---

### **4. 서버 시작 (발표 10분 전)**

#### **Terminal 1: Tomcat**
```powershell
cd "D:\Study\Github\Insurance-Claim-Processing-SOA"
.\start-tomcat.bat
```

**예상 출력:**
```
Using JAVA_HOME: C:\Program Files\Microsoft\jdk-11.0.16.101-hotspot
Server startup in [xxxx] milliseconds
```

#### **Terminal 2: gRPC Server**
```powershell
cd "D:\Study\Github\Insurance-Claim-Processing-SOA"
.\start-grpc-java.bat
```

**예상 출력:**
```
gRPC Fraud Detection Server started
Listening on port: 50051
```

#### **Terminal 3: Demo 준비**
```powershell
cd "D:\Study\Github\Insurance-Claim-Processing-SOA"
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

[출력이 나타남 - Terminal 3 화면에 집중]

"First, the SOAP service for Identity Verification.

[마우스로 출력 가리키며]
The SOAP client runs THREE test cases to demonstrate different scenarios.

Looking at Test Case 1, this is a Valid Identity scenario.
The User ID is U-S-R dash one-two-three, the name is John Doe,
and you can see the verification result shows a green checkmark - PASSED -
with a confidence score of ninety-five percent.
This demonstrates successful identity verification.

Now Test Case 2 shows an Invalid Document scenario.
The User ID is U-S-R dash four-five-six,
and here the verification result shows a red X - FAILED -
with only a forty-five percent confidence score.
The system detected an invalid document.

And Test Case 3 demonstrates Missing Information.
The User ID is U-S-R dash seven-eight-nine,
the verification result is FAILED with zero percent confidence
because required fields are missing.

[30초 휴지 - 출력 읽을 시간 제공]

I chose SOAP for identity verification for four main reasons.
First, it provides enterprise-level security with WS-Security standards.
Second, strict protocols are required for handling personal data.
Third, the WSDL contract ensures type safety and eliminates ambiguity.
And fourth, SOAP is compatible with legacy banking and government systems
that our insurance company needs to integrate with.

The WSDL contract is publicly available at this endpoint.
[브라우저 탭으로 전환 또는 화면에 URL 표시]
http://localhost:8080/claim-processing/services/IdentityVerification?wsdl

This WSDL endpoint provides the complete service contract
defining all available operations, input parameters, and output types."

[Press any key to continue... → Enter]
[Terminal 3으로 다시 전환]
```

#### **Test 2: gRPC Service (1분)**

```
=== Running gRPC Client ===
=== Testing Fraud Detection ===

[출력이 나타남 - Terminal 3 화면 유지]

"Next, the gRPC service for Fraud Detection.

[마우스로 출력 가리키며]
Looking at the fraud detection analysis results,
the Claim ID is C-L-M dash zero-zero-one.
The system determined this is NOT fraudulent - you can see 'Is Fraudulent: false'.
However, the Risk Score is zero-point-three, which falls in the MEDIUM risk level.
The recommendation is MANUAL_REVIEW, meaning a human agent should examine this claim.
The explanation states 'Medium fraud risk detected',
and you can see the red flag: High claim amount exceeding fifty thousand dollars.

[아래로 스크롤하며]
The service also provides aggregated statistics.
Total Claims Analyzed: one thousand claims.
Fraud Detected: forty-five cases.
This gives us a Fraud Rate of four-point-five percent.
And the Total Amount Saved is one hundred twenty-five thousand dollars.
This demonstrates real-time fraud analysis capabilities at scale.

[30초 휴지]

I chose gRPC for fraud detection for four key reasons.
First, real-time low-latency analysis is critical for fraud detection.
Second, the binary Protocol Buffers format is five to ten times faster than JSON.
Third, we need high performance to process thousands of claims per second.
And fourth, gRPC supports bidirectional streaming for continuous monitoring
of fraud patterns across multiple claims simultaneously.

The service contract is defined in the dot-proto file using Protocol Buffers.

[Optional: Terminal 2로 화면 전환]
And if you look at Terminal 2, the gRPC server logs show
the incoming requests in real-time.
You can see: Claim ID C-L-M dash zero-zero-one,
Amount seventy-five thousand dollars, Risk level MEDIUM.
This demonstrates the real-time client-server communication
happening between our Java client and the gRPC server."

[Press any key to continue... → Enter]
[Terminal 3으로 다시 전환]
```

#### **Test 3: GraphQL Service (1분)**

```
=== Running GraphQL Client ===
=== Testing GraphQL Policy Service ===

[출력이 나타남 - Terminal 3 화면 유지]

"Then, the GraphQL service for Policy Validation.

[마우스로 출력 가리키며]
The GraphQL client demonstrates FIVE different test cases
showing GraphQL's flexible query capabilities.

Test Case 1 retrieves a specific Policy by ID.
You can see Policy ID P-O-L dash zero-zero-one is an AUTO insurance policy
with ACTIVE status and fifty thousand dollars coverage amount.

Test Case 2 gets all Policies for a specific User.
It returns multiple policies for user U-S-R dash one-two-three,
showing both ACTIVE and EXPIRED policies in a single query.

Test Case 3 validates a Policy for a Valid Claim.
Policy P-O-L dash zero-zero-one returns 'Is Valid: true',
Status is VALID, and the message confirms the policy is valid for this claim.

Test Case 4 tests a claim that Exceeds Coverage.
When the claim amount is seventy-five thousand dollars,
'Is Valid' returns false, Status shows EXCEEDS_COVERAGE,
and the error message states 'Claim exceeds coverage limit'.

And Test Case 5 validates an Inactive Policy.
Policy P-O-L dash zero-zero-three returns 'Is Valid: false',
Status is INACTIVE, and the error indicates the policy is expired.

[30초 휴지]

I chose GraphQL for policy validation for five key reasons.
First, flexible queries allow the client to request only the fields it needs.
Second, this reduces over-fetching compared to traditional REST APIs.
Third, we have a single endpoint for all policy-related queries
instead of multiple REST endpoints.
Fourth, the strongly typed schema provides self-documentation
so developers know exactly what fields are available.
And fifth, GraphQL is perfect for complex policy validation logic
where different clients need different subsets of policy data.

GraphQL schema introspection is available at this endpoint.
[브라우저 탭으로 전환 또는 화면에 URL 표시]
http://localhost:8080/claim-processing/graphql

You can use this endpoint to query the GraphQL schema
and explore all available queries, mutations, and types interactively."

[Press any key to continue... → Enter]
[Terminal 3으로 다시 전환]
```

#### **Test 4: REST Complete Workflow (2.5분)**

```
=== Running REST Client ===
=== Testing REST Service ===

[출력이 나타남 - Terminal 3 화면 유지]

"Finally, the REST service orchestrates the complete workflow.

[출력 상단을 가리키며]
You can see the claim submission:
Claim ID is C-L-M dash R-E-S-T dash zero-zero-one,
and the Amount is five thousand dollars.

[Response 섹션을 가리키며]
Now looking at the Response section, let me explain what happened behind the scenes.

The Status is APPROVED.
The Message states: Claim approved successfully.

Now, these three fields show the results from our three backend services:

[Identity Verified를 가리키며]
Identity Verified shows 'true'.
This means the SOAP Identity Verification Service successfully verified the customer.

[Fraud Check Passed를 가리키며]
Fraud Check Passed shows 'true'.
This means the gRPC Fraud Detection Service analyzed the claim
and determined it was not fraudulent with LOW risk level.

[Policy Status를 가리키며]
Policy Status shows 'VALID'.
This means the GraphQL Policy Validation Service confirmed
the policy is active and covers this claim amount.

All three validation checks passed,
so the final Status is APPROVED.
You can also see the Timestamp showing when this claim was processed.

[30초 휴지]

I chose REST for claim submission for four practical reasons.
First, claim submission involves simple CRUD operations -
Create, Read, Update, Delete.
Second, the JSON format is web-friendly and easy to parse
for front-end applications and mobile apps.
Third, REST's stateless protocol is perfect for independent claim submissions
where each request stands alone.
And fourth, REST is the industry standard for web APIs,
making it easy for external systems to integrate with our platform.

This demonstration shows the complete service orchestration.
The REST service acts as the coordinator or orchestrator,
calling SOAP for identity verification,
then gRPC for fraud detection,
and finally GraphQL for policy validation - all in sequence.

This is how all four services work together
to process an insurance claim from start to finish."
```

---

### **[6:00-8:30] Rejection Case Demo (2.5분)**

```
[Demo Complete 화면 - Terminal 3]

"Now let me demonstrate a rejection case to show the gateway logic in action.

I will change the claim amount to trigger high-risk fraud detection."

[VSCode 화면으로 전환]
[RestClient.java 파일이 이미 열려있음 - Line 102 근처]

"I'm modifying the claim amount from five thousand dollars to five hundred thousand dollars.
This high amount will trigger the fraud detection system."

[마우스로 Line 102 가리키며 5000.0 → 500000.0 수정]
[Ctrl+S 저장]

"Now I need to recompile the modified class."

[Terminal 3으로 전환]
.\recompile-restclient.bat

[컴파일 완료 대기 - 5초 정도]

"Compilation successful. Let me run the REST client again."

.\run-demo-java.bat

[선택 화면이 나타남]
"I'll select option 4 to run only the REST service."

[4 입력 → Enter]
[Press any key to continue 메시지 → Enter]

[출력이 나타남]

"Notice the submitting claim amount is now five hundred thousand dollars.

[출력을 위에서 아래로 천천히 읽으며 설명]

Looking at the response here,
the Status is REJECTED.
The Message states: Fraud detected, High fraud risk detected,
Thorough investigation required.

Now let me explain each field:

Identity Verified shows 'true' -
the first check passed successfully.

Fraud Check Passed shows 'false' -
this is where the claim was rejected.
The fraud detection system flagged this five hundred thousand dollar claim as high risk.

Now look very carefully at the Policy Status field.
[Policy Status: null 부분을 마우스로 가리키며]
It shows 'null' - there's nothing there.

This is the key point that demonstrates our XOR gateway logic.

The workflow stopped immediately after fraud detection failed.
Step 3 - Policy Validation - was never executed.
The system didn't even attempt to validate the policy
because the fraud detection already rejected the claim.

[잠깐 멈춤 - Terminal 3 화면 그대로 유지, 출력 전체가 보이도록]

As Thijmen explained in his presentation using the BPMN diagram,
this demonstrates the XOR gateway logic.
XOR means 'exclusive or' - only ONE path can be taken at each gateway.

At the Identity Verification gateway, the claim passed, so it continued.
At the Fraud Detection gateway, high risk was detected,
so the workflow immediately took the rejection path.
The Policy Validation step was completely skipped.

This is efficient design.
We don't waste computational resources validating a policy
for a claim that's already been flagged as fraudulent.

This is the gateway logic that Thijmen designed in the BPMN workflow diagram,
and I implemented it in the Java orchestration code in InsuranceClaimOrchestrator dot java.

The code checks each service result,
and uses conditional statements - if-else logic - to implement the XOR gateways.
When fraud is detected, it returns REJECTED immediately
without calling the GraphQL policy validation service."
```

---

### **[8:30-10:00] Summary & Q&A (1.5분)**

```
[Terminal 3 화면 또는 전체 화면 공유 상태]

"To summarize what we just demonstrated today.

First, we tested each of the four services individually.
We tested SOAP for secure identity verification with three different test cases.
We tested gRPC for high-performance fraud detection with real-time analysis.
And we tested GraphQL for flexible policy queries with five different query scenarios.

Second, we demonstrated the complete workflow integration.
The REST service orchestrates all three services together.
The execution is sequential: first Identity Verification,
then Fraud Detection, and finally Policy Validation.
And we implemented XOR gateway logic in the Java code
to control the workflow based on each service's result.

Third, we demonstrated two different cases.
In the approval case, a five thousand dollar claim was APPROVED
because it passed all three validation checks.
In the rejection case, a five hundred thousand dollar claim was REJECTED
due to high fraud risk detected by the gRPC service.
And you saw how the Policy Validation step was completely skipped
when fraud was detected - demonstrating the XOR gateway logic.

All four services are fully testable and documented.
For SOAP, we have the WSDL contract available at the service endpoint.
For gRPC, we have the Protocol Buffers dot-proto file defining the service contract.
For GraphQL, schema introspection is available at the GraphQL endpoint.
And for REST, we demonstrated using Java application client code.

As the professor required in the project guidelines,
we used a Java Application Client - not Swagger or Postman -
to demonstrate the complete workflow execution.

The workflow can execute from start to end,
correctly handling both approval and rejection cases
according to the business logic we implemented.

[잠깐 멈춤]

This completes our live demonstration.

Are there any questions about the implementation,
the technology choices, or the gateway logic?

[Q&A 대기]

Thank you for your attention."
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
