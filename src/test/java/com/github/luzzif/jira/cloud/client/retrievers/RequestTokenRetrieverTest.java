package com.github.luzzif.jira.cloud.client.retrievers;

import com.google.api.client.http.apache.ApacheHttpTransport;
import org.junit.Test;

import java.security.PrivateKey;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Tests related to {@link RequestTokenRetriever}.
 */
public class RequestTokenRetrieverTest {

    /**
     * Tests the correct setup of a {@link RequestTokenRetriever} instance.
     */
    @Test
    public void coherentSetupTest() {

        RequestTokenRetriever requestTokenRetriever = new RequestTokenRetriever(
            "subdomain",
            "consumer-key",
            mock(PrivateKey.class),
            "callback-url"
            );

        assertEquals("https://subdomain.atlassian.net/plugins/servlet/oauth/request-token", requestTokenRetriever.build());
        assertEquals("consumer-key", requestTokenRetriever.consumerKey);
        assertNotNull(requestTokenRetriever.signer);
        assertTrue(requestTokenRetriever.transport instanceof ApacheHttpTransport);
        assertEquals("callback-url", requestTokenRetriever.callback);

    }

}
