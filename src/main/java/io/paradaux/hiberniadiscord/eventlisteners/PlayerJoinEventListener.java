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

import io.paradaux.hiberniadiscord.HiberniaDiscord;
import io.paradaux.hiberniadiscord.api.PlaceholderWrapper;
import io.paradaux.hiberniadiscord.controllers.TaskController;
import io.paradaux.hiberniadiscord.webhookutils.ChatWebhook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinEventListener extends WebhookListener {

    PlaceholderWrapper papi = new PlaceholderWrapper();
    Player player;

    @EventHandler(priority = EventPriority.LOWEST)
    public void listener(PlayerJoinEvent event) {

        // Stop if disabled
        if (!configuration.isPlayerJoinEnabled()) {
            return;
        }

        TaskController.newChain().async(() -> {
            player = event.getPlayer();

            // Parse Username Placeholders
            String userName = this.parsePlaceholders(player, configuration
                    .getPlayerJoinUsernameFormat());

            // Parse Message Placeholders
            String messageContent = this.parsePlaceholders(player, configuration
                    .getPlayerJoinMessageFormat());

            // Parse Avatar Url Placeholders
            String avatarUrl = this.parsePlaceholders(player, configuration
                    .getPlayerJoinAvatarUrl());

            // If placeholder api is installed, parse papi placeholders.
            if (papi.isPresent()) {
                userName = papi.withPlaceholders(player, userName);
                messageContent = papi.withPlaceholders(player, messageContent);
            }

            // Sanitise Message, remove @everyone, @here and replace empty messages with a
            // zero-width space.
            messageContent = this.sanistiseMessage(messageContent);

            // Send the webhook
            new ChatWebhook(userName, avatarUrl, messageContent).sendWebhook();
        }).execute();

    }
}
