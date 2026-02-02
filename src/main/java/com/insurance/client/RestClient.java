package com.insurance.client;

import com.google.gson.Gson;
import com.insurance.dto.ClaimRequest;
import com.insurance.dto.ClaimResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * REST Client for testing Claim Submission Service
 */
public class RestClient {

    private static final String BASE_URL = "http://localhost:8080/claim-processing/api";
    private static final Gson gson = new Gson();

    /**
     * Submit a claim via REST API
     */
    public static ClaimResponse submitClaim(ClaimRequest request) throws Exception {
        URL url = new URL(BASE_URL + "/claims/submit");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        try {
            // Set request method and headers
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Send request body
            String jsonRequest = gson.toJson(request);
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonRequest.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Read response
            int responseCode = conn.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"));
            }

            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            return gson.fromJson(response.toString(), ClaimResponse.class);

        } finally {
            conn.disconnect();
        }
    }

    /**
     * Get claim status via REST API
     */
    public static ClaimResponse getClaimStatus(String claimId) throws Exception {
        URL url = new URL(BASE_URL + "/claims/" + claimId);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        try {
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"));

            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            return gson.fromJson(response.toString(), ClaimResponse.class);

        } finally {
            conn.disconnect();
        }
    }

    /**
     * Test the REST service
     */
    public static void main(String[] args) {
        try {
            System.out.println("=== Testing REST Service ===\n");

            // Create test claim request
            ClaimRequest request = new ClaimRequest(
                    "CLM-REST-001",
                    "USR-123",
                    "AUTO",
                    500000.0,
                    "Car accident on highway",
                    "2024-01-15"
            );

            System.out.println("Submitting claim: " + request.getClaimId());
            System.out.println("Amount: $" + request.getClaimAmount());

            // Submit claim
            ClaimResponse response = submitClaim(request);

            // Display response
            System.out.println("\n=== Response ===");
            System.out.println("Status: " + response.getStatus());
            System.out.println("Message: " + response.getMessage());
            System.out.println("Identity Verified: " + response.isIdentityVerified());
            System.out.println("Fraud Check Passed: " + response.isFraudCheckPassed());
            System.out.println("Policy Status: " + response.getPolicyStatus());
            System.out.println("Timestamp: " + response.getTimestamp());

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
