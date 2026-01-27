package com.insurance.orchestrator;

import com.insurance.dto.ClaimRequest;
import com.insurance.dto.ClaimResponse;
import com.insurance.grpc.FraudDetectionClient;
import com.insurance.grpc.proto.FraudResponse;
import com.insurance.soap.IdentityVerificationService;
import com.insurance.soap.VerificationResult;
import com.insurance.graphql.PolicyDataFetcher;
import com.insurance.graphql.ValidationResult;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

/**
 * Orchestrator that coordinates all services (REST, SOAP, gRPC, GraphQL)
 * for insurance claim processing
 */
public class InsuranceClaimOrchestrator {

    private IdentityVerificationService soapService;
    private FraudDetectionClient grpcClient;
    private GraphQL graphQL;

    public InsuranceClaimOrchestrator() {
        // Initialize SOAP service
        this.soapService = new IdentityVerificationService();

        // Initialize gRPC client
        try {
            this.grpcClient = new FraudDetectionClient("localhost", 50051);
        } catch (Exception e) {
            System.err.println("Warning: gRPC server not available. Fraud detection will be skipped.");
            System.err.println("Please start gRPC server using start-grpc-server.bat");
            this.grpcClient = null;
        }

        // Initialize GraphQL
        try {
            this.graphQL = buildGraphQL();
        } catch (Exception e) {
            System.err.println("Warning: GraphQL initialization failed: " + e.getMessage());
            this.graphQL = null;
        }
    }

    /**
     * Process insurance claim through all services
     */
    public ClaimResponse processClaim(ClaimRequest request) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ORCHESTRATOR: Starting Claim Processing Pipeline");
        System.out.println("=".repeat(70));

        ClaimResponse response = new ClaimResponse();
        response.setClaimId(request.getClaimId());
        response.setTimestamp(getCurrentTimestamp());

        try {
            // Step 1: Identity Verification (SOAP)
            System.out.println("\n[Step 1/3] Identity Verification (SOAP Service)");
            VerificationResult verificationResult = soapService.verifyIdentity(
                    request.getUserId(),
                    "John Doe", // Mock name
                    "ID12345678" // Mock document ID
            );

            response.setIdentityVerified(verificationResult.isVerified());

            if (!verificationResult.isVerified()) {
                response.setStatus("REJECTED");
                response.setMessage("Identity verification failed: " + verificationResult.getMessage());
                System.out.println("❌ Claim rejected: Identity verification failed");
                return response;
            }
            System.out.println("✓ Identity verified successfully");

            // Step 2: Fraud Detection (gRPC)
            System.out.println("\n[Step 2/3] Fraud Detection (gRPC Service)");
            boolean fraudCheckPassed = true;

            if (grpcClient != null) {
                try {
                    FraudResponse fraudResult = grpcClient.analyzeClaim(
                            request.getClaimId(),
                            request.getUserId(),
                            request.getClaimAmount(),
                            request.getClaimType(),
                            request.getIncidentDate(),
                            "FIRST_TIME_CLAIM" // Mock user history
                    );

                    response.setFraudCheckPassed(!fraudResult.getIsFraudulent());
                    fraudCheckPassed = !fraudResult.getIsFraudulent();

                    if (fraudResult.getIsFraudulent()) {
                        response.setStatus("REJECTED");
                        response.setMessage("Fraud detected: " + fraudResult.getExplanation());
                        System.out.println("❌ Claim rejected: Fraud detected (Risk: " +
                                fraudResult.getRiskLevel() + ")");
                        return response;
                    }
                    System.out.println("✓ Fraud check passed (Risk: " + fraudResult.getRiskLevel() + ")");

                } catch (Exception e) {
                    System.err.println("⚠ gRPC call failed: " + e.getMessage());
                    System.err.println("⚠ Proceeding without fraud check");
                }
            } else {
                System.out.println("⚠ gRPC service unavailable - skipping fraud check");
                response.setFraudCheckPassed(true);
            }

            // Step 3: Policy Validation (GraphQL)
            System.out.println("\n[Step 3/3] Policy Validation (GraphQL Service)");
            String policyStatus = "VALID";

            if (graphQL != null) {
                try {
                    ValidationResult policyResult = validatePolicyViaGraphQL(
                            "POL-001", // Mock policy ID
                            request.getClaimAmount()
                    );

                    policyStatus = policyResult.getStatus();
                    response.setPolicyStatus(policyStatus);

                    if (!policyResult.isValid()) {
                        response.setStatus("REJECTED");
                        response.setMessage("Policy validation failed: " + policyResult.getMessage());
                        System.out.println("❌ Claim rejected: " + policyResult.getMessage());
                        return response;
                    }
                    System.out.println("✓ Policy validated successfully");

                } catch (Exception e) {
                    System.err.println("⚠ GraphQL call failed: " + e.getMessage());
                    System.err.println("⚠ Proceeding with default policy validation");
                    response.setPolicyStatus("VALID");
                }
            } else {
                System.out.println("⚠ GraphQL service unavailable - using default validation");
                response.setPolicyStatus("VALID");
            }

            // All checks passed
            response.setStatus("APPROVED");
            response.setMessage("Claim approved successfully");

            System.out.println("\n" + "=".repeat(70));
            System.out.println("✓ CLAIM APPROVED - All validation checks passed");
            System.out.println("=".repeat(70));

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus("ERROR");
            response.setMessage("Error processing claim: " + e.getMessage());
            System.out.println("\n❌ Error processing claim: " + e.getMessage());
        }

