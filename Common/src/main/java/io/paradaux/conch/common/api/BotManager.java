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

package io.paradaux.conch.common.api;

import co.aikar.taskchain.TaskChainFactory;
import io.paradaux.conch.common.api.events.DiscordMessageReceivedEvent;
import io.paradaux.conch.common.bot.DiscordMessageListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;

public class BotManager {

    private static JDA client;
    private static Logger logger;
    private static TaskChainFactory taskChainFactory;


    /**
     * Instances of DiscordMessageReceivedListener which will respond when a disocrd message
     * is received from the discord bot.
     * */
    private static final List<DiscordMessageReceivedEvent> eventListeners = new ArrayList<>();

    /**
     * Sets up the manager.
     * */
    public static void initialise(String token, Logger logger, List<String> monitoredChannels,
                                  TaskChainFactory taskChainFactory, String messageFormat, boolean ignoreBots) {
        BotManager.logger = logger;

        JDABuilder builder = JDABuilder.createDefault(token);

        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);

        builder.setBulkDeleteSplittingEnabled(false);
        builder.setCompression(Compression.ZLIB);

        builder.addEventListeners(new DiscordMessageListener(monitoredChannels, taskChainFactory, messageFormat, ignoreBots));

        if (token == null) {
            logger.warn("You have not set the token for your discord bot for discord2mc"
                    + " functionality. This has been disabled.");
            return;
        }

        try {
            client = builder.build();
        } catch(LoginException exception) {
            client = null;
            logger.warn("FAILED TO LOGIN TO DISCORD USING TOKEN"
                    + " PROVIDED");
        }
    }

}
