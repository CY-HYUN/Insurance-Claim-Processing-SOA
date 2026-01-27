package com.insurance.grpc;

import com.insurance.grpc.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.concurrent.TimeUnit;

/**
 * gRPC Client for Fraud Detection Service
 */
public class FraudDetectionClient {
    private final ManagedChannel channel;
    private final FraudDetectionGrpc.FraudDetectionBlockingStub blockingStub;

    /**
     * Constructor - creates channel and stub
     */
    public FraudDetectionClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build());
    }

    /**
     * Constructor with custom channel
     */
    public FraudDetectionClient(ManagedChannel channel) {
        this.channel = channel;
        blockingStub = FraudDetectionGrpc.newBlockingStub(channel);
    }

    /**
     * Shutdown the channel
     */
    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    /**
     * Analyze a claim for fraud
     */
    public FraudResponse analyzeClaim(String claimId, String userId, double claimAmount,
                                     String claimType, String incidentDate, String userHistory) {
        FraudRequest request = FraudRequest.newBuilder()
                .setClaimId(claimId)
                .setUserId(userId)
                .setClaimAmount(claimAmount)
                .setClaimType(claimType)
                .setIncidentDate(incidentDate)
                .setUserHistory(userHistory)
                .build();

        FraudResponse response;
        try {
            response = blockingStub.analyzeClaim(request);
        } catch (StatusRuntimeException e) {
            System.err.println("RPC failed: " + e.getStatus());
            return null;
        }
        return response;
    }

    /**
     * Get fraud detection statistics
     */
    public StatisticsResponse getStatistics(String timePeriod) {
        StatisticsRequest request = StatisticsRequest.newBuilder()
                .setTimePeriod(timePeriod)
                .build();

        StatisticsResponse response;
        try {
            response = blockingStub.getStatistics(request);
        } catch (StatusRuntimeException e) {
            System.err.println("RPC failed: " + e.getStatus());
            return null;
        }
        return response;
    }

    /**
     * Test client
     */
    public static void main(String[] args) throws Exception {
        FraudDetectionClient client = new FraudDetectionClient("localhost", 50051);
        try {
            // Test fraud analysis
            System.out.println("=== Testing Fraud Detection ===");
            FraudResponse response = client.analyzeClaim(
                    "CLM-001",
                    "USR-123",
                    75000.0,
                    "ACCIDENT",
                    "2024-01-15",
                    "FIRST_TIME_CLAIM"
            );

            if (response != null) {
                System.out.println("\nFraud Analysis Result:");
                System.out.println("Claim ID: " + response.getClaimId());
                System.out.println("Is Fraudulent: " + response.getIsFraudulent());
                System.out.println("Risk Score: " + response.getRiskScore());
                System.out.println("Risk Level: " + response.getRiskLevel());
                System.out.println("Recommendation: " + response.getRecommendation());
                System.out.println("Explanation: " + response.getExplanation());
                System.out.println("Red Flags: " + response.getRedFlagsList());
            }

            // Test statistics
            System.out.println("\n=== Testing Statistics ===");
            StatisticsResponse stats = client.getStatistics("LAST_30_DAYS");
            if (stats != null) {
                System.out.println("\nFraud Detection Statistics:");
                System.out.println("Total Claims Analyzed: " + stats.getTotalClaimsAnalyzed());
                System.out.println("Fraud Detected: " + stats.getFraudDetected());
                System.out.println("Fraud Rate: " + (stats.getFraudRate() * 100) + "%");
                System.out.println("Total Amount Saved: $" + stats.getTotalAmountSaved());
            }

        } finally {
            client.shutdown();
        }
    }
}
