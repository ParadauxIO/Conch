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
import java.util.List;
import java.util.Map;

public class CachedBotSettings {

    // Fields in the order they're defined in bot-settings.conf

    private boolean enabled;
    private String token;
    private boolean discordCommandsEnabled;
    private String commandPrefix;
    private boolean sendBotMessages;
    private List<String> monitoredChannels;
    boolean proxyBasedMonitoring;
    Map<String, String> proxyMonitoredChannels;
    String messageFormat;

    private CachedBotSettings() {
        // Using The Builder is the requested method as various fields will differ between platforms.
    }

    public static CachedBotSettings.Builder builder() {
        return new CachedBotSettings.Builder();
    }

    public static class Builder {
        private final CachedBotSettings cachedBotSettings;

        @CheckReturnValue
        private Builder() {
            // Please use the static method CachedBot#builder to get an instance of this class.
            cachedBotSettings = new CachedBotSettings();
        }

        public CachedBotSettings.Builder setEnabled(boolean enabled) {
            cachedBotSettings.enabled = enabled;
            return this;
        }

        @CheckReturnValue
        @NotNull
        public CachedBotSettings.Builder setToken(String token) {
            cachedBotSettings.token = token;
            return this;
        }

        @CheckReturnValue
        @NotNull
        public CachedBotSettings.Builder setDiscordCommandsEnabled(boolean discordCommandsEnabled) {
            cachedBotSettings.discordCommandsEnabled = discordCommandsEnabled;
            return this;
        }

        @CheckReturnValue
        @NotNull
        public CachedBotSettings.Builder setCommandPrefix(String commandPrefix) {
            cachedBotSettings.commandPrefix = commandPrefix;
            return this;
        }

        @CheckReturnValue
        @NotNull
        public CachedBotSettings.Builder setSendBotMessages(boolean sendBotMessages) {
            cachedBotSettings.sendBotMessages = sendBotMessages;
            return this;
        }

        @CheckReturnValue
        @NotNull
        public CachedBotSettings.Builder setMonitoredChannels(List<String> monitoredChannels) {
            cachedBotSettings.monitoredChannels = monitoredChannels;
            return this;
        }

        @CheckReturnValue
        @NotNull
        public CachedBotSettings.Builder setProxyBasedMonitoring(boolean proxyBasedMonitoring) {
            cachedBotSettings.proxyBasedMonitoring = proxyBasedMonitoring;
            return this;
        }

        @CheckReturnValue
        @NotNull
        public CachedBotSettings.Builder setProxyMonitoredChannels(Map<String, String> proxyMonitoredChannels) {
            cachedBotSettings.proxyMonitoredChannels = proxyMonitoredChannels;
            return this;
        }

        @CheckReturnValue
        @NotNull
        public CachedBotSettings.Builder setMessageFormat(String messageFormat) {
            cachedBotSettings.messageFormat = messageFormat;
            return this;
        }

        @CheckReturnValue
        @NotNull
        public CachedBotSettings build() {
            return cachedBotSettings;
        }
    }

    @CheckReturnValue
    @NotNull
    public boolean isEnabled() {
        return enabled;
    }

    @CheckReturnValue
    @NotNull
    public String getToken() {
        return token;
    }

    @CheckReturnValue
    @NotNull
    public boolean isDiscordCommandsEnabled() {
        return discordCommandsEnabled;
    }

    @CheckReturnValue
    @NotNull
    public String getCommandPrefix() {
        return commandPrefix;
    }

    @CheckReturnValue
    @NotNull
    public boolean isSendBotMessages() {
        return sendBotMessages;
    }

    @CheckReturnValue
    @NotNull
    public List<String> getMonitoredChannels() {
        return monitoredChannels;
    }

    @CheckReturnValue
    @NotNull
    public boolean isProxyBasedMonitoring() {
        return proxyBasedMonitoring;
    }

    @CheckReturnValue
    @NotNull
    public Map<String, String> getProxyMonitoredChannels() {
        return proxyMonitoredChannels;
    }

    @CheckReturnValue
    @NotNull
    public String getMessageFormat() {
        return messageFormat;
    }
}