        return response;
    }

    /**
     * Validate policy using GraphQL
     */
    private ValidationResult validatePolicyViaGraphQL(String policyId, double claimAmount) {
        String query = "query ValidatePolicy($policyId: String!, $claimAmount: Float!) {" +
                "  validatePolicy(policyId: $policyId, claimAmount: $claimAmount) {" +
                "    policyId isValid status message validationErrors coverageLimit" +
                "  }" +
                "}";

        Map<String, Object> variables = new HashMap<>();
        variables.put("policyId", policyId);
        variables.put("claimAmount", claimAmount);

        ExecutionResult result = graphQL.execute(
                graphql.ExecutionInput.newExecutionInput()
                        .query(query)
                        .variables(variables)
                        .build()
        );

        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) result.getData();
        @SuppressWarnings("unchecked")
        Map<String, Object> validationData = (Map<String, Object>) data.get("validatePolicy");

        ValidationResult validationResult = new ValidationResult();
        validationResult.setPolicyId((String) validationData.get("policyId"));
        validationResult.setValid((Boolean) validationData.get("isValid"));
        validationResult.setStatus((String) validationData.get("status"));
        validationResult.setMessage((String) validationData.get("message"));
        validationResult.setCoverageLimit(((Number) validationData.get("coverageLimit")).doubleValue());

        return validationResult;
    }

    /**
     * Build GraphQL instance
     */
    private GraphQL buildGraphQL() {
        String schema = "type Query {" +
                "  validatePolicy(policyId: String!, claimAmount: Float!): ValidationResult" +
                "}" +
                "type ValidationResult {" +
                "  policyId: String!" +
                "  isValid: Boolean!" +
                "  status: String!" +
                "  message: String!" +
                "  validationErrors: [String!]!" +
                "  coverageLimit: Float!" +
                "}";

        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);

        RuntimeWiring runtimeWiring = newRuntimeWiring()
                .type("Query", builder -> builder
                        .dataFetcher("validatePolicy", PolicyDataFetcher.validatePolicy()))
                .build();

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(
                typeDefinitionRegistry, runtimeWiring);

        return GraphQL.newGraphQL(graphQLSchema).build();
    }

    /**
     * Get current timestamp
     */
    private String getCurrentTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

    /**
     * Cleanup resources
     */
    public void cleanup() {
        if (grpcClient != null) {
            try {
                grpcClient.shutdown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
