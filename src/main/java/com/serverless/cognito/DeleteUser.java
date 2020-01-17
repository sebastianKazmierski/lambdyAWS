package com.serverless.cognito;


import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.ApiGatewayResponse;
import com.serverless.responses.ApiResponseHandler;
import java.util.Map;


public class DeleteUser implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {
    private static final CognitoConfig cognitoConfig = new CognitoConfig();
    private static final AWSCognitoIdentityProvider cognitoClient = new UserManagement()
            .getAmazonCognitoIdentityClient();

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        try {
            Map<String, String> pathParameters = (Map<String, String>) input.get("pathParameters");
            try {
                AdminDeleteUserRequest adminDeleteUserRequest = new AdminDeleteUserRequest();
                adminDeleteUserRequest.setUserPoolId(cognitoConfig.getUSER_POOL_ID());
                adminDeleteUserRequest.setUsername(pathParameters.get("username"));

                AdminDeleteUserResult adminDeleteUserResult = cognitoClient.adminDeleteUser(adminDeleteUserRequest);

                return ApiResponseHandler.createResponse(adminDeleteUserResult, 200);
            } catch (Exception ex) {
                return ApiResponseHandler.createResponse("error in deleting user", 404);
            }
        } catch (Exception ex) {
            return ApiResponseHandler.createResponse("error in retrieving username", 400);
        }
    }
}
