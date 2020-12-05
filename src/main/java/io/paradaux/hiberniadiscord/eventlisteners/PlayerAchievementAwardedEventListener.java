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
import io.paradaux.hiberniadiscord.webhookutils.ChatWebhook;
import io.paradaux.hiberniadiscord.api.ConfigurationCache;
import io.paradaux.hiberniadiscord.api.EventUtils;
import io.paradaux.hiberniadiscord.api.PlaceholderAPIWrapper;
import org.bukkit.Achievement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;

/*
*
*  Author's Note:
*  Best case scenario, I wouldn't have to use the deprecated PlayerAchievementAwardedEvent,
*  but PlayerAdvancementDoneEvent simply doesn't give me the achievement name, making it
*  impossible to do what I need to, which is send a webhook with the name of the
*  achievement to discord.
*
* */

@SuppressWarnings("deprecation")
public class PlayerAchievementAwardedEventListener extends WebhookListener {

    ConfigurationCache config = HiberniaDiscord.getConfigurationCache();
    PlaceholderAPIWrapper papi = new PlaceholderAPIWrapper();
    Achievement achievement;
    Player player;

    @EventHandler
    public void listener(PlayerAchievementAwardedEvent event) {
        achievement = event.getAchievement();
        player = event.getPlayer();

        // Stop if disabled
        if (!config.isAdvancementCompletedEnabled()) {
            return;
        }

        HiberniaDiscord.newChain().async(() -> {
            // Parse Username Placeholders
            String userName = EventUtils.parsePlaceholders(config, player,
                    config.getAdvancementCompletedUsernameFormat());

            // Parse Message Placeholders
            String messageContent = EventUtils.parsePlaceholders(config, player,
                    config.getAdvancementCompletedMessageFormat());

            // Inject achievement name
            messageContent = messageContent.replace("%achievementName%", achievement.name());


            // Parse Avatar Url Placeholders
            String avatarUrl = EventUtils.parsePlaceholders(config, player,
                    config.getAdvancementCompletedAvatarUrl());

            // If placeholder api is installed, parse papi placeholders.
            if (papi.isPresent()) {
                userName = papi.withPlaceholders(player, userName);
                messageContent = papi.withPlaceholders(player, messageContent);
            }

            // Sanitise Message, remove @everyone, @here and replace empty messages with a
            // zero-width space.
            messageContent = EventUtils.sanistiseMessage(messageContent);

            // Send the webhook
            new ChatWebhook(userName, avatarUrl, messageContent).sendWebhook();
        }).execute();

    }
}
