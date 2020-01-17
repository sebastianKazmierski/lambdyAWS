package com.serverless.cognito;

public class CognitoConfig
{
    private final String USER_POOL_ID = "us-east-1_T81j2rtFA";
    private final String REGION = "us-east-1";

    public String getREGION() {
        return REGION;
    }

    public String getUSER_POOL_ID() {
        return USER_POOL_ID;
    }
}
