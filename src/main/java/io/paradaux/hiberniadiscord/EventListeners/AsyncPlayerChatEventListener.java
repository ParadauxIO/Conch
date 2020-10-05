/*
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 */

package io.paradaux.hiberniadiscord.EventListeners;

import io.paradaux.hiberniadiscord.HiberniaDiscord;
import io.paradaux.hiberniadiscord.WebhookUtils.ChatWebhook;
import io.paradaux.hiberniadiscord.api.ConfigurationCache;
import io.paradaux.hiberniadiscord.api.EventUtils;
import io.paradaux.hiberniadiscord.api.PlaceholderAPIWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatEventListener implements Listener {

    ConfigurationCache config = HiberniaDiscord.getConfigurationCache();
    PlaceholderAPIWrapper papi = new PlaceholderAPIWrapper();
    Player player;

    @EventHandler(priority = EventPriority.LOWEST)
    public void Listener(AsyncPlayerChatEvent event) {

        // Stop if disabled
        if (!config.isChatMessageEnabled()) return;

        player = event.getPlayer();

        // Parse Username Placeholders
        String userName = EventUtils.parsePlaceholders(config, player, config.getChatMessageUsernameFormat());

        // Parse Message Placeholders
        String messageContent = EventUtils.parsePlaceholders(config, player, config.getChatMessageMessageFormat());
        messageContent = messageContent.replace("%messageContent%", event.getMessage()); // Inject chat message

        // Parse Avatar Url Placeholders
        String avatarUrl = EventUtils.parsePlaceholders(config, player, config.getChatMessageAvatarUrl());

        // If placeholder api is installed, parse papi placeholders.
        if (papi.isPresent()) {
            userName = papi.withPlaceholders(player, userName);
            messageContent = papi.withPlaceholders(player, messageContent);
        }

        // Sanitise Message, remove @everyone, @here and replace empty messages with a zero-width space.
        messageContent = EventUtils.sanistiseMessage(messageContent);

        // Send the webhook
        new ChatWebhook(userName, avatarUrl, messageContent).sendWebhook();

    }

}
