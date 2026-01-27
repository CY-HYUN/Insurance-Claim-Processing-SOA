package com.insurance.graphql;

import java.util.ArrayList;
import java.util.List;

/**
 * Policy validation result for GraphQL
 */
public class ValidationResult {
    private String policyId;
    private boolean isValid;
    private String status;
    private String message;
    private List<String> validationErrors;
    private double coverageLimit;

    // Default constructor
    public ValidationResult() {
        this.validationErrors = new ArrayList<>();
    }

    // Parameterized constructor
    public ValidationResult(String policyId, boolean isValid, String status, String message) {
        this.policyId = policyId;
        this.isValid = isValid;
        this.status = status;
        this.message = message;
        this.validationErrors = new ArrayList<>();
    }

    // Getters and Setters
    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
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

    public List<String> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(List<String> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public void addValidationError(String error) {
        this.validationErrors.add(error);
    }

    public double getCoverageLimit() {
        return coverageLimit;
    }

    public void setCoverageLimit(double coverageLimit) {
        this.coverageLimit = coverageLimit;
    }

    @Override
    public String toString() {
        return "ValidationResult{" +
                "policyId='" + policyId + '\'' +
                ", isValid=" + isValid +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", validationErrors=" + validationErrors +
                ", coverageLimit=" + coverageLimit +
                '}';
    }
}
