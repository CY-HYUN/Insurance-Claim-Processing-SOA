# 📚 Documentation Guide

This folder contains all project documentation organized by category.

---

## 📂 Folder Structure

### 1. **professor-submission/**
**For Professor - Required Submission Documents**

| File | Description | Purpose |
|------|-------------|---------|
| `readme.txt` | Execution instructions | How to build, deploy, and run the project |
| `MY_README_Changyong.md` | Personal implementation notes | Changyong's detailed implementation documentation |

**Usage:** These are the primary documents for professor review and grading.

---

### 2. **presentation/**
**For Live Demonstration (20 minutes)**

| File | Description | Duration |
|------|-------------|----------|
| `PRESENTATION_GUIDE.md` | Complete presentation strategy | Full 20 min (PPT 10min + Demo 10min) |
| `LIVE_DEMO_GUIDE.md` | Live demo script with terminal commands | Demo 10 min |
| `TERMINAL_COMMANDS_SUMMARY.md` | Quick command reference | Reference only |

**Usage:** Follow these guides during the online presentation at IMT Web Conference.

**Presentation Flow:**
1. **Part 1 (10 min):** Thijmen presents PPT (Architecture, BPMN, Gateway Logic)
2. **Part 2 (10 min):** Changyong demonstrates live services (SOAP, gRPC, GraphQL, REST)

---

### 3. **technical-docs/**
**Technical Documentation for Developers**

| File | Description | Audience |
|------|-------------|----------|
| `Architecture_Overview.md` | System architecture and design | Developers, Architects |
| `Deployment_Guide.md` | Step-by-step deployment instructions | DevOps, System Admins |
| `Service_Endpoints.md` | Complete API endpoint reference | API Consumers |
| `Testing_Guide.md` | Testing procedures and test cases | QA, Developers |
| `Project_Implementation_Plan.md` | Implementation details and timeline | Project Managers |

**Usage:** Reference material for understanding, deploying, and maintaining the system.

---

### 4. **requirements/**
**Professor's Course Requirements**

| File | Description | Source |
|------|-------------|--------|
| `guideline.txt` | Course project requirements (250 points) | Professor's Moodle |
| `guideline2.txt` | Demonstration method and Q&A | Professor's lecture transcript |

**Usage:** These define the project scope and evaluation criteria.

**Key Requirements:**
- ✅ REST resource (30 points)
- ✅ SOAP service (30 points)
- ✅ gRPC API (20 points)
- ✅ GraphQL API (20 points)
- ✅ Application Client (40 points) - **NOT Swagger/Postman**
- ✅ Complete execution (40 points)
- ✅ API test & documentation (30 points)
- ✅ Gateway logic (XOR) (15 points)

---

### 5. **API_Documentation/**
**API Testing Collections**

| File | Description | Usage |
|------|-------------|-------|
| `Insurance_Claim_Processing.postman_collection.json` | Postman test collection | Import into Postman for API testing |

**Contains:**
- REST endpoints (POST, GET)
- GraphQL queries (policy validation, user policies)
- Test cases for approval and rejection scenarios

---

### 6. **archive/**
**Historical Records**

| File | Description | Date |
|------|-------------|------|
| `PROJECT_CLEANUP_SUMMARY.md` | Project cleanup and reorganization summary | 2026-01-21 |

**Purpose:** Historical documentation of project evolution and cleanup activities.

---

## 🎯 Quick Start for Different Users

### **For Professor (Grading):**
1. Start with: `professor-submission/readme.txt` - Execution instructions
2. Review: `professor-submission/MY_README_Changyong.md` - Implementation details
3. Check: `requirements/guideline.txt` - Verify all requirements met

### **For Presentation (Tomorrow Morning):**
1. Open: `presentation/LIVE_DEMO_GUIDE.md` - Complete demo script
2. Reference: `presentation/TERMINAL_COMMANDS_SUMMARY.md` - Quick commands
3. Review: `presentation/PRESENTATION_GUIDE.md` - 20-minute presentation plan

### **For Developers (Future Maintenance):**
1. Understand: `technical-docs/Architecture_Overview.md` - System design
2. Deploy: `technical-docs/Deployment_Guide.md` - Setup instructions
3. Test: `technical-docs/Testing_Guide.md` - Test procedures
4. API Reference: `technical-docs/Service_Endpoints.md` - Endpoint documentation

---

## 📊 Documentation Statistics

| Category | Files | Total Size | Purpose |
|----------|-------|------------|---------|
| Professor Submission | 2 | ~45 KB | Grading materials |
| Presentation | 3 | ~38 KB | Live demo scripts |
| Technical Docs | 5 | ~132 KB | Developer reference |
| Requirements | 2 | ~7 KB | Course requirements |
| API Documentation | 1 | ~15 KB | API testing |
| Archive | 1 | ~9 KB | Historical records |
| **Total** | **14 files** | **~246 KB** | Complete documentation |

---

## 🔗 Related Files Outside docs/

- **Root README.md** - Project overview and quick start
- **pom.xml** - Maven build configuration
- **src/** - Source code (23 Java files)
- **16 .bat files** - Automation scripts for Windows

---

## 📅 Last Updated

**Date:** 2026-01-27
**Status:** Ready for GitHub submission and professor review
**Presentation:** Tomorrow morning (Online Web Conference)

---

**Contact:** Changyong Hyun
**Project:** Insurance Claim Processing - SOA
**Course:** Service Oriented Computing
**Institution:** Télécom SudParis
