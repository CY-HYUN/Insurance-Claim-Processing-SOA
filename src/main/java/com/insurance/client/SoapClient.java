package com.insurance.client;

import com.insurance.soap.IdentityVerificationService;
import com.insurance.soap.VerificationResult;

/**
 * SOAP Client for testing Identity Verification Service
 * Note: This is a direct Java client. For external SOAP testing, use SoapUI or similar tools.
 */
public class SoapClient {

    public static void main(String[] args) {
        System.out.println("=== Testing SOAP Identity Verification Service ===\n");

        try {
            // Create SOAP service instance
            IdentityVerificationService service = new IdentityVerificationService();

            // Test Case 1: Valid identity
            System.out.println("Test Case 1: Valid Identity");
            System.out.println("-".repeat(50));
            VerificationResult result1 = service.verifyIdentity(
                    "USR-123",
                    "John Doe",
                    "ID12345678"
            );
            printResult(result1);

            System.out.println("\n");

            // Test Case 2: Invalid document ID (too short)
            System.out.println("Test Case 2: Invalid Document ID");
            System.out.println("-".repeat(50));
            VerificationResult result2 = service.verifyIdentity(
                    "USR-456",
                    "Jane Smith",
                    "ID123" // Too short
            );
            printResult(result2);

            System.out.println("\n");

            // Test Case 3: Missing information
            System.out.println("Test Case 3: Missing Information");
            System.out.println("-".repeat(50));
            VerificationResult result3 = service.verifyIdentity(
                    "USR-789",
                    "",
                    ""
            );
            printResult(result3);

            System.out.println("\n");

            // Test service status
            System.out.println("Service Status: " + service.getServiceStatus());

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void printResult(VerificationResult result) {
        System.out.println("User ID: " + result.getUserId());
        System.out.println("Verified: " + (result.isVerified() ? "✓ YES" : "✗ NO"));
        System.out.println("Confidence Score: " + (result.getConfidenceScore() * 100) + "%");
        System.out.println("Message: " + result.getMessage());
        System.out.println("Method: " + result.getVerificationMethod());
    }
}
