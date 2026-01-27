package com.insurance.client;

import com.insurance.grpc.FraudDetectionClient;
import com.insurance.grpc.proto.FraudResponse;
import com.insurance.grpc.proto.StatisticsResponse;

/**
 * gRPC Client test for Fraud Detection Service
 */
public class GrpcClient {

    public static void main(String[] args) throws Exception {
        System.out.println("=== Testing gRPC Fraud Detection Service ===\n");

        FraudDetectionClient client = new FraudDetectionClient("localhost", 50051);

        try {
            // Test Case 1: Low risk claim
            System.out.println("Test Case 1: Low Risk Claim");
            System.out.println("-".repeat(60));
            testFraudDetection(client, "CLM-001", "USR-123", 5000.0, "AUTO", "FIRST_TIME_CLAIM");

            System.out.println("\n");

            // Test Case 2: Medium risk claim
            System.out.println("Test Case 2: Medium Risk Claim");
            System.out.println("-".repeat(60));
            testFraudDetection(client, "CLM-002", "USR-456", 75000.0, "ACCIDENT", "FIRST_TIME_CLAIM");

            System.out.println("\n");

            // Test Case 3: High risk claim
            System.out.println("Test Case 3: High Risk Claim");
            System.out.println("-".repeat(60));
            testFraudDetection(client, "CLM-003", "USR-789", 150000.0, "ACCIDENT", "MULTIPLE_CLAIMS");

            System.out.println("\n");

            // Test Case 4: Get statistics
            System.out.println("Test Case 4: Fraud Statistics");
            System.out.println("-".repeat(60));
            StatisticsResponse stats = client.getStatistics("LAST_30_DAYS");
            if (stats != null) {
                System.out.println("Total Claims Analyzed: " + stats.getTotalClaimsAnalyzed());
                System.out.println("Fraud Detected: " + stats.getFraudDetected());
                System.out.println("Fraud Rate: " + String.format("%.2f%%", stats.getFraudRate() * 100));
                System.out.println("Total Amount Saved: $" + stats.getTotalAmountSaved());
            }

        } finally {
            client.shutdown();
        }
    }

    private static void testFraudDetection(FraudDetectionClient client, String claimId,
                                          String userId, double amount, String type,
                                          String history) {
        FraudResponse response = client.analyzeClaim(
                claimId, userId, amount, type, "2024-01-15", history
        );

        if (response != null) {
            System.out.println("Claim ID: " + response.getClaimId());
            System.out.println("Amount: $" + amount);
            System.out.println("Is Fraudulent: " + (response.getIsFraudulent() ? "⚠ YES" : "✓ NO"));
            System.out.println("Risk Score: " + String.format("%.2f", response.getRiskScore()));
            System.out.println("Risk Level: " + response.getRiskLevel());
            System.out.println("Recommendation: " + response.getRecommendation());
            System.out.println("Explanation: " + response.getExplanation());

            if (response.getRedFlagsCount() > 0) {
                System.out.println("Red Flags:");
                for (String flag : response.getRedFlagsList()) {
                    System.out.println("  - " + flag);
                }
            }
        }
    }
}
