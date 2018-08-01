package com.github.luzzif.jira.cloud.client;

import com.github.luzzif.jira.cloud.client.resources.ResourcesManager;
import com.google.api.client.auth.oauth.OAuthParameters;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;

import java.io.IOException;
import java.security.PrivateKey;

/**
 * Represents an OAuth client capable of requesting
 * resources from Jira Cloud's REST API.
 */
public class OAuthClient {

    private String jiraSubdomain;
    private String consumerKey;
    private PrivateKey privateKey;

    /**
     * Empty constructor.
     */
    public OAuthClient() {
    }

    /**
     * Default constructor.
     *
     * @param jiraSubdomain The Jira Cloud's instance subdomain.
     * @param consumerKey   The consumer's key.
     * @param privateKey    The private key used to sign the request.
     */
    public OAuthClient(
        String jiraSubdomain,
        String consumerKey,
        PrivateKey privateKey) {

        this.jiraSubdomain = jiraSubdomain;
        this.consumerKey = consumerKey;
        this.privateKey = privateKey;

    }

    /**
     * Retrieves a request token from Jira.
     *
     * @param callbackUrl The callback URL.
     * @return A freshly issued request token.
     * @throws IOException If something goes wrong during the token retrieval.
     */
    public String getRequestToken(String callbackUrl) throws IOException {

        return ResourcesManager.getInstance().getRequestTokenRetriever(
            jiraSubdomain,
            consumerKey,
            privateKey,
            callbackUrl
        ).execute().token;

    }

    /**
     * Retrieves an access token from Jira.
     *
     * @param requestToken The request token as gotten from {@link #getRequestToken(String)}.
     * @param verifier     The verifier appended to the callback's query.
     * @return A freshly issued access token.
     * @throws IOException If something goes wrong during the token retrieval.
     */
    public String getAccessToken(String requestToken, String verifier) throws IOException {

        return ResourcesManager.getInstance().getAccessTokenRetriever(
            jiraSubdomain,
            consumerKey,
            privateKey,
            requestToken,
            verifier
        ).execute().token;

    }

    /**
     * Builds an authorization URL.
     *
     * @param requestToken The request token as gotten from {@link #getRequestToken(String)}.
     * @return A built authorization URL.
     */
    public String getAuthorizationUrl(String requestToken) {
        return "https://" + jiraSubdomain + ".atlassian.net/plugins/servlet/" +
            "oauth/authorize?oauth_token=" + requestToken;
    }

    /**
     * Executes a Jira Cloud's REST API call.
     *
     * @param method      The method through which the request should be performed.
     * @param resource    The wanted resource.
     * @param accessToken The access token as gotten from
     *                    {@link #getAccessToken(String, String)}.
     * @param verifier    The verifier code.
     * @return A {@link HttpResponse} instance representing Jira's response.
     * @throws IOException If something goes wrong in the process.
     */
    public HttpResponse execute(
        String method,
        String resource,
        String accessToken,
        String verifier) throws IOException {

        OAuthParameters parameters = ResourcesManager.getInstance().getOAuthParameters(
            accessToken,
            verifier,
            consumerKey,
            privateKey
        );

        HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory(parameters);
        GenericUrl genericUrl = new GenericUrl("https://" + jiraSubdomain + ".atlassian.net");
        genericUrl.appendRawPath(resource);
        HttpRequest request = requestFactory
            .buildRequest(method, genericUrl, null);
        return request.execute();

    }

    public String getJiraSubdomain() {
        return jiraSubdomain;
    }

    public void setJiraSubdomain(String jiraSubdomain) {
        this.jiraSubdomain = jiraSubdomain;
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

}
