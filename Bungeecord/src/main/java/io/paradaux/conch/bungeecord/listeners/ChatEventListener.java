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

package io.paradaux.conch.bungeecord.listeners;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import org.slf4j.Logger;

import javax.annotation.Nullable;

public class ChatEventListener extends GenericListener {

    Logger logger;
    String messagePrefix;
    boolean debug;

    public ChatEventListener(String avatarApiUrl, String userNameFormat, String serverName,
                             @Nullable String messagePrefix, Logger logger, boolean debug) {
        super(avatarApiUrl, userNameFormat, serverName);
        this.messagePrefix = messagePrefix;
        this.logger = logger;
        this.debug = debug;
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(ChatEvent event) {

        if (!(event.getSender() instanceof ProxiedPlayer)) {
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        String userName = parsePlaceholders(player, getUserNameFormat());
        String messageContent = parsePlaceholders(player, event.getMessage());

        // PlaceholderAPI doesn't exist for Bungeecord

        if (userName == null || messageContent == null) {
            logger.error("Something went wrong. ChatEvent null.");
            return;
        }

        // Only send the message if it starts with the configured prefix (if applicable)
        if (messagePrefix != null) {
            if (!messageContent.startsWith(messagePrefix)) {
                return;
            }

            // Remove the prefix from the message.
            messageContent = messagePrefix.replace(messagePrefix, "");
        }

        if (debug) {
            logger.info("{} has sent a message in chat which will be relayed to the discord webhook.", player.getName());
        }

//        DiscordManager.sendDiscordMessage(userName, parseAvatarApi(player), messageContent);

    }

}
