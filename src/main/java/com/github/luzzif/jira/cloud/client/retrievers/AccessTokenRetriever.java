package com.github.luzzif.jira.cloud.client.retrievers;

import com.github.luzzif.jira.cloud.client.resources.ResourcesManager;
import com.google.api.client.auth.oauth.OAuthGetAccessToken;
import com.google.api.client.http.apache.ApacheHttpTransport;

import java.security.PrivateKey;

/**
 * Retriever for a Jira Cloud REST API's request token.
 */
public class AccessTokenRetriever extends OAuthGetAccessToken {

    /**
     * Default constructor.
     *
     * @param jiraSubdomain The subdomain at which the Jira Cloud instance is located.
     * @param consumerKey   The consumer's key.
     * @param privateKey    The private key used to sign the request.
     * @param verifier      A valid Jira issued verifier code.
     * @param requestToken  A valid Jira issued request token..
     */
    public AccessTokenRetriever(
        String jiraSubdomain,
        String consumerKey,
        PrivateKey privateKey,
        String verifier,
        String requestToken) {

        super("https://" + jiraSubdomain + ".atlassian.net/plugins/servlet/oauth/access-token");
        usePost = true;
        super.consumerKey = consumerKey;
        super.signer = ResourcesManager.getInstance().getSigner(privateKey);
        super.transport = new ApacheHttpTransport();
        super.verifier = verifier;
        super.temporaryToken = requestToken;

    }

}
