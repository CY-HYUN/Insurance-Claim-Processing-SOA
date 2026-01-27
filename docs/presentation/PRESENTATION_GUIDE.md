# 🎤 발표 가이드 (Presentation Guide)

**프로젝트**: Insurance Claim Processing SOA
**발표 시간**: 10-12분
**발표자**: Changyong Hyun (실행 및 코드 설명) + Thijmen Welberg (PPT 제작 및 BPMN 설명)

---

## 🌐 온라인 발표 정보 (Online Presentation Info)

| 항목 | 내용 |
|------|------|
| **날짜** | 내일 아침 |
| **방식** | 온라인 (Web Conference) |
| **접속 링크** | https://webconf.imt.fr/frontend/rooms/wal-sdy-iyf-j9h/join |
| **플랫폼** | IMT Web Conference |

### ⚠️ 온라인 발표 추가 체크리스트
- [ ] **발표 30분 전**: 접속 링크 테스트
- [ ] **인터넷 연결**: 유선 연결 권장 (Wi-Fi보다 안정적)
- [ ] **마이크 테스트**: 음성 명확하게 들리는지 확인
- [ ] **화면 공유 테스트**: 전체 화면 or 특정 창 선택
- [ ] **카메라** (선택): 얼굴 보이면 더 좋음
- [ ] **조용한 환경**: 배경 소음 최소화
- [ ] **발표 10분 전 접속**: 기술적 문제 대비

### 🖥️ 화면 공유 순서 (Screen Sharing Order)
1. **PPT 슬라이드** - Introduction, Architecture
2. **터미널 창** - Demo 실행 (Orchestrator)
3. **VSCode** - 코드 수정 (금액 변경)
4. **터미널 창** - 재실행 (HIGH risk 시연)
5. **PPT 슬라이드** - 코드 설명, Gateway 로직
6. **BPMN 다이어그램** (시간 있으면)

### 💡 온라인 발표 팁
- **말하기 전 화면 공유 확인**: "Can you see my screen?"
- **천천히 말하기**: 온라인은 딜레이가 있음
- **주요 포인트 강조**: 마우스 포인터로 가리키며 설명
- **질문 대기**: 발표 후 잠시 기다리기 (음소거 해제 시간 필요)

---

## 📋 발표 전 준비사항 (Pre-presentation Checklist)

### ✅ 1. 서버 실행 확인 (발표 10분 전)

```bash
# Terminal 1: Tomcat 시작
C:\apache-tomcat-9.0.113\bin\startup.bat

# Terminal 2: gRPC 서버 시작
cd "D:\Study\Github\TSP\Service Oriented Computing\Project\Insurance-Claim-Processing-SOA"
mvn exec:java -Dexec.mainClass="com.insurance.grpc.FraudDetectionServer"

# Terminal 3: Demo용 대기 (발표 중 사용)
cd "D:\Study\Github\TSP\Service Oriented Computing\Project\Insurance-Claim-Processing-SOA"
# 명령어 입력 준비: mvn exec:java -Dexec.mainClass="com.insurance.orchestrator.InsuranceClaimOrchestrator"
```

### ✅ 2. VSCode 준비
- `InsuranceClaimOrchestrator.java` 파일 열기
- Line 50-55 위치 확인 (ClaimRequest 생성 부분)
- 금액 수정 준비: `4000.0` → `25000.0`

### ✅ 3. PPT 준비 (Thijmen)
- 9개 슬라이드 준비 완료 확인
- Slide 3-7: 코드 슬라이드 (REST, SOAP, gRPC, GraphQL, Gateway)
- 폰트 크기: 코드는 14pt 이상 (가독성)

### ✅ 4. 발표 역할 분담
- **Changyong**: Introduction (1분) + Live Demo (4-5분) + Code Explanation (4-5분) + Q&A
- **Thijmen**: PPT 조작 + BPMN 설명 (2-3분, 시간 있으면)

---

