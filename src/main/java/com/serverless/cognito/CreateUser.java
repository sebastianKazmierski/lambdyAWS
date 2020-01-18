package com.serverless.cognito;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.ApiGatewayResponse;
import com.serverless.responses.ApiResponseHandler;

import java.util.*;


public class CreateUser implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {
    private static final CognitoConfig cognitoConfig = new CognitoConfig();
    private static final AWSCognitoIdentityProvider cognitoClient = new UserManagement()
            .getAmazonCognitoIdentityClient();

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        try {
            JsonNode body = new ObjectMapper().readValue((String) input.get("body"), JsonNode.class);
            try {
                AdminCreateUserRequest adminCreateUserRequest = new AdminCreateUserRequest();
                adminCreateUserRequest.setUserPoolId(cognitoConfig.getUSER_POOL_ID());
                adminCreateUserRequest.setUsername(body.get("username").asText());
                adminCreateUserRequest.withTemporaryPassword(body.get("temporaryPassword").asText());
                adminCreateUserRequest.setForceAliasCreation(body.get("forceAliasCreation").asBoolean());
                adminCreateUserRequest.setDesiredDeliveryMediums(List.of("EMAIL"));

                AttributeType profile = new AttributeType();
                profile.setName("profile");
                profile.setValue(body.get("profile").asText());
                AttributeType name = new AttributeType();
                name.setName("name");
                name.setValue(body.get("name").asText());
                AttributeType email = new AttributeType();
                email.setName("email");
                email.setValue(body.get("email").asText());
                List<AttributeType> attributes = List.of(profile, name, email);
                adminCreateUserRequest.setUserAttributes(attributes);

                AdminCreateUserResult adminCreateUserResult = cognitoClient.adminCreateUser(adminCreateUserRequest);

                return ApiResponseHandler.createResponse(adminCreateUserResult, 200);
            } catch (Exception ex) {
                return ApiResponseHandler.createResponse("error in creating user", 404);
            }
        } catch (Exception ex) {
            return ApiResponseHandler.createResponse("error in retrieving username", 400);
        }
    }
}
