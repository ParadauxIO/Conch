/*
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 */

package io.paradaux.hiberniadiscord.EventListeners;

import io.paradaux.hiberniadiscord.HiberniaDiscord;
import io.paradaux.hiberniadiscord.WebhookUtils.ChatWebhook;
import io.paradaux.hiberniadiscord.api.ConfigurationCache;
import io.paradaux.hiberniadiscord.api.EventUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;

public class ServerLoadEventListener implements Listener {

    ConfigurationCache config = HiberniaDiscord.getConfigurationCache();


    @EventHandler(priority = EventPriority.LOW)
    public void Listener (ServerLoadEvent event) {

        // Stop if diabled
        if (!config.isServerStartupEnabled()) return;

        // Parse Username Placeholders
        String userName = EventUtils.parsePlaceholders(config, config.getServerStartupUsernameFormat());

        // Parse Message Placeholders
        String messageContent = EventUtils.parsePlaceholders(config, config.getServerStartupMessageFormat());

        String avatarUrl = config.getServerStartupAvatarUrl();

        // Sanitise Message, remove @everyone, @here and replace empty messages with a zero-width space.
        messageContent = EventUtils.sanistiseMessage(messageContent);

        // Send the webhook
        new ChatWebhook(userName, avatarUrl, messageContent).sendWebhook();
    }
}
