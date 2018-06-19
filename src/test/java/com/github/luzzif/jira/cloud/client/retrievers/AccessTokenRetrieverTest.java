package com.github.luzzif.jira.cloud.client.retrievers;

import com.google.api.client.http.apache.ApacheHttpTransport;
import org.junit.Test;

import java.security.PrivateKey;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Tests related to {@link AccessTokenRetriever}.
 */
public class AccessTokenRetrieverTest {

    /**
     * Tests the correct setup of a {@link AccessTokenRetriever} instance.
     */
    @Test
    public void coherentSetupTest() {

        AccessTokenRetriever accessTokenRetriever = new AccessTokenRetriever(
            "subdomain",
            "consumer-key",
            mock(PrivateKey.class),
            "verifier",
            "request-token"
        );

        assertEquals("https://subdomain.atlassian.net/plugins/servlet/oauth/access-token", accessTokenRetriever.build());
        assertEquals("consumer-key", accessTokenRetriever.consumerKey);
        assertNotNull(accessTokenRetriever.signer);
        assertTrue(accessTokenRetriever.transport instanceof ApacheHttpTransport);
        assertEquals("verifier", accessTokenRetriever.verifier);
        assertEquals("request-token", accessTokenRetriever.temporaryToken);

    }

}
