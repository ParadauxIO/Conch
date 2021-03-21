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

import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

/**
 * Singleton references to
 *
 * */
public class ConfigurationUtil {

    private static boolean debug;

    private static CachedServerSettings generalSettings = null;
    private static CachedBotSettings botSettings = null;
    private static CachedEventSettings eventSettings = null;

    private ConfigurationUtil() {
        // No instantiation of utility classes, dang it!
    }
    
    @CheckReturnValue
    @Nullable
    public static CachedServerSettings getGeneralSettings() {
        return generalSettings;
    }

    @CheckReturnValue
    @Nullable
    public static CachedBotSettings getBotSettings() {
        return botSettings;
    }

    @CheckReturnValue
    @Nullable
    public static CachedEventSettings getEventSettings() {
        return eventSettings;
    }

    @CheckReturnValue
    public static boolean isDebug() {
        return debug;
    }

    /**
     * <p>(Re)Sets the configuration file singletons.</p>
     *
     * <p>In the event of a reload we want to be able to re-set the instances.</p>>
     * @param generalSettings settings.conf cached values.
     * @param botSettings bot-settings.conf cached values.
     * @param eventSettings event-settings.conf cached values.
     * */
    public static void loadConfigurationValues(CachedServerSettings generalSettings,
                                               CachedEventSettings eventSettings,
                                               CachedBotSettings botSettings) {
        ConfigurationUtil.generalSettings = generalSettings;
        ConfigurationUtil.botSettings = botSettings;
        ConfigurationUtil.eventSettings = eventSettings;
        ConfigurationUtil.debug = generalSettings.isDebug();
    }

}
