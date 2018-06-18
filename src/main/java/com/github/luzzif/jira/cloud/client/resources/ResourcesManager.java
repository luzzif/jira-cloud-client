package com.github.luzzif.jira.cloud.client.resources;

import com.github.luzzif.jira.cloud.client.retrievers.AccessTokenRetriever;
import com.github.luzzif.jira.cloud.client.retrievers.RequestTokenRetriever;
import com.google.api.client.auth.oauth.OAuthParameters;
import com.google.api.client.auth.oauth.OAuthRsaSigner;

import java.security.PrivateKey;

/**
 * Singleton class. Manages resources related concerns.
 */
public class ResourcesManager {

    private static ResourcesManager instance;
    private static RequestTokenRetriever requestTokenRetriever;
    private static AccessTokenRetriever accessTokenRetriever;
    private static OAuthRsaSigner signer;
    private static OAuthParameters parameters;

    /**
     * Default private constructor.
     */
    private ResourcesManager() {
    }

    /**
     * Standard instance getter for singleton.
     *
     * @return The singleton instrance.
     */
    public static ResourcesManager getInstance() {

        if (instance == null) {
            instance = new ResourcesManager();
        }
        return instance;

    }

    public AccessTokenRetriever getAccessTokenRetriever(
        String jiraSubdomain,
        String consumerKey,
        PrivateKey privateKey,
        String requestToken,
        String verifier) {

        if (accessTokenRetriever == null) {

            accessTokenRetriever = new AccessTokenRetriever(
                jiraSubdomain,
                consumerKey,
                privateKey,
                verifier,
                requestToken
            );

        }
        return accessTokenRetriever;

    }

    public RequestTokenRetriever getRequestTokenRetriever(
        String jiraSubdomain,
        String consumerKey,
        PrivateKey privateKey,
        String callbackUrl) {

        if (requestTokenRetriever == null) {

            requestTokenRetriever = new RequestTokenRetriever(
                jiraSubdomain,
                consumerKey,
                privateKey,
                callbackUrl
            );

        }
        return requestTokenRetriever;

    }

    public OAuthRsaSigner getSigner(PrivateKey privateKey) {

        if (signer == null) {
            signer = new OAuthRsaSigner();
            signer.privateKey = privateKey;
        }
        return signer;

    }

    public OAuthParameters getOAuthParameters(
        String accessToken,
        String verifier,
        String consumerKey,
        PrivateKey privateKey) {

        if (parameters == null) {

            parameters = new OAuthParameters();
            parameters.token = accessToken;
            parameters.signer = getSigner(privateKey);
            parameters.version = "1.0";
            parameters.consumerKey = consumerKey;
            parameters.verifier = verifier;

        }
        return parameters;

    }

}
