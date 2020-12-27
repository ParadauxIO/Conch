/*
 * Copyright (c) 2020, Rían Errity. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 3 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 3 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 3 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Rían Errity <rian@paradaux.io> or visit https://paradaux.io
 * if you need additional information or have any questions.
 * See LICENSE.md for more details.
 */

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookMessage;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import org.slf4j.Logger;

public class GenericWebhook {



    private static WebhookClient client;
    private static String webhookUrl;

    WebhookMessageBuilder messageBuilder = new WebhookMessageBuilder();

    Logger logger;

    String webhookUserName;
    String webhookAvatarUrl;
    String webhookMessageContent;

    /**
     * Creates an instance of GenericWebhook assuming the client has already been created.
     * */
    public GenericWebhook(String webhookUserName, String webhookAvatarUrl, String webhookMessageContent) {

        if (client == null) {
            logger.error("Failed to send a discord message.");
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

        // Prevent empty messages being passed to the discord io.paradaux.hiberniadiscord.common.API
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

    /**
     * Returns whether or not the provided webhook URL is actually a webhook URL.
     * */




}
