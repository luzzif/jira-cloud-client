package com.github.luzzif.jira.cloud.client;

import com.github.luzzif.jira.cloud.client.resources.ResourcesManager;
import com.github.luzzif.jira.cloud.client.retrievers.AccessTokenRetriever;
import com.github.luzzif.jira.cloud.client.retrievers.RequestTokenRetriever;
import com.github.luzzif.jira.cloud.client.utils.Utils;
import com.google.api.client.auth.oauth.OAuthCredentialsResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.security.PrivateKey;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests related to {@link OAuthClientTest}
 */
public class OAuthClientTest {

    private OAuthClient client;

    @Before
    public void setup() {

        client = new OAuthClient(
            "subdomain",
            "consumer-key",
            mock(PrivateKey.class)
        );

    }

    /**
     * Test related to {@link OAuthClient#getRequestToken(String)}.
     */
    @Test
    public void getRequestTokenTest() throws Exception {

        OAuthCredentialsResponse mockedResponse = mock(OAuthCredentialsResponse.class);
        mockedResponse.token = "test-token";

        RequestTokenRetriever mockedRequestTokenRetriever = mock(RequestTokenRetriever.class);
        when(mockedRequestTokenRetriever.execute()).thenReturn(mockedResponse);

        ResourcesManager mockedResourceManager = mock(ResourcesManager.class);
        when(mockedResourceManager.getRequestTokenRetriever(
            anyString(),
            anyString(),
            any(PrivateKey.class),
            anyString()
        )).thenReturn(mockedRequestTokenRetriever);
        Utils.setSingletonMock(mockedResourceManager);

        assertEquals("test-token", client.getRequestToken("callback-url"));

    }

    /**
     * Test related to {@link OAuthClient#getAccessToken(String, String)}}.
     */
    @Test
    public void getAccessTokenTest() throws Exception {

        OAuthCredentialsResponse mockedResponse = mock(OAuthCredentialsResponse.class);
        mockedResponse.token = "test-token";

        AccessTokenRetriever mockedAccessTokenRetriever = mock(AccessTokenRetriever.class);
        when(mockedAccessTokenRetriever.execute()).thenReturn(mockedResponse);

        ResourcesManager mockedResourceManager = mock(ResourcesManager.class);
        when(mockedResourceManager.getAccessTokenRetriever(
            anyString(),
            anyString(),
            any(PrivateKey.class),
            anyString(),
            anyString()
        )).thenReturn(mockedAccessTokenRetriever);
        Utils.setSingletonMock(mockedResourceManager);

        assertEquals("test-token", client.getAccessToken("request-token", "verifier"));

    }

    /**
     * Test related to {@link OAuthClient#getAuthorizationUrl(String)}.
     */
    @Test
    public void getAuthorizationUrlTest() {

        assertEquals(
            "https://subdomain.atlassian.net/plugins/servlet/oauth/authorize?oauth_token=request-token",
            client.getAuthorizationUrl("request-token")
        );

    }

    @After
    public void cleanup() throws Exception {

        Field instance = ResourcesManager.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
        instance.setAccessible(false);

    }

}
