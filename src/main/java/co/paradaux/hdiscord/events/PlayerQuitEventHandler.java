package co.paradaux.hdiscord.events;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import co.paradaux.hdiscord.core.CachedConfigValues;
import co.paradaux.hdiscord.hooks.PlaceholderAPIHook;
import ninja.egg82.service.ServiceLocator;
import ninja.egg82.service.ServiceNotFoundException;
import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerQuitEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.function.Consumer;

public class PlayerQuitEventHandler implements Consumer<PlayerQuitEvent> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void accept(PlayerQuitEvent event) {
        Optional<PlaceholderAPIHook> placeholderapi;
        Optional<WebhookClient> discordClient;
        CachedConfigValues cachedConfig;

        try {
            cachedConfig = ServiceLocator.get(CachedConfigValues.class);
        } catch (InstantiationException | IllegalAccessException | ServiceNotFoundException ex) {
            logger.error(ex.getMessage(), ex);
            return;
        }

        if(cachedConfig.getLeaveEventMsg() == "") { return; }

        try {
            placeholderapi = ServiceLocator.getOptional(PlaceholderAPIHook.class);
        } catch(InstantiationException | IllegalAccessException ex) {
            logger.error(ex.getMessage(), ex);
            placeholderapi = Optional.empty();
        }

        try {
            discordClient = ServiceLocator.getOptional(WebhookClient.class);
        } catch(InstantiationException | IllegalAccessException ex) {
            logger.error(ex.getMessage(), ex);
            discordClient = Optional.empty();
        }

        if (!discordClient.isPresent()) {
            return;
        }

        String strippedDisplayName =  ChatColor.stripColor(cachedConfig.getLeaveEventMsg().replace("%player%", event.getPlayer().getDisplayName()));

        WebhookMessageBuilder messageBuilder = new WebhookMessageBuilder();
        messageBuilder.setAvatarUrl(cachedConfig.getServerIcon());
        messageBuilder.setContent("\u200B");

        if (placeholderapi.isPresent()) {
            String stipppedPlaceholderAPIName = ChatColor.stripColor(placeholderapi.get().withPlaceholders(event.getPlayer(), cachedConfig.getLeaveEventMsg().replace("%player%", "%player_displayname%")));
            messageBuilder.setUsername(stipppedPlaceholderAPIName);
        } else {
            messageBuilder.setUsername(strippedDisplayName);
        }

        if (cachedConfig.getDebug()) {
            if (!event.getPlayer().getName().equals(strippedDisplayName)) {
                logger.info("Sending message from " + event.getPlayer().getName() + " (" + strippedDisplayName + ")..");
            } else {
                logger.info("Sending message from " + event.getPlayer().getName() + "..");
            }
        }

        discordClient.get().send(messageBuilder.build()).thenRun(() -> {
            if (cachedConfig.getDebug()) {
                if (!event.getPlayer().getName().equals(strippedDisplayName)) {
                    logger.info("Successfully sent message from " + event.getPlayer().getName() + " (" + strippedDisplayName + ")");
                } else {
                    logger.info("Successfully sent message from " + event.getPlayer().getName());
                }
            }
        });
    }
}
