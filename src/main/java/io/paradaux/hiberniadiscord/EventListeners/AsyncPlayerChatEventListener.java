package io.paradaux.hiberniadiscord.EventListeners;

import io.paradaux.hiberniadiscord.WebhookUtils.ChatWebhook;
import io.paradaux.hiberniadiscord.api.EventUtils;
import io.paradaux.hiberniadiscord.api.PlaceholderAPIWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatEventListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void Listener(AsyncPlayerChatEvent event) {

        PlaceholderAPIWrapper placeholderapi = new PlaceholderAPIWrapper();

        Player player = event.getPlayer();

        String userName = EventUtils.getColourlessName(player);
        String avatarUrl = EventUtils.createAvatarUrl(player.getUniqueId());
        String messageContent = EventUtils.removeColor(event.getMessage());

        if (placeholderapi.isPresent()) {
            userName = placeholderapi.withPlaceholders(player, userName);
            messageContent = placeholderapi.withPlaceholders(player, messageContent);
        }

        new ChatWebhook(userName, avatarUrl, messageContent).sendWebhook();

    }

}
