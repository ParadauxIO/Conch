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

import io.paradaux.hiberniadiscord.common.api.exceptions.NoSuchEventListenerException;
import org.jetbrains.annotations.NotNull;

import javax.annotation.CheckReturnValue;

public class CachedEventSettings {

    // Fields in the order they're defined in event-settings.conf

    EventConfiguration onChatMessage;
    EventConfiguration onPlayerJoin;
    EventConfiguration onPlayerLeave;
    EventConfiguration onPlayerDeath;
    EventConfiguration onPlayerAchievementCompleted;
    EventConfiguration onPlayerKick;
    EventConfiguration onPlayerRespawn;
    EventConfiguration onServerStartup;
    EventConfiguration onGracefulShutdown;

    private CachedEventSettings() {
        // Using The Builder is the requested method as various fields will differ between platforms.
    }

    public static CachedEventSettings.Builder builder() {
        return new CachedEventSettings.Builder();
    }

    public static class Builder {

        private final CachedEventSettings eventSettings;

        private Builder() {
            // Please use the static method CachedSettings#builder to get an instance of this class.
            eventSettings = new CachedEventSettings();
        }

        /**
         * Set the correct EventConfiguration Field by {@link EventConfiguration#eventName}.
         *
         * @implNote This was done as a generic set with switch-case to allow me to use a foreach loop to load the configuration.
         * @see ConfigurationLoader#loadEventSettings()
         * */
        @NotNull
        public CachedEventSettings.Builder set(EventConfiguration eventConfiguration) throws NoSuchEventListenerException {
            switch (eventConfiguration.getEventName()) {
                case "onChatMessage": {
                    eventSettings.onChatMessage = eventConfiguration;
                    break;
                }

                case "onPlayerJoin": {
                    eventSettings.onPlayerJoin = eventConfiguration;
                    break;
                }

                case "onPlayerLeave": {
                    eventSettings.onPlayerLeave = eventConfiguration;
                    break;
                }

                case "onPlayerDeath": {
                    eventSettings.onPlayerDeath = eventConfiguration;
                    break;
                }

                case "onPlayerAchievementCompleted": {
                    eventSettings.onPlayerAchievementCompleted = eventConfiguration;
                    break;
                }

                case "onPlayerKick": {
                    eventSettings.onPlayerKick = eventConfiguration;
                    break;
                }

                case "onPlayerRespawn": {
                    eventSettings.onPlayerRespawn = eventConfiguration;
                    break;
                }

                case "onServerStartup": {
                    eventSettings.onServerStartup = eventConfiguration;
                    break;
                }

                case "onGracefulShutdown": {
                    eventSettings.onGracefulShutdown = eventConfiguration;
                    break;
                }

                default: {
                    throw new NoSuchEventListenerException("This event listener does not exist.");
                }

            }

            return this;
        }

        @CheckReturnValue
        @NotNull
        public CachedEventSettings build() {
            return eventSettings;
        }

    }

    @CheckReturnValue
    @NotNull
    public EventConfiguration getOnChatMessage() {
        return onChatMessage;
    }

    @CheckReturnValue
    @NotNull
    public EventConfiguration getOnPlayerJoin() {
        return onPlayerJoin;
    }

    @CheckReturnValue
    @NotNull
    public EventConfiguration getOnPlayerLeave() {
        return onPlayerLeave;
    }

    @CheckReturnValue
    @NotNull
    public EventConfiguration getOnPlayerDeath() {
        return onPlayerDeath;
    }

    @CheckReturnValue
    @NotNull
    public EventConfiguration getOnPlayerAchievementCompleted() {
        return onPlayerAchievementCompleted;
    }

    @CheckReturnValue
    @NotNull
    public EventConfiguration getOnPlayerKick() {
        return onPlayerKick;
    }

    @CheckReturnValue
    @NotNull
    public EventConfiguration getOnPlayerRespawn() {
        return onPlayerRespawn;
    }

    @CheckReturnValue
    @NotNull
    public EventConfiguration getOnServerStartup() {
        return onServerStartup;
    }

    @CheckReturnValue
    @NotNull
    public EventConfiguration getOnGracefulShutdown() {
        return onGracefulShutdown;
    }

}
