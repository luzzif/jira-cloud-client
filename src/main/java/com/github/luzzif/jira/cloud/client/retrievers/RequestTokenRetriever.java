package com.github.luzzif.jira.cloud.client.retrievers;

import com.github.luzzif.jira.cloud.client.resources.ResourcesManager;
import com.google.api.client.auth.oauth.OAuthGetTemporaryToken;
import com.google.api.client.http.apache.ApacheHttpTransport;

import java.security.PrivateKey;

/**
 * Retriever for a Jira Cloud REST API's request token.
 */
public class RequestTokenRetriever extends OAuthGetTemporaryToken {

    /**
     * Default constructor.
     *
     * @param jiraSubdomain The subdomain at which the Jira Cloud instance is located.
     * @param consumerKey   The consumer's key.
     * @param privateKey    The private key used to sign the request.
     * @param callbackUrl   The callback URL.
     */
    public RequestTokenRetriever(
        String jiraSubdomain,
        String consumerKey,
        PrivateKey privateKey,
        String callbackUrl) {

        super("https://" + jiraSubdomain + ".atlassian.net/plugins/servlet/oauth/request-token");
        this.usePost = true;
        super.consumerKey = consumerKey;
        super.signer = ResourcesManager.getInstance().getSigner(privateKey);
        super.transport = new ApacheHttpTransport();
        super.callback = callbackUrl;

    }

}
