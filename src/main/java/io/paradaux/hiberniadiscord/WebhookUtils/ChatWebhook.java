package io.paradaux.hiberniadiscord.WebhookUtils;

import club.minnced.discord.webhook.send.WebhookMessage;
import io.paradaux.hiberniadiscord.HiberniaDiscord;
import io.paradaux.hiberniadiscord.api.ConfigurationCache;

import java.util.UUID;

public class ChatWebhook {
    WebhookClient webhookClient = HiberniaDiscord.getWebhookClient();
    ConfigurationCache configurationCache = HiberniaDiscord.getConfigurationCache();

    WebhookMessage chatWebhook;
    String userName;
    String avatarUrl;
    UUID uuid;
    String messageContent;

    public ChatWebhook(WebhookMessage chatWebhook, String userName, String avatarUrl, UUID uuid, String messageContent) {
        this.userName = userName;
        this.uuid = uuid;
        this.avatarUrl = configurationCache.getAvatarApi().replace("%uuid%", uuid.toString());
        this.messageContent = messageContent;
    }
}
