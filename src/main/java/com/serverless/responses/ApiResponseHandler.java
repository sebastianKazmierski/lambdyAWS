package com.serverless.responses;

import com.serverless.ApiGatewayResponse;

import java.util.HashMap;
import java.util.Map;

public class ApiResponseHandler {
    public static ApiGatewayResponse createResponse(Object objectBody, int code){
        Map<String,String> headers = new HashMap<>();
        headers.put("X-Powered-By","AWS Lambda & serverless");
        headers.put("Access-Control-Allow-Origin","*");
        headers.put("Access-Control-Allow-Credentials","true");
        return ApiGatewayResponse.builder()
                .setStatusCode(code)
                .setObjectBody(objectBody)
                .setHeaders(headers)
                .build();
    }
}
