package com.insurance.dto;

/**
 * Data Transfer Object for Insurance Claim Response
 * Used in REST API for claim submission response
 */
public class ClaimResponse {
    private String claimId;
    private String status;
    private String message;
    private String timestamp;
    private boolean identityVerified;
    private boolean fraudCheckPassed;
    private String policyStatus;

    // Default constructor
    public ClaimResponse() {
    }

    // Parameterized constructor
    public ClaimResponse(String claimId, String status, String message, String timestamp) {
        this.claimId = claimId;
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public String getClaimId() {
        return claimId;
    }

    public void setClaimId(String claimId) {
        this.claimId = claimId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isIdentityVerified() {
        return identityVerified;
    }

    public void setIdentityVerified(boolean identityVerified) {
        this.identityVerified = identityVerified;
    }

    public boolean isFraudCheckPassed() {
        return fraudCheckPassed;
    }

    public void setFraudCheckPassed(boolean fraudCheckPassed) {
        this.fraudCheckPassed = fraudCheckPassed;
    }

    public String getPolicyStatus() {
        return policyStatus;
    }

    public void setPolicyStatus(String policyStatus) {
        this.policyStatus = policyStatus;
    }

    @Override
    public String toString() {
        return "ClaimResponse{" +
                "claimId='" + claimId + '\'' +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", identityVerified=" + identityVerified +
                ", fraudCheckPassed=" + fraudCheckPassed +
                ", policyStatus='" + policyStatus + '\'' +
                '}';
    }
}
