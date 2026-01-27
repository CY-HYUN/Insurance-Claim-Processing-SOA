package com.insurance.graphql;

import com.google.gson.Gson;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

/**
 * GraphQL Servlet for Insurance Policy operations
 * Endpoint: /graphql
 */
public class GraphQLServlet extends HttpServlet {

    private GraphQL graphQL;
    private Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        try {
            // Load GraphQL schema
            String schema = loadSchemaFile("schema.graphql");

            // Parse schema
            SchemaParser schemaParser = new SchemaParser();
            TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);

            // Build runtime wiring
            RuntimeWiring runtimeWiring = buildWiring();

            // Generate executable schema
            SchemaGenerator schemaGenerator = new SchemaGenerator();
            GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(
                    typeDefinitionRegistry, runtimeWiring);

            // Create GraphQL instance
            this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();

            System.out.println("GraphQL Service initialized successfully");

        } catch (Exception e) {
            throw new ServletException("Failed to initialize GraphQL", e);
        }
    }

    /**
     * Load GraphQL schema file
     */
    private String loadSchemaFile(String filename) throws IOException {
        InputStream inputStream = getServletContext().getResourceAsStream("/WEB-INF/classes/" + filename);
        if (inputStream == null) {
            throw new IOException("Schema file not found: " + filename);
        }
        return new BufferedReader(new InputStreamReader(inputStream))
                .lines()
                .collect(Collectors.joining("\n"));
    }

    /**
     * Build runtime wiring with data fetchers
     */
    private RuntimeWiring buildWiring() {
        return newRuntimeWiring()
                .type("Query", builder -> builder
                        .dataFetcher("policy", PolicyDataFetcher.getPolicyById())
                        .dataFetcher("policiesByUser", PolicyDataFetcher.getPoliciesByUserId())
                        .dataFetcher("allPolicies", PolicyDataFetcher.getAllPolicies())
                        .dataFetcher("validatePolicy", PolicyDataFetcher.validatePolicy()))
                .build();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Read request body
            String requestBody = request.getReader().lines()
                    .collect(Collectors.joining(System.lineSeparator()));

            // Parse JSON request
            @SuppressWarnings("unchecked")
            Map<String, Object> json = gson.fromJson(requestBody, Map.class);
            String query = (String) json.get("query");
            @SuppressWarnings("unchecked")
            Map<String, Object> variables = json.get("variables") != null ?
                    (Map<String, Object>) json.get("variables") : new HashMap<>();

            System.out.println("\n=== GraphQL Request ===");
            System.out.println("Query: " + query);
            System.out.println("Variables: " + variables);

            // Execute query
            ExecutionResult executionResult = graphQL.execute(
                    graphql.ExecutionInput.newExecutionInput()
                            .query(query)
                            .variables(variables)
                            .build()
            );

            // Send response
            Map<String, Object> result = executionResult.toSpecification();
            String jsonResponse = gson.toJson(result);

            response.getWriter().write(jsonResponse);
            response.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.getWriter().write("{\"message\":\"GraphQL endpoint. Use POST to send queries.\"}");
    }
}
