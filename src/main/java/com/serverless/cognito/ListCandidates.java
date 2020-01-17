package com.serverless.cognito;


import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.ApiGatewayResponse;
import com.serverless.responses.ApiResponseHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ListCandidates implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {
    private static final CognitoConfig cognitoConfig = new CognitoConfig();
    private static final AWSCognitoIdentityProvider cognitoClient = new UserManagement()
            .getAmazonCognitoIdentityClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        try {
            ListUsersRequest listUsersRequest = new ListUsersRequest();
            listUsersRequest.setUserPoolId(cognitoConfig.getUSER_POOL_ID());

            List<String> attributeToGet = new ArrayList<>();
            attributeToGet.add("name");
            listUsersRequest.setAttributesToGet(attributeToGet);

            listUsersRequest.setFilter("name ^= \"Candidate\"");

            ListUsersResult listUsersResult = cognitoClient.listUsers(listUsersRequest);

            String jsonString = objectMapper.writeValueAsString(listUsersResult.getUsers());
            JsonNode node = objectMapper.readValue(jsonString, JsonNode.class);

            return ApiResponseHandler.createResponse(node, 200);
        } catch (Exception ex) {
            return ApiResponseHandler.createResponse("cannot get list of candidates.", 401);
        }
    }
}
