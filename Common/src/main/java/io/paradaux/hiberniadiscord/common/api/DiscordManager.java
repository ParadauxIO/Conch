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

package io.paradaux.hiberniadiscord.common.api;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookMessage;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import org.slf4j.Logger;

import javax.annotation.CheckReturnValue;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

public class DiscordManager {

    private static final Pattern WEBHOOK_PATTERN = Pattern
            .compile("(?:https?://)?(?:\\w+\\.)?discord(?:app)?\\.com/api(?:/v\\d+)?/webhooks/(\\d+)/([\\w-]+)(?:/(?:\\w+)?)?");
    private static final String ZERO_WIDTH_SPACE = "\u200B";
    private static boolean sanitiseMentions = true;

    private static WebhookClient client;
    private static Logger logger;

    /**
     * Setup the manager.
     * @return Did the process complete successfully?
     * */
    public static boolean initialise(String webhookUrl, boolean sanitiseMentions, Logger logger) {
        DiscordManager.sanitiseMentions = sanitiseMentions;
        DiscordManager.logger = logger;

        if (!isValidWebhook(webhookUrl)) {
            logger.error("Your webhook URL is invalid or not set.");
            return false;
        }


        WebhookClientBuilder builder = new WebhookClientBuilder(webhookUrl);
        builder.setThreadFactory((job) -> {
            Thread thread = new Thread(job);
            thread.setName("HiberniaDiscord");
            thread.setDaemon(true);
            return thread;
        });
        builder.setWait(true);
        client = builder.build();

        return true;
    }

    /**
     * Sends a discord message to the channel specified by the webhook provided in the intialising method.
     * */
    public static void sendDiscordMessage(WebhookMessage message) {
        try {
            client.send(message).get();
        } catch (ExecutionException | InterruptedException exception) {
            logger.error("An error occurred", exception);
        }
    }

    /**
     * Parses the username, avatarUrl and message content to construct a WebhookMessage Object which is then sent to discord.
     * */
    public static void sendDiscordMessage(String username, String avatarUrl, String message) {
        if (message.isEmpty()) {
            message = String.valueOf(ZERO_WIDTH_SPACE);
        }

        if (sanitiseMentions) {
            if (message.contains("@everyone") || message.contains("@here")) {
                message = message.replace("@everyone", ZERO_WIDTH_SPACE)
                                 .replace("@here", ZERO_WIDTH_SPACE);
            }
        }

        WebhookMessageBuilder builder = new WebhookMessageBuilder();
        builder.setUsername(username);
        builder.setAvatarUrl(avatarUrl);
        builder.setContent(message);

        sendDiscordMessage(builder.build());
    }

    @CheckReturnValue
    public static boolean isValidWebhook(String webhookUrl) {
        return WEBHOOK_PATTERN.matcher(webhookUrl).matches();
    }
}
