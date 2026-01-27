package com.insurance.graphql;

/**
 * Insurance Policy model for GraphQL
 */
public class Policy {
    private String policyId;
    private String userId;
    private String policyType;
    private String status;
    private double coverageAmount;
    private double premium;
    private String startDate;
    private String endDate;
    private boolean isActive;

    // Default constructor
    public Policy() {
    }

    // Parameterized constructor
    public Policy(String policyId, String userId, String policyType, String status,
                 double coverageAmount, double premium, String startDate, String endDate, boolean isActive) {
        this.policyId = policyId;
        this.userId = userId;
        this.policyType = policyType;
        this.status = status;
        this.coverageAmount = coverageAmount;
        this.premium = premium;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
    }

    // Getters and Setters
    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getCoverageAmount() {
        return coverageAmount;
    }

    public void setCoverageAmount(double coverageAmount) {
        this.coverageAmount = coverageAmount;
    }

    public double getPremium() {
        return premium;
    }

    public void setPremium(double premium) {
        this.premium = premium;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Policy{" +
                "policyId='" + policyId + '\'' +
                ", userId='" + userId + '\'' +
                ", policyType='" + policyType + '\'' +
                ", status='" + status + '\'' +
                ", coverageAmount=" + coverageAmount +
                ", premium=" + premium +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
