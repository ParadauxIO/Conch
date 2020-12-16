/*
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 */

package io.paradaux.hiberniadiscord.webhookutils;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookMessage;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import io.paradaux.hiberniadiscord.controllers.LogController;

public class GenericWebhook {

    private static WebhookClient client;
    private static String webhookUrl;

    WebhookMessageBuilder messageBuilder = new WebhookMessageBuilder();

    String webhookUserName;
    String webhookAvatarUrl;
    String webhookMessageContent;

    /**
     * Creates an instance of GenericWebhook assuming the client has already been created.
     * */
    public GenericWebhook(String webhookUserName, String webhookAvatarUrl, String webhookMessageContent) {
        if (client == null) {
            LogController.getLogger().error("Failed to send a discord message.");
            return;
        }

        this.webhookUserName = webhookUserName;
        this.webhookAvatarUrl = webhookAvatarUrl;
        this.webhookMessageContent = webhookMessageContent;
    }

    /**
     * Factory for creating the discord connection.
     */
    public static void createClient() {
        WebhookClientBuilder clientBuilder = new WebhookClientBuilder(webhookUrl);

        clientBuilder.setThreadFactory((job) -> {
            Thread thread = new Thread(job);
            thread.setName("hiberniadiscord");
            thread.setDaemon(true);
            return thread;
        });

        clientBuilder.setWait(true);
        client = clientBuilder.build();
    }

    public WebhookClient getClient() {
        return client;
    }

    /**
     * Sends the webhook to discord.
     */
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

    /**
     * Creates a webhook message from the set parameters.
     * @return WebhookMessage Object which represents the chat message.
     * */
    public WebhookMessage createMessage() {
        return messageBuilder.setUsername(webhookUserName)
                .setAvatarUrl(webhookAvatarUrl)
                .setContent(webhookMessageContent)
                .build();
    }

    public static void setWebhookUrl(String newWebhookUrl) {
        webhookUrl = newWebhookUrl;
    }

}
