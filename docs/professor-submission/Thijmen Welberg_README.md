# Insurance Claim Processing – Workflow (Flowable BPM)

## Author
**Workflow Design & Implementation**  
Name: Thijmen Welberg  
Course: Service-Oriented Computing  
Institution: Télécom SudParis  

---

## 1. Overview

This part of the project focuses on the **workflow orchestration** of the Insurance Claim Processing System.  
The workflow is modeled using **Flowable BPM** and represents the coordination between customers, automated system checks, and human expert decisions.

The workflow complements the service-oriented architecture implemented separately (REST, SOAP, gRPC, GraphQL).

---

## 2. Why Flowable BPM?

Flowable BPM was chosen for the following reasons:

- **Native BPMN 2.0 support**
  - Clear modeling of XOR, AND, and OR gateways
  - Explicit pools and lanes for partner interaction
- **Strong human task support**
  - Expert review and manual decisions are easy to model
- **Integrated UI Designer**
  - Forms for customer submission and expert assessment
- **Educational clarity**
  - Visual workflow is easy to explain during presentation
  - Errors and gateway conditions are explicitly validated


---

## 3. BPMN Structure

### 3.1 Pools

The workflow uses **two pools**, representing the customer and the Insurance Company:

#### Pool 1: Customer
- Responsibilities:
  - Submit insurance claim
  - Wait for decision
  - View final result

#### Pool 2: Insurance Company
- Responsibilities:
  - Identity verification
  - Fraud detection
  - Risk calculation
  - Expert assessment
  - Final decision

---

## 4. Workflow Steps

### Step 1: Claim Submission (Customer Pool)

- Task: **Submit Claim** (Human Task)
- Inputs:
  - Customer ID
  - Customer Name
  - Claim Amount
- A message is sent to the Insurance Company pool.

---

### Step 2: Identity Verification (Insurance Company Pool)

- Task: **Verify Identity** (Service Task)

#### XOR Gateway – *Identity valid?*

- If **false** → Reject claim immediately
- If **true** → Continue workflow

**Reason for XOR**:  
If identity verification fails, the process must stop. So therefore only one path is possible. 

---

### Step 3: Parallel Processing (Insurance Company Pool )

#### AND Gateway (Split)

Two tasks run in parallel:
- **Fraud Detection** 
- **Policy Validation** 

#### AND Gateway (Join)

The workflow continues only after **both tasks are completed**.

**Reason for AND**:  
The tasks are independent and can be executed simultaneously.


---

### Step 5: Risk-Based Routing (OR Gateway)

#### OR Gateway – Inclusive Decision

- LOW risk → Auto Accept
- MEDIUM risk → Accept with review
- HIGH risk → Expert Assessment required

**Reason for OR**:  
Multiple business rules may apply; routing is based on conditions rather than exclusivity.

---

### Step 6: Expert Assessment (Insurance Company Pool)

- Task: **Expert Assessment** (Human Task)
- Used only for HIGH-risk claims
- Expert decides to accept or reject the claim

---

### Step 7: Final Decision & Notification

- Task: **Accept Claim** or **Reject Claim**
- A response message is sent back to the customer
- Customer views the final result
- Workflow ends

---

## 5. Challenges & Limitations in Service Integration 

While the BPMN workflow was successfully designed and deployed in Flowable BPM,
we encountered challenges when invoking external services directly from the
workflow at runtime.

### 5.1 Steps taken

1. Implemented Java Service Tasks (JavaDelegate) for:
  - REST-based claim submission
  - SOAP-based identity verification
  - GraphQL-based policy validation
2. Added the org.flowable-0.0.1-SNAPSHOT.jar file to TOMCAT
3. Added the Java classes to the specific tasks

### 5.2 Problems

- HTTP 404 errors when services were not reachable from Flowable
- Exception during command execution when saving the app
- We were unable to find a reliable way to run the gRPC server alongside Flowable  


## 6. Conclusion

This workflow:
- Demonstrates correct BPMN modeling
- Uses pools, lanes, and message flows
- Implements XOR, AND, and OR gateway logic
- Clearly separates automated and human decision-making

While full runtime service invocation was not achieved, the workflow design accurately represents real-world 
insurance claim orchestration and demonstrates proper BPMN usage within a service-oriented architecture.

