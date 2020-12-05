/*
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 */

package io.paradaux.hiberniadiscord.webhookutils;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookMessage;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import io.paradaux.hiberniadiscord.HiberniaDiscord;

public class GenericWebhook {

    WebhookClient client;
    WebhookClientBuilder clientBuilder;
    WebhookMessageBuilder messageBuilder;

    String webhookUrl;
    String webhookUserName;
    String webhookAvatarUrl;
    String webhookMessageContent;

    public GenericWebhook(String webhookUrl, String webhookUserName, String webhookAvatarUrl, String webhookMessageContent) {
        this.webhookUrl = webhookUrl;
        this.webhookUserName = webhookUserName;
        this.webhookAvatarUrl = webhookAvatarUrl;
        this.webhookMessageContent = webhookMessageContent;

        client = createClient(webhookUrl);
        messageBuilder = new WebhookMessageBuilder();
    }

    public GenericWebhook(String webhookUserName, String webhookAvatarUrl, String webhookMessageContent) {
        this.webhookUrl = HiberniaDiscord.getConfigurationCache().getDiscordWebhookUrl();
        this.webhookUserName = webhookUserName;
        this.webhookAvatarUrl = webhookAvatarUrl;
        this.webhookMessageContent = webhookMessageContent;

        client = createClient(webhookUrl);
        messageBuilder = new WebhookMessageBuilder();
    }


    public WebhookClient createClient(String webhookUrl) {
        clientBuilder = new WebhookClientBuilder(webhookUrl);

        clientBuilder.setThreadFactory((job) -> {
            Thread thread = new Thread(job);
            thread.setName("hiberniadiscord");
            thread.setDaemon(true);
            return thread;
        });

        clientBuilder.setWait(true);
        return clientBuilder.build();

    }

    public WebhookClient getClient() {
        return client;
    }

    public void sendWebhook() {

        // Prevent empty messages being passed to the discord API
        if (webhookMessageContent.equals("")) {
            // Zero Width space in double quotes.
            webhookMessageContent = "\u200B";
        }

        try {
            getClient().send(createMessage()).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public WebhookMessage createMessage() {
        return messageBuilder.setUsername(webhookUserName)
                .setAvatarUrl(webhookAvatarUrl)
                .setContent(webhookMessageContent)
                .build();
    }

}
