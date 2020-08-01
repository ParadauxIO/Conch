/*
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 */

package io.paradaux.hiberniadiscord.EventListeners;

import io.paradaux.hiberniadiscord.HiberniaDiscord;
import io.paradaux.hiberniadiscord.WebhookUtils.ChatWebhook;
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
*  Best case scenario, I wouldn't have to use the deprecated PlayerAchievementAwardedEvent, but PlayerAdvancementDoneEvent
*  simply doesn't give me the achievement name, making it impossible to do what I need to, which is send a webhook with the name of the
*  achievement to discord.
*
* */

public class PlayerAchievementAwardedEventListener implements Listener {

    ConfigurationCache config = HiberniaDiscord.getConfigurationCache();
    PlaceholderAPIWrapper papi = new PlaceholderAPIWrapper();
    Achievement achievement;
    Player player;

    @EventHandler
    public void Listener(PlayerAchievementAwardedEvent event) {
        achievement = event.getAchievement();
        player = event.getPlayer();

        // Stop if disabled
        if (!config.isAdvancementCompletedEnabled()) return;

        // Parse Username Placeholders
        String userName = EventUtils.parsePlaceholders(config, player, config.getAdvancementCompletedUsernameFormat());

        // Parse Message Placeholders
        String messageContent = EventUtils.parsePlaceholders(config, player, config.getAdvancementCompletedMessageFormat());
        messageContent = messageContent.replace("%achievementName%", achievement.name()); // Inject achievement name

        // Parse Avatar Url Placeholders
        String avatarUrl = EventUtils.parsePlaceholders(config, player, config.getAdvancementCompletedAvatarUrl());

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
