/*
 * Copyright (c) 2021, Rían Errity. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 3 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 3 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 3 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Rían Errity <rian@paradaux.io> or visit https://paradaux.io
 * if you need additional information or have any questions.
 * See LICENSE.md for more details.
 */

package io.paradaux.conch.common.api.config;

import org.jetbrains.annotations.NotNull;

import javax.annotation.CheckReturnValue;
import java.util.Map;

public class CachedSettings {

    String configurationVersion;
    boolean debug;
    String localeCode;
    String webhookUrl;
    boolean eventsEnabled;
    String serverName;
    boolean avatarApiHyphen;
    String avatarApi;
    boolean proxyBasedWebhookConfiguration;
    Map<String, String> proxyWebhookConfiguration;

    private CachedSettings() {
        // Using The Builder is the requested method as various fields will differ between platforms.
    }

    public static CachedSettings.Builder builder() {
        return new CachedSettings.Builder();
    }

    public static class Builder {

        private final CachedSettings settings;

        private Builder() {
            settings = new CachedSettings();
            // Please use the static method CachedSettings#builder to get an instance of this class.
        }

        public CachedSettings.Builder setConfigurationVersion(String configurationVersion) {
            settings.configurationVersion = configurationVersion;
            return this;
        }

        public CachedSettings.Builder setDebug(boolean debug) {
            settings.debug = debug;
            return this;
        }

        public CachedSettings.Builder setLocaleCode(String localeCode) {
            settings.localeCode = localeCode;
            return this;
        }

        public CachedSettings.Builder setWebhookUrl(String webhookUrl) {
            settings.webhookUrl = webhookUrl;
            return this;
        }

        public CachedSettings.Builder setEventsEnabled(boolean eventsEnabled) {
            settings.eventsEnabled = eventsEnabled;
            return this;
        }

        public CachedSettings.Builder setServerName(String serverName) {
            settings.serverName = serverName;
            return this;
        }

        public CachedSettings.Builder setAvatarApiHyphen(boolean avatarApiHyphen) {
            settings.avatarApiHyphen = avatarApiHyphen;
            return this;
        }

        public CachedSettings.Builder setAvatarApi(String avatarApi) {
            settings.avatarApi = avatarApi;
            return this;
        }

        public CachedSettings.Builder setProxyBasedWebhookConfiguration(boolean proxyBasedWebhookConfiguration) {
            settings.proxyBasedWebhookConfiguration = proxyBasedWebhookConfiguration;
            return this;
        }

        public CachedSettings.Builder setProxyWebhookConfiguration(Map<String, String> proxyWebhookConfiguration) {
            settings.proxyWebhookConfiguration = proxyWebhookConfiguration;
            return this;
        }

        @CheckReturnValue
        @NotNull
        public CachedSettings build() {
            return settings;
        }

    }

    public String getConfigurationVersion() {
        return configurationVersion;
    }

    public boolean isDebug() {
        return debug;
    }

    public String getLocaleCode() {
        return localeCode;
    }

    public String getWebhookUrl() {
        return webhookUrl;
    }

    public boolean isEventsEnabled() {
        return eventsEnabled;
    }

    public String getServerName() {
        return serverName;
    }

    public boolean isAvatarApiHyphen() {
        return avatarApiHyphen;
    }

    public String getAvatarApi() {
        return avatarApi;
    }

    public boolean isProxyBasedWebhookConfiguration() {
        return proxyBasedWebhookConfiguration;
    }

    public Map<String, String> getProxyWebhookConfiguration() {
        return proxyWebhookConfiguration;
    }
}
