package com.insurance.grpc;

import com.insurance.grpc.proto.*;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * gRPC Service Implementation for Fraud Detection
 */
public class FraudDetectionServiceImpl extends FraudDetectionGrpc.FraudDetectionImplBase {

    @Override
    public void analyzeClaim(FraudRequest request, StreamObserver<FraudResponse> responseObserver) {
        System.out.println("\n=== gRPC Service: Fraud Detection Analysis ===");
        System.out.println("Claim ID: " + request.getClaimId());
        System.out.println("User ID: " + request.getUserId());
        System.out.println("Claim Amount: $" + request.getClaimAmount());
        System.out.println("Claim Type: " + request.getClaimType());

        // Analyze claim for fraud
        FraudResponse response = performFraudAnalysis(request);

        System.out.println("Risk Score: " + response.getRiskScore());
        System.out.println("Risk Level: " + response.getRiskLevel());
        System.out.println("Recommendation: " + response.getRecommendation());

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getStatistics(StatisticsRequest request, StreamObserver<StatisticsResponse> responseObserver) {
        System.out.println("\n=== gRPC Service: Fraud Statistics Request ===");
        System.out.println("Time Period: " + request.getTimePeriod());

        StatisticsResponse response = StatisticsResponse.newBuilder()
                .setTotalClaimsAnalyzed(1000)
                .setFraudDetected(45)
                .setFraudRate(0.045)
                .setTotalAmountSaved(125000)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * Perform fraud analysis on the claim
     */
    private FraudResponse performFraudAnalysis(FraudRequest request) {
        double riskScore = 0.0;
        List<String> redFlags = new ArrayList<>();
        boolean isFraudulent = false;
        String riskLevel = "LOW";
        String recommendation = "APPROVE";
        String explanation = "";

        // Rule 1: Check claim amount (suspicious if > $50,000)
        if (request.getClaimAmount() > 50000) {
            riskScore += 0.3;
            redFlags.add("High claim amount (> $50,000)");
        }

        // Rule 2: Check claim amount (very suspicious if > $100,000)
        if (request.getClaimAmount() > 100000) {
            riskScore += 0.4;
            redFlags.add("Very high claim amount (> $100,000)");
        }

        // Rule 3: Check for pattern in claim type
        if (request.getClaimType().equalsIgnoreCase("ACCIDENT") &&
            request.getClaimAmount() > 75000) {
            riskScore += 0.2;
            redFlags.add("High-value accident claim requires investigation");
        }

        // Rule 4: Check user history
        if (request.getUserHistory().contains("MULTIPLE_CLAIMS")) {
            riskScore += 0.25;
            redFlags.add("User has history of multiple claims");
        }

        // Normalize risk score
        riskScore = Math.min(riskScore, 1.0);

        // Determine risk level and recommendation
        if (riskScore < 0.3) {
            riskLevel = "LOW";
            recommendation = "APPROVE";
            explanation = "Low fraud risk detected. Claim can be processed normally.";
        } else if (riskScore < 0.6) {
            riskLevel = "MEDIUM";
            recommendation = "MANUAL_REVIEW";
            explanation = "Medium fraud risk detected. Manual review recommended.";
        } else if (riskScore < 0.8) {
            riskLevel = "HIGH";
            recommendation = "MANUAL_REVIEW";
            explanation = "High fraud risk detected. Thorough investigation required.";
            isFraudulent = true;
        } else {
            riskLevel = "CRITICAL";
            recommendation = "REJECT";
            explanation = "Critical fraud risk detected. Claim should be rejected.";
            isFraudulent = true;
        }

        // Build response
        FraudResponse.Builder responseBuilder = FraudResponse.newBuilder()
                .setClaimId(request.getClaimId())
                .setIsFraudulent(isFraudulent)
                .setRiskScore(riskScore)
                .setRiskLevel(riskLevel)
                .setExplanation(explanation)
                .setRecommendation(recommendation);

        // Add all red flags
        for (String flag : redFlags) {
            responseBuilder.addRedFlags(flag);
        }

        return responseBuilder.build();
    }
}
