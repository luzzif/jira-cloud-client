package com.github.luzzif.jira.cloud.client.resources;

import org.junit.Test;

import java.security.PrivateKey;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

/**
 * Tests related to {@link ResourcesManager}.
 */
public class ResourcesManagerTest {

    /**
     * Test related to {@link ResourcesManager#getInstance()}.
     */
    @Test
    public void getInstanceTest() {
        assertNotNull(ResourcesManager.getInstance());
    }

    /**
     * Test related to {@link ResourcesManager#getRequestTokenRetriever(String, String, PrivateKey, String)}.
     */
    @Test
    public void getRequestTokenRetrieverTest() {

        assertNotNull(ResourcesManager.getInstance().getRequestTokenRetriever(
            "subdomain",
            "consumer-key",
            mock(PrivateKey.class),
            "callback-url"
        ));

    }

    /**
     * Test related to {@link ResourcesManager#getAccessTokenRetriever(String, String, PrivateKey, String, String)}.
     */
    @Test
    public void getAccessTokenRetrieverTest() {

        assertNotNull(ResourcesManager.getInstance().getAccessTokenRetriever(
            "subdomain",
            "consumer-key",
            mock(PrivateKey.class),
            "request-token",
            "verifier"
        ));

    }

    /**
     * Test related to {@link ResourcesManager#getSigner(PrivateKey)}.
     */
    @Test
    public void getSignerTest() {
        assertNotNull(ResourcesManager.getInstance().getSigner(mock(PrivateKey.class)));
    }

    /**
     * Test related to {@link ResourcesManager#getOAuthParameters(String, String, String, PrivateKey)}.
     */
    @Test
    public void getOAuthParametersTest() {

        assertNotNull(ResourcesManager.getInstance().getOAuthParameters(
            "access-token",
            "verifier",
            "consumer-key",
            mock(PrivateKey.class)
        ));

    }

}
