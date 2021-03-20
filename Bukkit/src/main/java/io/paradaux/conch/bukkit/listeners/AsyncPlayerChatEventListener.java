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

package io.paradaux.conch.bukkit.listeners;

import io.paradaux.conch.bukkit.api.PlaceholderWrapper;
import io.paradaux.conch.common.api.DiscordManager;
import io.paradaux.conch.common.api.I18NLogger;
import io.paradaux.conch.common.api.config.EventConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.annotation.Nullable;

import static io.paradaux.conch.bukkit.api.PlaceholderWrapper.withPlaceholders;

public class AsyncPlayerChatEventListener extends GenericListener {

    private final DiscordManager discord;

    private final boolean debug;
    private final String serverName;
    private final String messagePrefix; // TODO implement

    private final String avatarApiFormat;
    private final String userNameFormat;
    private final String messageFormat;

    public AsyncPlayerChatEventListener(DiscordManager discord, boolean debug, String serverName, String messagePrefix, EventConfiguration config) {
        this.discord = discord;
        this.debug = debug;
        this.serverName = serverName;
        this.messagePrefix = messagePrefix;
        this.avatarApiFormat = config.getWebhookAvatarFormat();
        this.userNameFormat = config.getWebhookUsernameFormat();
        this.messageFormat = config.getWebhookMessageFormat();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();

        String discordAvatarUrl = parsePlaceholders(player, serverName, avatarApiFormat, avatarApiFormat);
        String discordUserName = parsePlaceholders(player, serverName, avatarApiFormat,userNameFormat);

        String messageContent = parsePlaceholders(player, serverName, avatarApiFormat, event.getMessage());

        // Only send the message if it starts with the configured prefix (if applicable)
        if (messagePrefix != null) {
            if (!messageContent.startsWith(messagePrefix)) {
                return;
            }

            // Remove the prefix from the message.
            messageContent = messagePrefix.replace(messagePrefix, "");
        }

        if (debug) {
            // TODO log
//            logger.info("{} has sent a message in chat which will be relayed to the discord webhook.", player.getName());
        }

        I18NLogger.rawInfo(parseAvatarApi(player, avatarApiFormat));

        discord.sendDiscordMessage(discordUserName, discordAvatarUrl, messageContent);
    }

}
