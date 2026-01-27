package com.insurance.dto;

/**
 * Data Transfer Object for Insurance Claim Request
 * Used in REST API for claim submission
 */
public class ClaimRequest {
    private String claimId;
    private String userId;
    private String claimType;
    private double claimAmount;
    private String description;
    private String incidentDate;

    // Default constructor
    public ClaimRequest() {
    }

    // Parameterized constructor
    public ClaimRequest(String claimId, String userId, String claimType,
                       double claimAmount, String description, String incidentDate) {
        this.claimId = claimId;
        this.userId = userId;
        this.claimType = claimType;
        this.claimAmount = claimAmount;
        this.description = description;
        this.incidentDate = incidentDate;
    }

    // Getters and Setters
    public String getClaimId() {
        return claimId;
    }

    public void setClaimId(String claimId) {
        this.claimId = claimId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClaimType() {
        return claimType;
    }

    public void setClaimType(String claimType) {
        this.claimType = claimType;
    }

    public double getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(double claimAmount) {
        this.claimAmount = claimAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIncidentDate() {
        return incidentDate;
    }

    public void setIncidentDate(String incidentDate) {
        this.incidentDate = incidentDate;
    }

    @Override
    public String toString() {
        return "ClaimRequest{" +
                "claimId='" + claimId + '\'' +
                ", userId='" + userId + '\'' +
                ", claimType='" + claimType + '\'' +
                ", claimAmount=" + claimAmount +
                ", description='" + description + '\'' +
                ", incidentDate='" + incidentDate + '\'' +
                '}';
    }
}
