package com.insurance.graphql;

import graphql.schema.DataFetcher;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

/**
 * GraphQL Data Fetchers for Policy operations
 */
public class PolicyDataFetcher {

    // Mock database
    private static Map<String, Policy> policyDatabase = new HashMap<>();

    static {
        // Initialize with sample policies
        policyDatabase.put("POL-001", new Policy(
                "POL-001", "USR-123", "AUTO", "ACTIVE",
                50000, 1200, "2024-01-01", "2025-01-01", true
        ));
        policyDatabase.put("POL-002", new Policy(
                "POL-002", "USR-456", "HOME", "ACTIVE",
                200000, 2400, "2024-01-01", "2025-01-01", true
        ));
        policyDatabase.put("POL-003", new Policy(
                "POL-003", "USR-123", "HEALTH", "EXPIRED",
                100000, 3600, "2023-01-01", "2024-01-01", false
        ));
    }

    /**
     * Fetch policy by ID
     */
    public static DataFetcher<Policy> getPolicyById() {
        return environment -> {
            String policyId = environment.getArgument("policyId");
            System.out.println("\n=== GraphQL: Fetching Policy ===");
            System.out.println("Policy ID: " + policyId);

            Policy policy = policyDatabase.get(policyId);
            if (policy != null) {
                System.out.println("Policy found: " + policy.getPolicyType());
            } else {
                System.out.println("Policy not found");
            }
            return policy;
        };
    }

    /**
     * Fetch policies by user ID
     */
    public static DataFetcher<List<Policy>> getPoliciesByUserId() {
        return environment -> {
            String userId = environment.getArgument("userId");
            System.out.println("\n=== GraphQL: Fetching Policies by User ===");
            System.out.println("User ID: " + userId);

            List<Policy> userPolicies = new ArrayList<>();
            for (Policy policy : policyDatabase.values()) {
                if (policy.getUserId().equals(userId)) {
                    userPolicies.add(policy);
                }
            }

            System.out.println("Found " + userPolicies.size() + " policies");
            return userPolicies;
        };
    }

    /**
     * Validate policy for claim
     */
    public static DataFetcher<ValidationResult> validatePolicy() {
        return environment -> {
            String policyId = environment.getArgument("policyId");
            Double claimAmount = environment.getArgument("claimAmount");

            System.out.println("\n=== GraphQL: Validating Policy ===");
            System.out.println("Policy ID: " + policyId);
            System.out.println("Claim Amount: $" + claimAmount);

            ValidationResult result = new ValidationResult();
            result.setPolicyId(policyId);

            Policy policy = policyDatabase.get(policyId);

            if (policy == null) {
                result.setValid(false);
                result.setStatus("INVALID");
                result.setMessage("Policy not found");
                result.addValidationError("Policy ID does not exist");
                return result;
            }

            // Validate policy status
            if (!policy.isActive()) {
                result.setValid(false);
                result.setStatus("INACTIVE");
                result.setMessage("Policy is not active");
                result.addValidationError("Policy is expired or inactive");
                result.setCoverageLimit(policy.getCoverageAmount());
                return result;
            }

            // Validate claim amount against coverage
            if (claimAmount > policy.getCoverageAmount()) {
                result.setValid(false);
                result.setStatus("EXCEEDS_COVERAGE");
                result.setMessage("Claim amount exceeds policy coverage");
                result.addValidationError("Claim amount ($" + claimAmount +
                        ") exceeds coverage limit ($" + policy.getCoverageAmount() + ")");
                result.setCoverageLimit(policy.getCoverageAmount());
                return result;
            }

            // Policy is valid
            result.setValid(true);
            result.setStatus("VALID");
            result.setMessage("Policy is valid for claim");
            result.setCoverageLimit(policy.getCoverageAmount());

            System.out.println("Validation Result: " + result.getStatus());
            return result;
        };
    }

    /**
     * Get all policies
     */
    public static DataFetcher<List<Policy>> getAllPolicies() {
        return environment -> {
            System.out.println("\n=== GraphQL: Fetching All Policies ===");
            System.out.println("Total policies: " + policyDatabase.size());
            return new ArrayList<>(policyDatabase.values());
        };
    }
}
