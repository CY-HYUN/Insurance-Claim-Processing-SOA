package com.insurance.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * GraphQL Client for testing Policy Validation Service
 */
public class GraphQLClient {

    private static final String GRAPHQL_URL = "http://localhost:8080/claim-processing/graphql";
    private static final Gson gson = new Gson();

    /**
     * Execute GraphQL query
     */
    public static String executeQuery(String query, Map<String, Object> variables) throws Exception {
        URL url = new URL(GRAPHQL_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        try {
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Build request
            Map<String, Object> request = new HashMap<>();
            request.put("query", query);
            if (variables != null) {
                request.put("variables", variables);
            }

            // Send request
            String jsonRequest = gson.toJson(request);
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonRequest.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Read response
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"));

            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            return response.toString();

        } finally {
            conn.disconnect();
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println("=== Testing GraphQL Policy Service ===\n");

            // Test Case 1: Get policy by ID
            System.out.println("Test Case 1: Get Policy by ID");
            System.out.println("-".repeat(60));
            String query1 = "query { policy(policyId: \"POL-001\") { " +
                    "policyId userId policyType status coverageAmount premium isActive } }";
            String response1 = executeQuery(query1, null);
            printFormattedResponse(response1);

            System.out.println("\n");

            // Test Case 2: Get policies by user
            System.out.println("Test Case 2: Get Policies by User");
            System.out.println("-".repeat(60));
            String query2 = "query { policiesByUser(userId: \"USR-123\") { " +
                    "policyId policyType status coverageAmount isActive } }";
            String response2 = executeQuery(query2, null);
            printFormattedResponse(response2);

            System.out.println("\n");

            // Test Case 3: Validate policy (valid claim)
            System.out.println("Test Case 3: Validate Policy - Valid Claim");
            System.out.println("-".repeat(60));
            String query3 = "query ValidatePolicy($policyId: String!, $claimAmount: Float!) { " +
                    "validatePolicy(policyId: $policyId, claimAmount: $claimAmount) { " +
                    "policyId isValid status message validationErrors coverageLimit } }";
            Map<String, Object> variables3 = new HashMap<>();
            variables3.put("policyId", "POL-001");
            variables3.put("claimAmount", 30000.0);
            String response3 = executeQuery(query3, variables3);
            printFormattedResponse(response3);

            System.out.println("\n");

            // Test Case 4: Validate policy (exceeds coverage)
            System.out.println("Test Case 4: Validate Policy - Exceeds Coverage");
            System.out.println("-".repeat(60));
            Map<String, Object> variables4 = new HashMap<>();
            variables4.put("policyId", "POL-001");
            variables4.put("claimAmount", 75000.0);
            String response4 = executeQuery(query3, variables4);
            printFormattedResponse(response4);

            System.out.println("\n");

            // Test Case 5: Validate inactive policy
            System.out.println("Test Case 5: Validate Policy - Inactive Policy");
            System.out.println("-".repeat(60));
            Map<String, Object> variables5 = new HashMap<>();
            variables5.put("policyId", "POL-003");
            variables5.put("claimAmount", 25000.0);
            String response5 = executeQuery(query3, variables5);
            printFormattedResponse(response5);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void printFormattedResponse(String jsonResponse) {
        JsonObject json = gson.fromJson(jsonResponse, JsonObject.class);
        System.out.println(gson.toJson(json));
    }
}
