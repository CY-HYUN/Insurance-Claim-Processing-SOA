package com.insurance.service;

import com.insurance.dto.ClaimRequest;
import com.insurance.dto.ClaimResponse;
import com.insurance.orchestrator.InsuranceClaimOrchestrator;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * REST Service for Insurance Claim Submission
 * Endpoint: /api/claims
 */
@Path("/claims")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClaimSubmissionService {

    private InsuranceClaimOrchestrator orchestrator = new InsuranceClaimOrchestrator();

    /**
     * Submit a new insurance claim
     * POST /api/claims/submit
     */
    @POST
    @Path("/submit")
    public Response submitClaim(ClaimRequest request) {
        try {
            System.out.println("=== REST Service: Received Claim Submission ===");
            System.out.println("Claim ID: " + request.getClaimId());
            System.out.println("User ID: " + request.getUserId());
            System.out.println("Claim Type: " + request.getClaimType());
            System.out.println("Amount: $" + request.getClaimAmount());

            // Orchestrate the claim processing
            ClaimResponse response = orchestrator.processClaim(request);

            return Response.ok(response).build();

        } catch (Exception e) {
            e.printStackTrace();
            ClaimResponse errorResponse = new ClaimResponse();
            errorResponse.setClaimId(request.getClaimId());
            errorResponse.setStatus("ERROR");
            errorResponse.setMessage("Error processing claim: " + e.getMessage());
            errorResponse.setTimestamp(getCurrentTimestamp());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(errorResponse)
                    .build();
        }
    }

    /**
     * Get claim status
     * GET /api/claims/{claimId}
     */
    @GET
    @Path("/{claimId}")
    public Response getClaimStatus(@PathParam("claimId") String claimId) {
        try {
            System.out.println("=== REST Service: Checking Claim Status ===");
            System.out.println("Claim ID: " + claimId);

            ClaimResponse response = new ClaimResponse();
            response.setClaimId(claimId);
            response.setStatus("PENDING");
            response.setMessage("Claim is being processed");
            response.setTimestamp(getCurrentTimestamp());

            return Response.ok(response).build();

        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Claim not found: " + claimId)
                    .build();
        }
    }

    /**
     * Health check endpoint
     * GET /api/claims/health
     */
    @GET
    @Path("/health")
    public Response healthCheck() {
        return Response.ok("{\"status\":\"UP\",\"service\":\"ClaimSubmissionService\"}")
                .build();
    }

    private String getCurrentTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}
