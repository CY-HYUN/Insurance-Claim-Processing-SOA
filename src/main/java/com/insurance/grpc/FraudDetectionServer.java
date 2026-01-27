package com.insurance.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * gRPC Server for Fraud Detection Service
 * Runs on port 50051
 */
public class FraudDetectionServer {
    private Server server;
    private static final int PORT = 50051;

    /**
     * Start the gRPC server
     */
    public void start() throws IOException {
        server = ServerBuilder.forPort(PORT)
                .addService(new FraudDetectionServiceImpl())
                .build()
                .start();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("gRPC Fraud Detection Server started");
        System.out.println("Listening on port: " + PORT);
        System.out.println("=".repeat(60) + "\n");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("Shutting down gRPC server (JVM shutdown hook)");
            try {
                FraudDetectionServer.this.stop();
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
            System.err.println("gRPC server shut down");
        }));
    }

    /**
     * Stop the gRPC server
     */
    public void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    /**
     * Wait for the server to terminate
     */
    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main method to start the server
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final FraudDetectionServer server = new FraudDetectionServer();
        server.start();
        server.blockUntilShutdown();
    }
}
