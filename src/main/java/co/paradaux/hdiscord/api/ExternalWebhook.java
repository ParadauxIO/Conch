package co.paradaux.hdiscord.api;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import ninja.egg82.service.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class ExternalWebhook {

    private String avatarUrl;
    private String userName;
    private String messageContent;

    private final Logger logger = LoggerFactory.getLogger(getClass());
    Optional<WebhookClient> discordClient;

    public ExternalWebhook () {}

    public void sendWebhook(String avatarUrl, String userName, String messageContent) {

        try {
            discordClient = ServiceLocator.getOptional(WebhookClient.class);
        } catch(InstantiationException | IllegalAccessException ex) {
            logger.error(ex.getMessage(), ex);
            discordClient = Optional.empty();
        }

        WebhookMessageBuilder messageBuilder = new WebhookMessageBuilder();

        messageBuilder.setAvatarUrl(avatarUrl);

        if (messageContent == null) {
            messageBuilder.setContent("\u200B");
        } else {
            messageBuilder.setContent(messageContent);
        }
        messageBuilder.setUsername(userName);

        discordClient.get().send(messageBuilder.build());
    }


}
