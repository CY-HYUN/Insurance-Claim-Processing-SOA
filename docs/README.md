# Documentation

This folder contains technical documentation for the Insurance Claim Processing SOA project.

## 📁 Documentation Structure

```
docs/
├── technical-docs/              # Architecture and deployment guides
│   ├── Architecture_Overview.md
│   ├── Deployment_Guide.md
│   ├── Service_Endpoints.md
│   ├── Testing_Guide.md
│   └── Project_Implementation_Plan.md
├── API_Documentation/           # API testing resources
│   └── Insurance_Claim_Processing.postman_collection.json
└── presentation/                # Demo resources
    └── LIVE_DEMO_GUIDE.md
```

## 📚 Document Overview

### Technical Documentation

**[Architecture_Overview.md](technical-docs/Architecture_Overview.md)**
- System architecture and design patterns
- Multi-protocol service integration
- Workflow orchestration details

**[Deployment_Guide.md](technical-docs/Deployment_Guide.md)**
- Step-by-step deployment instructions
- Tomcat and gRPC server configuration
- Maven build process

**[Service_Endpoints.md](technical-docs/Service_Endpoints.md)**
- Complete API reference for all services
- REST, SOAP, gRPC, GraphQL endpoint documentation
- Request/response examples

**[Testing_Guide.md](technical-docs/Testing_Guide.md)**
- Test case documentation
- Service testing procedures
- Client execution instructions

**[Project_Implementation_Plan.md](technical-docs/Project_Implementation_Plan.md)**
- Development timeline
- Implementation phases
- Technology decisions

### API Documentation

**[Insurance_Claim_Processing.postman_collection.json](API_Documentation/Insurance_Claim_Processing.postman_collection.json)**
- Postman collection for REST and GraphQL testing
- 12+ test requests with examples
- Pre-configured environment variables

### Presentation Materials

**[LIVE_DEMO_GUIDE.md](presentation/LIVE_DEMO_GUIDE.md)**
- Live demonstration script
- Terminal commands for demo
- Test scenario walkthroughs

## 🔗 Quick Links

**Main Project README:** [../README.md](../README.md)

**Service Implementations:**
- REST: `src/main/java/com/insurance/service/`
- SOAP: `src/main/java/com/insurance/soap/`
- gRPC: `src/main/java/com/insurance/grpc/`
- GraphQL: `src/main/java/com/insurance/graphql/`

**API Schemas:**
- GraphQL: `src/main/resources/schema.graphql`
- gRPC: `src/main/proto/fraud_detection.proto`
- WSDL: `http://localhost:8080/claim-processing/services/IdentityVerification?wsdl`

## 📖 Getting Started

1. Read the [main README](../README.md) for project overview
2. Follow [Deployment_Guide.md](technical-docs/Deployment_Guide.md) to set up the project
3. Reference [Service_Endpoints.md](technical-docs/Service_Endpoints.md) for API details
4. Use [Testing_Guide.md](technical-docs/Testing_Guide.md) to test services
5. Check [LIVE_DEMO_GUIDE.md](presentation/LIVE_DEMO_GUIDE.md) for demonstration

---

*For questions or issues, refer to the main project README or check the technical documentation.*