## 🎯 발표 흐름 (Presentation Flow)

### **Phase 1: Introduction** (1분)

**Changyong 발표**:
```
Good afternoon, Professor.

Today we will demonstrate our Insurance Claim Processing System
using Service-Oriented Architecture.

We implemented 4 different web service technologies:
1. REST for Claim Submission
2. SOAP for Identity Verification
3. gRPC for Fraud Detection
4. GraphQL for Policy Validation

And we integrated them using Java Orchestrator with XOR, AND, and OR gateways.

Let me start with a live demonstration.
```

---

### **Phase 2: Live Demo** (4-5분)

#### **Demo 1: 정상 승인 케이스** (2-3분)

**Changyong 발표**:
```
First, I will run our Java Application Client.
```

**터미널 명령어**:
```bash
mvn exec:java -Dexec.mainClass="com.insurance.orchestrator.InsuranceClaimOrchestrator"
```

**화면에 출력되는 내용 설명하기**:
```
As you can see, the workflow starts.

[Point 1] Step 1: REST service is called for claim submission.
The claim ID is generated: CLM-2025-001.

[Point 2] Step 2: SOAP service verifies the customer's identity.
The identity is verified successfully.

[Point 3] XOR Gateway: Since identity is verified, we continue to policy validation.

[Point 4] Step 3: GraphQL service validates the policy.
The policy is valid.

[Point 5] AND Gateway: Now we execute two services in parallel.
- Branch 1: gRPC service analyzes fraud risk.
- Branch 2: Document review.

Both branches complete successfully.

[Point 6] Step 4: gRPC fraud detection returns LOW risk.

[Point 7] OR Gateway: Since the risk is LOW, we auto-approve the claim.

[Point 8] The claim is APPROVED with $4,000 compensation.

This is the complete workflow using all 4 service types.
```

---

#### **Demo 2: HIGH Risk 케이스** (1-2분)

**Changyong 발표**:
```
Now, let me show you a different scenario.
I will change the claim amount to $25,000 to simulate a high-risk case.
```

**VSCode에서 수정** (화면 공유로 보여주기):
```java
// Line 50-55
ClaimRequest request = new ClaimRequest(
    "CLM-" + System.currentTimeMillis(),
    "John Doe",
    "CUST-12345",
    25000.0,  // ← 여기를 수정 (4000.0 → 25000.0)
    "ACCIDENT"
);
```

**재실행**:
```bash
mvn exec:java -Dexec.mainClass="com.insurance.orchestrator.InsuranceClaimOrchestrator"
```

**Changyong 발표**:
```
As you can see, the workflow detects HIGH risk.

The OR Gateway now routes this claim to MANUAL REVIEW
instead of auto-approval.

This demonstrates the flexibility of our workflow routing logic.
```

---

### **Phase 3: Code Explanation via PPT** (4-5분)

**Changyong 발표** (Thijmen이 PPT 조작):

#### **Slide 3: REST Service**
```
Now, let me explain the code.

[Slide 3 보여주기]

This is the REST service for claim submission.

I chose REST because:
1. It's a simple CRUD operation
2. JSON format is web and mobile friendly
3. Stateless protocol is perfect for independent submissions
4. REST is the industry standard for public APIs

Next slide, please.
```

---

#### **Slide 4: SOAP Service**
```
[Slide 4 보여주기]

This is the SOAP service for identity verification.

I chose SOAP because:
1. Enterprise-level security with WS-Security standards
2. Handling sensitive personal data requires strict protocols
3. Contract-first approach with WSDL ensures type safety
4. Legacy enterprise systems often use SOAP

Next slide, please.
```

---

#### **Slide 5: gRPC Service**
```
[Slide 5 보여주기]

This is the gRPC service for fraud detection.

I chose gRPC because:
1. Real-time analysis requires low latency
2. Binary protocol (Protobuf) is 5 to 10 times faster than JSON
3. High performance is critical for fraud detection
4. Bidirectional streaming supports continuous monitoring

Next slide, please.
```

