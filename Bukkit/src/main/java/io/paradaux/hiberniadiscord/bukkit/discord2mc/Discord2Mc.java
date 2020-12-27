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

package io.paradaux.hiberniadiscord.discord2mc;

import io.paradaux.hiberniadiscord.discord2mc.api.Discord2McConfigurationCache;
import io.paradaux.hiberniadiscord.discord2mc.listeners.MessageListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.bukkit.configuration.file.FileConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.security.auth.login.LoginException;

public class Discord2Mc {

    private static Discord2McConfigurationCache configurationCache;

    public static Discord2McConfigurationCache getConfigurationCache() {
        return configurationCache;
    }

    Logger logger = LoggerFactory.getLogger(getClass());

    String token;

    @Nullable
    JDA client;

    public Discord2Mc(FileConfiguration configuration) {
        configurationCache = new Discord2McConfigurationCache(configuration);

        token = configurationCache.getToken();

        JDABuilder builder = JDABuilder.createDefault(token);

        // Disable parts of the cache
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        // Enable the bulk delete event
        builder.setBulkDeleteSplittingEnabled(false);
        // Disable compression (not recommended)
        builder.setCompression(Compression.NONE);

        builder.addEventListeners(new MessageListener(configurationCache));

        if (token == null) {
            logger.warn("You have not set the token for your discord bot for discord2mc"
                    + " functionality. This has been disabled.");
            return;
        }

        if (!configurationCache.isEnabled()) {
            logger.warn("Discord2mc functionality has been disabled, if this is intentional no"
                    + " further action is required.");
            return;
        }


        try {
            client = builder.build();
        } catch(LoginException excpetion) {
            client = null;
            LoggerFactory.getLogger(getClass()).warn("FAILED TO LOGIN TO DISCORD USING TOKEN"
                    + " PROVIDED");
        }

    }

}
