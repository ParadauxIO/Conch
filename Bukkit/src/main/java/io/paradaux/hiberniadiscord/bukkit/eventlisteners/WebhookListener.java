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

package io.paradaux.hiberniadiscord.eventlisteners;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookMessage;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import io.paradaux.hiberniadiscord.models.PluginConfiguration;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import javax.annotation.Nullable;
import java.util.concurrent.ExecutionException;

public abstract class WebhookListener implements Listener {

    protected static PluginConfiguration configuration;

    private static WebhookClient client;

    String webhookUserName;
    String webhookAvatarUrl;
    String webhookMessageContent;

    @Nullable
    protected String parsePlaceholders(Player player, String str) {
        return parsePlaceholders((OfflinePlayer) player, str);
    }

    @Nullable
    protected String parsePlaceholders(OfflinePlayer player, String str) {
        if (player == null || player.getName() == null) {
            return null;
        }

        return str.replace("%playername%", player.getName())
                .replace("%playeruuid%", player.getUniqueId().toString())
                .replace("%servername%", configuration.getServerName())
                .replace("%avatarapi%", parseAvatarUrl(player, configuration.getAvatarApi()));
    }

    protected String parseAvatarUrl(Player player, String str) {
        return parseAvatarUrl((OfflinePlayer) player, str);
    }

    protected String parseAvatarUrl(OfflinePlayer player, String str) {
        return str.replace("%playeruuid%", player.getUniqueId().toString());
    }

    protected String sanistiseMessage(String str) {
        str = str.replace("@everyone", "")
                .replace("@here", "");

        if (str.equals("")) {
            return "\u200B"; // Zero-Width space
        }

        return str;
    }

    /**
     * Creates a WebhookClient which communicates with discord via HTTP.
     */
    public static WebhookClient createClient() {
        WebhookClientBuilder clientBuilder =
                new WebhookClientBuilder(configuration.getDiscordWebhookUrl());

        clientBuilder.setThreadFactory((job) -> {
            Thread thread = new Thread(job);
            thread.setName("hiberniadiscord");
            thread.setDaemon(true);
            return thread;
        });

        clientBuilder.setWait(true);
        return clientBuilder.build();
    }

    protected WebhookMessage createMessage() {
        return new WebhookMessageBuilder()
                .setUsername(webhookUserName)
                .setAvatarUrl(webhookAvatarUrl)
                .setContent(webhookMessageContent)
                .build();
    }

    protected void sendWebhook() {
        if (webhookMessageContent.equals("")) {
            webhookMessageContent = "\u200B";
        }

        try {
            client.send(createMessage()).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
