package io.paradaux.conch.common.api.config;

import org.jetbrains.annotations.NotNull;

import javax.annotation.CheckReturnValue;
import java.util.Map;

public class CachedProxySettings {

    private String configurationVersion;
    private boolean debug;
    private String localeCode;
    private String webhookUrl;
    private boolean eventsEnabled;
    private String serverName;
    private boolean avatarApiHyphen;
    private String avatarApi;
    private boolean proxyBasedWebhookConfiguration;
    private Map<String, String> proxyWebhookConfiguration;

    private CachedProxySettings() {
        // Using The Builder is the requested method as various fields will differ between platforms.
    }

    public static CachedProxySettings.Builder builder() {
        return new CachedProxySettings.Builder();
    }

    public static class Builder {

        private final CachedProxySettings settings;

        private Builder() {
            settings = new CachedProxySettings();
            // Please use the static method CachedSettings#builder to get an instance of this class.
        }

        public CachedProxySettings.Builder setConfigurationVersion(String configurationVersion) {
            settings.configurationVersion = configurationVersion;
            return this;
        }

        public CachedProxySettings.Builder setDebug(boolean debug) {
            settings.debug = debug;
            return this;
        }

        public CachedProxySettings.Builder setLocaleCode(String localeCode) {
            settings.localeCode = localeCode;
            return this;
        }

        public CachedProxySettings.Builder setWebhookUrl(String webhookUrl) {
            settings.webhookUrl = webhookUrl;
            return this;
        }

        public CachedProxySettings.Builder setEventsEnabled(boolean eventsEnabled) {
            settings.eventsEnabled = eventsEnabled;
            return this;
        }

        public CachedProxySettings.Builder setServerName(String serverName) {
            settings.serverName = serverName;
            return this;
        }

        public CachedProxySettings.Builder setAvatarApiHyphen(boolean avatarApiHyphen) {
            settings.avatarApiHyphen = avatarApiHyphen;
            return this;
        }

        public CachedProxySettings.Builder setAvatarApi(String avatarApi) {
            settings.avatarApi = avatarApi;
            return this;
        }

        public CachedProxySettings.Builder setProxyBasedWebhookConfiguration(boolean proxyBasedWebhookConfiguration) {
            settings.proxyBasedWebhookConfiguration = proxyBasedWebhookConfiguration;
            return this;
        }

        public CachedProxySettings.Builder setProxyWebhookConfiguration(Map<String, String> proxyWebhookConfiguration) {
            settings.proxyWebhookConfiguration = proxyWebhookConfiguration;
            return this;
        }

        @CheckReturnValue
        @NotNull
        public CachedProxySettings build() {
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
