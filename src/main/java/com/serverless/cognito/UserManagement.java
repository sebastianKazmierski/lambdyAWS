package com.serverless.cognito;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;

class UserManagement {
    AWSCognitoIdentityProvider getAmazonCognitoIdentityClient() {
        CognitoConfig cognitoConfig = new CognitoConfig();

        return AWSCognitoIdentityProviderClientBuilder.standard()
                .withCredentials(new EnvironmentVariableCredentialsProvider())
                .withRegion(cognitoConfig.getREGION())
                .build();

    }
}