---

#### **Slide 6: GraphQL Service**
```
[Slide 6 보여주기]

This is the GraphQL service for policy validation.

I chose GraphQL because:
1. Clients can request only the fields they need
2. This reduces over-fetching and saves bandwidth
3. Single endpoint serves all policy queries
4. Strongly typed schema prevents errors

Next slide, please.
```

---

#### **Slide 7: Gateway Logic**
```
[Slide 7 보여주기]

Finally, let me explain the gateway logic.

XOR Gateway: Exclusive decision.
If identity verification fails, the workflow stops immediately.
No need to check fraud or policy.

AND Gateway: Parallel processing.
Fraud detection and document review run in parallel
using Java CompletableFuture.
This improves performance by approximately 50%.

OR Gateway: Multi-condition routing.
If risk is HIGH and amount is over $10,000, we reject.
If risk is HIGH alone, we route to expert review.
Otherwise, we auto-approve.

This is the complete implementation.
```

---

### **Phase 4: BPMN Explanation** (2-3분, 선택사항)

**Thijmen 발표** (시간 있으면):
```
I would like to briefly explain the BPMN diagram.

[BPMN 다이어그램 보여주기]

The diagram shows 3 pools:
1. Insurance Company (our main pool)
2. External Partners (identity provider, fraud detection)
3. Customer

Inside the Insurance Company pool, we have 3 lanes:
1. Customer Service: Claim submission
2. Risk Assessment: Identity, fraud, policy validation
3. Claims Processing: Compensation, payment, notification

The workflow uses 3 types of gateways:
- XOR Gateway: Exclusive decision (identity verification)
- AND Gateway: Parallel execution (fraud + document review)
- OR Gateway: Conditional routing (risk level evaluation)

This completes our BPMN design.
```

---

### **Phase 5: Q&A** (1-2분)

**Changyong 발표**:
```
To summarize:

1. We successfully implemented 4 web service technologies.
2. Each technology is optimized for its specific service.
3. We demonstrated the actual workflow using a Java Application Client,
   as required by the professor.
4. My teammate Thijmen designed the BPMN diagram.

We are ready for your questions.

Thank you.
```

---

## 🎯 예상 질문 및 답변 (Expected Q&A)

### Q1: "Why did you choose Java for the Orchestrator?"
**Answer**:
```
I chose Java because:
1. All 4 service technologies (REST, SOAP, gRPC, GraphQL) have mature Java libraries
2. Java CompletableFuture provides excellent support for parallel processing (AND gateway)
3. Java is the standard language for enterprise applications
4. Maven simplifies dependency management
```

---

### Q2: "Did you use a BPMS like Bonita or Activiti?"
**Answer**:
```
No, Professor. We did not use a BPMS.

As you mentioned in class, the BPMN workflow accounts for only 30 points out of 250.
Instead, we focused on implementing all 4 services correctly (220 points).

We implemented the workflow logic directly in Java code,
which you said was acceptable: "the workflow can be just implemented as code in the client."

We created a BPMN diagram using draw.io for visualization,
but the actual execution is done by our Java Orchestrator.
```

---

### Q3: "Why didn't you use Postman for the demo?"
**Answer**:
```
Professor, you explicitly instructed us:
"Don't use Swagger or browser test... build the client, it should be an application client."

That's why we built a Java Application Client (InsuranceClaimOrchestrator.java)
to demonstrate the complete workflow.

This client calls all 4 services automatically,
which is more representative of a real production system.
```

---

### Q4: "Are these mock services or real implementations?"
**Answer**:
```
These are mock services, Professor.

As you mentioned in class: "It can be okay, just make it the simplest way, randomly decide."

For example:
- Identity verification: checks if customer ID is even (50% pass rate)
- Fraud detection: risk level based on claim amount ($0-5000 = LOW, $5000-20000 = MEDIUM, $20000+ = HIGH)
- Policy validation: checks if policy number starts with "POL-"

The focus is on demonstrating the service integration and workflow,
not on complex business logic.
```

