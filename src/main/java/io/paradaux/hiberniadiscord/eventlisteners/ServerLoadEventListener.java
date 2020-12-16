/*
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 */

package io.paradaux.hiberniadiscord.eventlisteners;

import io.paradaux.hiberniadiscord.HiberniaDiscord;
import io.paradaux.hiberniadiscord.webhookutils.ChatWebhook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.server.ServerLoadEvent;

public class ServerLoadEventListener extends WebhookListener {

    ConfigurationCache config = HiberniaDiscord.getConfigurationCache();


    @EventHandler(priority = EventPriority.LOW)
    public void listener(ServerLoadEvent event) {

        // Stop if disabled
        if (!config.isServerStartupEnabled()) {
            return;
        }

        HiberniaDiscord.newChain().async(() -> {
            // Parse Username Placeholders
            String userName = EventUtils.parsePlaceholders(config,
                    config.getServerStartupUsernameFormat());

            // Parse Message Placeholders
            String messageContent = EventUtils.parsePlaceholders(config,
                    config.getServerStartupMessageFormat());

            String avatarUrl = config.getServerStartupAvatarUrl();

            // Sanitise Message, remove @everyone, @here and replace empty messages with
            // a zero-width space.
            messageContent = this.sanistiseMessage(messageContent);

            // Send the webhook
            new ChatWebhook(userName, avatarUrl, messageContent).sendWebhook();
        }).execute();
    }
}
