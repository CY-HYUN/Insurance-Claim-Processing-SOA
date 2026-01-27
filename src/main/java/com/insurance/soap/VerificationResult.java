package com.insurance.soap;

/**
 * Result object for identity verification
 * Used by SOAP IdentityVerificationService
 */
public class VerificationResult {
    private String userId;
    private boolean verified;
    private double confidenceScore;
    private String message;
    private String verificationMethod;

    // Default constructor
    public VerificationResult() {
    }

    // Parameterized constructor
    public VerificationResult(String userId, boolean verified, double confidenceScore,
                            String message, String verificationMethod) {
        this.userId = userId;
        this.verified = verified;
        this.confidenceScore = confidenceScore;
        this.message = message;
        this.verificationMethod = verificationMethod;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public double getConfidenceScore() {
        return confidenceScore;
    }

    public void setConfidenceScore(double confidenceScore) {
        this.confidenceScore = confidenceScore;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getVerificationMethod() {
        return verificationMethod;
    }

    public void setVerificationMethod(String verificationMethod) {
        this.verificationMethod = verificationMethod;
    }

    @Override
    public String toString() {
        return "VerificationResult{" +
                "userId='" + userId + '\'' +
                ", verified=" + verified +
                ", confidenceScore=" + confidenceScore +
                ", message='" + message + '\'' +
                ", verificationMethod='" + verificationMethod + '\'' +
                '}';
    }
}