---

### Q5: "How did you test the services?"
**Answer**:
```
We tested each service individually first:
1. REST: Using Postman (during development)
2. SOAP: Verified WSDL at http://localhost:8080/claim-processing/ws/identity?wsdl
3. gRPC: Ran the gRPC server on port 50051 and tested with the client
4. GraphQL: Executed queries at http://localhost:8080/claim-processing/graphql

Then we tested the complete workflow using the Java Orchestrator
with 3 scenarios:
- Normal approval (LOW risk)
- Manual review (HIGH risk)
- Rejection (identity failure)

All tests passed successfully.
```

---

### Q6: "What was the most challenging part?"
**Answer**:
```
The most challenging part was implementing the AND Gateway with parallel processing.

I used Java CompletableFuture to execute fraud detection and document review in parallel.
The challenge was ensuring both services complete before proceeding to the OR Gateway.

I solved this using the join() method:
- fraudFuture.join() waits for fraud detection
- docFuture.join() waits for document review

This ensures synchronization while maintaining parallel execution.
```

---

### Q7: "How do you justify the technology choices?"
**Answer**:
```
Each technology is chosen based on the service characteristics:

REST for Claim Submission:
- Simple CRUD operation
- JSON-friendly
- Stateless

SOAP for Identity Verification:
- Enterprise-level security (WS-Security)
- Sensitive personal data
- Contract-first approach

gRPC for Fraud Detection:
- Real-time analysis requires low latency
- Binary protocol is 5-10x faster
- High performance critical

GraphQL for Policy Validation:
- Flexible queries
- Clients request only needed fields
- Single endpoint for all queries

This is not arbitrary—each choice is optimized for the specific service requirements.
```

---

## 📝 발표 체크리스트 (Final Checklist)

### **발표 전날**
- [ ] PPT 슬라이드 9개 완성 (Thijmen)
- [ ] 모든 서버 테스트 (Tomcat + gRPC)
- [ ] InsuranceClaimOrchestrator.java 실행 테스트
- [ ] 발표 대본 리허설 (2회 이상)
- [ ] 예상 질문 답변 준비

### **발표 당일 (1시간 전)**
- [ ] 노트북 충전 100%
- [ ] 프로젝터 연결 테스트
- [ ] 터미널 3개 준비
- [ ] Tomcat 실행
- [ ] gRPC 서버 실행
- [ ] VSCode에서 InsuranceClaimOrchestrator.java 열기
- [ ] PPT 파일 열기

### **발표 직전 (10분 전)**
- [ ] 터미널 폰트 크기 확대 (교수님이 보기 쉽게)
- [ ] VSCode 폰트 크기 확대
- [ ] 브라우저 탭 정리 (불필요한 탭 닫기)
- [ ] 알림/메시지 끄기 (방해 금지 모드)
- [ ] 발표 대본 옆에 두기

### **발표 중**
- [ ] 자신감 있게 말하기
- [ ] 화면을 가리키며 설명
- [ ] 교수님과 눈 맞추기
- [ ] 천천히, 명확하게 발음
- [ ] 질문이 있으면 정중하게 답변

---

## 🎉 발표 성공 팁

1. **연습**: 최소 3번 이상 전체 발표 리허설
2. **시간 관리**: 10-12분 엄수 (타이머 사용)
3. **백업 플랜**: 서버 실행 실패 시 → 미리 녹화한 영상 준비
4. **자신감**: "We successfully implemented..." (긍정적 표현)
5. **명확성**: 기술 용어는 천천히, 약어는 풀어서 설명
6. **교수님 요구사항 강조**: "As you instructed, we used a Java Application Client"

---

**작성일**: 2026-01-21
**버전**: 1.0
**작성자**: Changyong Hyun with Claude Sonnet 4.5
