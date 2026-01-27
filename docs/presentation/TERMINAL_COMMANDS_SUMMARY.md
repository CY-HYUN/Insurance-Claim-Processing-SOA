# 🎬 발표용 터미널 명령어 요약

**Live Demo 순서대로 실행할 명령어 목록**

---

## 📋 발표 10분 전 준비

### **Terminal 1: Tomcat 시작**
```powershell
cd "d:\Study\Github\TSP\Service Oriented Computing\Project\Insurance-Claim-Processing-SOA"
.\start-tomcat.bat
```

**예상 출력:**
```
Using JAVA_HOME: C:\Program Files\Microsoft\jdk-11.0.16.101-hotspot
Server startup in [xxxx] milliseconds
```

---

### **Terminal 2: gRPC 서버 시작**
```powershell
cd "d:\Study\Github\TSP\Service Oriented Computing\Project\Insurance-Claim-Processing-SOA"
.\start-grpc-java.bat
```

**예상 출력:**
```
gRPC Fraud Detection Server started
Listening on port: 50051
```

---

### **Terminal 3: 대기**
```powershell
cd "d:\Study\Github\TSP\Service Oriented Computing\Project\Insurance-Claim-Processing-SOA"
# 명령어 입력 대기 (발표 시작까지 대기)
```

---

## 🎯 Live Demo 시작 (10분)

### **[0:30-6:00] Main Demo - All Services Test**

#### **Terminal 3에서 실행:**
```powershell
.\run-demo-java.bat
```

**메뉴가 나타나면:**
```
5
```
(Enter 키 입력 - Test 5: Run All Tests 선택)

---

#### **출력을 보며 설명 (Enter 키로 진행):**

1. **Test 1: SOAP** → 출력 확인 → `Enter` 키
2. **Test 2: gRPC** → 출력 확인 → `Enter` 키
3. **Test 3: GraphQL** → 출력 확인 → `Enter` 키
4. **Test 4: REST (Complete Workflow)** → 출력 확인 → `Enter` 키

---

### **[6:00-8:30] Rejection Case Demo**

#### **1. VSCode에서 코드 수정 (30초)**

**파일:** `src\main\java\com\insurance\client\RestClient.java`

**Line 102 찾기:**
```java
5000.0,  // amount
```

**수정:**
```java
500000.0,  // amount - HIGH RISK for rejection demo
```

**저장:** `Ctrl + S`

---

#### **2. Terminal 3에서 재컴파일 (30초)**
```powershell
.\recompile-restclient.bat
```

**예상 출력:**
```
Compilation successful!
You can now run: .\run-demo-java.bat
```

---

#### **3. Terminal 3에서 Rejection Demo 실행 (1분)**
```powershell
.\run-demo-java.bat
```

**메뉴가 나타나면:**
```
4
```
(Enter 키 입력 - Test 4: REST Client만 실행)

**출력 확인:**
- Amount: $500,000.0
- Step 1: Identity ✓ PASSED
- Step 2: Fraud ✗ HIGH RISK
- Status: ❌ REJECTED

---

## 📊 명령어 타임라인 요약

| 시간 | 터미널 | 명령어 | 비고 |
|------|--------|--------|------|
| **발표 10분 전** | Terminal 1 | `.\start-tomcat.bat` | Tomcat 시작 |
| **발표 10분 전** | Terminal 2 | `.\start-grpc-java.bat` | gRPC 서버 시작 |
| **발표 10분 전** | Terminal 3 | `cd ...` | 프로젝트 폴더로 이동 |
| **0:30** | Terminal 3 | `.\run-demo-java.bat` → `5` | All Tests 실행 |
| **0:30-6:00** | Terminal 3 | `Enter` 키 4번 | Test 1, 2, 3, 4 진행 |
| **6:00** | VSCode | Line 102 수정 | 5000.0 → 500000.0 |
| **6:30** | Terminal 3 | `.\recompile-restclient.bat` | 재컴파일 |
| **7:00** | Terminal 3 | `.\run-demo-java.bat` → `4` | Rejection Demo |

---

## 🔧 트러블슈팅 명령어

### **서버 재시작이 필요한 경우:**

#### **Tomcat 재시작:**
```powershell
# Terminal 1
.\stop-tomcat.bat
.\start-tomcat.bat
```

#### **gRPC 재시작:**
```powershell
# Terminal 2
Ctrl + C  (서버 중지)
.\start-grpc-java.bat
```

---

### **Demo 실행 안 될 때:**
```powershell
# Terminal 3
.\recompile-restclient.bat
.\run-demo-java.bat
```

---

## ✅ 핵심 명령어 3개 (암기 필수!)

1. **`.\run-demo-java.bat`** → 메인 데모 실행
2. **`5`** → Run All Tests (Approval Case)
3. **`4`** → Run REST Only (Rejection Case)

---

## 🎯 발표 시 주의사항

### **Enter 키 타이밍:**
- 각 Test 출력을 **천천히 설명**한 후 Enter
- 교수님이 출력을 읽을 시간 주기
- 너무 빨리 넘어가지 않기

### **화면 전환:**
1. Terminal 1, 2 (서버 확인) - 30초
2. Terminal 3 (Demo 실행) - 5.5분
3. VSCode (코드 수정) - 30초
4. Terminal 3 (재컴파일) - 30초
5. Terminal 3 (Rejection Demo) - 1분
6. Summary - 1.5분

---

**작성일**: 2026-01-27
**용도**: Live Demo 발표용 명령어 요약
**작성자**: Changyong Hyun with Claude Sonnet 4.5
