package com.insurance.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

/**
 * SOAP Web Service for Identity Verification
 * Uses JAX-WS for SOAP implementation
 */
@WebService
@SOAPBinding(style = Style.RPC)
public class IdentityVerificationService {

    /**
     * Verify user identity for insurance claim
     *
     * @param userId User identifier
     * @param name User's full name
     * @param documentId Government-issued document ID
     * @return VerificationResult with verification status
     */
    @WebMethod
    public VerificationResult verifyIdentity(
            @WebParam(name = "userId") String userId,
            @WebParam(name = "name") String name,
            @WebParam(name = "documentId") String documentId) {

        System.out.println("\n=== SOAP Service: Identity Verification ===");
        System.out.println("User ID: " + userId);
        System.out.println("Name: " + name);
        System.out.println("Document ID: " + documentId);

        VerificationResult result = new VerificationResult();
        result.setUserId(userId);

        // Simulate identity verification logic
        if (userId != null && !userId.isEmpty() &&
            name != null && !name.isEmpty() &&
            documentId != null && !documentId.isEmpty()) {

            // Mock verification - in real system, would check against government database
            boolean verified = documentId.length() >= 8; // Simple validation

            result.setVerified(verified);
            result.setConfidenceScore(verified ? 0.95 : 0.45);
            result.setMessage(verified ?
                "Identity verified successfully" :
                "Identity verification failed - invalid document");
            result.setVerificationMethod("GOVERNMENT_ID_CHECK");

        } else {
            result.setVerified(false);
            result.setConfidenceScore(0.0);
            result.setMessage("Missing required verification information");
            result.setVerificationMethod("NONE");
        }

        System.out.println("Verification Result: " + (result.isVerified() ? "PASSED" : "FAILED"));
        System.out.println("Confidence Score: " + result.getConfidenceScore());

        return result;
    }

    /**
     * Health check for SOAP service
     */
    @WebMethod
    public String getServiceStatus() {
        return "Identity Verification Service is running";
    }
}
