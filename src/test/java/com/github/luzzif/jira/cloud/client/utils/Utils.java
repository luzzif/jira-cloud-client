package com.github.luzzif.jira.cloud.client.utils;

import com.github.luzzif.jira.cloud.client.resources.ResourcesManager;

import java.lang.reflect.Field;

/**
 * Generic utility class.
 */
public class Utils {

    /**
     * Sets a singleton mock of type {@link ResourcesManager}.
     *
     * @param mock The mock to be set as singleton.
     */
    public static void setSingletonMock(ResourcesManager mock) throws Exception {

        Field instance = ResourcesManager.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(instance, mock);

    }

}
