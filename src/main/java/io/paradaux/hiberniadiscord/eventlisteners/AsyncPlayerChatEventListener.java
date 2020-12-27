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

package io.paradaux.hiberniadiscord.eventlisteners;

import io.paradaux.hiberniadiscord.api.PlaceholderWrapper;
import io.paradaux.hiberniadiscord.controllers.TaskController;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatEventListener extends WebhookListener {

    Player player;



    /**
     * Event called when messages are sent in chat, this is usually the only one people want.
     * */
    @EventHandler(priority = EventPriority.MONITOR)
    public void listener(AsyncPlayerChatEvent event) {

        // Stop if disabled
        if (!configuration.isChatMessageEnabled()) {
            return;
        }

        // Send the webhook
        TaskController.newChain().async(() -> {
            player = event.getPlayer();

            // Parse Username Placeholders
            this.webhookUserName = parsePlaceholders(player, configuration
                    .getChatMessageUsernameFormat());

            // Parse Message Placeholders
            this.webhookMessageContent = parsePlaceholders(player, configuration
                    .getChatMessageMessageFormat());

            // Inject chat message
            webhookMessageContent = webhookMessageContent.replace("%messageContent%",
                    event.getMessage());

            // If placeholder api is installed, parse papi placeholders.
            if (PlaceholderWrapper.isPresent()) {
                this.webhookUserName = PlaceholderWrapper.withPlaceholders(player, this.webhookUserName);
                webhookMessageContent = PlaceholderWrapper.withPlaceholders(player, webhookMessageContent);
            }

            // Sanitise Message, remove @everyone, @here and replace empty messages with a
            // zero-width space.
            webhookMessageContent = sanistiseMessage(webhookMessageContent);

            // Parse Avatar Url Placeholders
            this.webhookAvatarUrl = parsePlaceholders(player,
                    configuration.getChatMessageAvatarUrl());




            // Global Message-only support in 3.0.1
            if (!configuration.isMessagePrefixDisabled()) {
                if (!webhookMessageContent.startsWith(configuration.getMessagePrefix())) {
                    return;
                }

                // Thanks Jake! Such a silly mistake to make (RE 5/12/20)
                webhookMessageContent = webhookMessageContent.replace(configuration.getMessagePrefix(), "");
            }

            sendWebhook();
        }).execute();


    }

}
