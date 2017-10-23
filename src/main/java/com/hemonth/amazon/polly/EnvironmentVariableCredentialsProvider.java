/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hemonth.amazon.polly;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hemonth.Mandava
 */
public class EnvironmentVariableCredentialsProvider implements AWSCredentialsProvider {

    /**
     * Environment variable name for the AWS access key ID
     */
    private static final String ACCESS_KEY_ENV_VAR = "AWS_ACCESS_KEY_ID";

    /**
     * Environment variable name for the AWS secret key
     */
    private static final String SECRET_KEY_ENV_VAR = "AWS_SECRET_KEY";

    public AWSCredentials getCredentials() {
        //Adding ACCESS_KEY_ENV_VAR,SECRET_KEY_ENV_VAR into System properties 
        try {
            FileInputStream propFile = new FileInputStream("accessKey_Secret.txt");
            Properties p = new Properties(System.getProperties());
            p.load(propFile);
            System.setProperties(p);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EnvironmentVariableCredentialsProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EnvironmentVariableCredentialsProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (System.getProperty(ACCESS_KEY_ENV_VAR) != null && System.getProperty(SECRET_KEY_ENV_VAR) != null) {

            return new BasicAWSCredentials(System.getProperty(ACCESS_KEY_ENV_VAR), System.getProperty(SECRET_KEY_ENV_VAR));
        }

        throw new AmazonClientException(
                "Unable to load AWS credentials from environment variables "
                + "(" + ACCESS_KEY_ENV_VAR + " and " + SECRET_KEY_ENV_VAR + ")");
    }

    public void refresh() {
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
