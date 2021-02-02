/*
 * Copyright (c) 2020, Rían Errity. All rights reserved.
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

package io.paradaux.hiberniadiscord.common.api.config;

import org.jetbrains.annotations.NotNull;

import javax.annotation.CheckReturnValue;

public class EventConfiguration {

    private String eventName;
    private boolean enabled;
    private String webhookUsernameFormat;
    private String webhookAvatarFormat;
    private String webhookMessageFormat;

    private EventConfiguration() {
        // Use the builder!
    }

    public static EventConfiguration.Builder builder() {
        return new EventConfiguration.Builder();
    }

    public static class Builder {
        private final EventConfiguration eventConfiguration;

        private Builder() {
            // Please use the static method CachedSettings#builder to get an instance of this class.
            eventConfiguration = new EventConfiguration();
        }

        public EventConfiguration.Builder setEventName(String eventName) {
            eventConfiguration.eventName = eventName;
            return this;
        }

        public EventConfiguration.Builder setEnabled(boolean enabled) {
            eventConfiguration.enabled = enabled;
            return this;
        }

        public EventConfiguration.Builder setWebhookUsernameFormat(String webhookUsernameFormat) {
            eventConfiguration.webhookUsernameFormat = webhookUsernameFormat;
            return this;
        }

        public EventConfiguration.Builder setWebhookAvatarFormat(String webhookAvatarFormat) {
            eventConfiguration.webhookAvatarFormat = webhookAvatarFormat;
            return this;
        }

        public EventConfiguration.Builder setWebhookMessageFormat(String webhookMessageFormat) {
            eventConfiguration.webhookMessageFormat = webhookMessageFormat;
            return this;
        }

        @CheckReturnValue
        @NotNull
        public EventConfiguration build() {
            return eventConfiguration;
        }

    }

    @CheckReturnValue
    @NotNull
    public String getEventName() {
        return eventName;
    }

    @CheckReturnValue
    @NotNull
    public boolean isEnabled() {
        return enabled;
    }

    @CheckReturnValue
    @NotNull
    public String getWebhookUsernameFormat() {
        return webhookUsernameFormat;
    }

    @CheckReturnValue
    @NotNull
    public String getWebhookAvatarFormat() {
        return webhookAvatarFormat;
    }

    @CheckReturnValue
    @NotNull
    public String getWebhookMessageFormat() {
        return webhookMessageFormat;
    }
}
