package io.paradaux.hiberniadiscord.WebhookUtils;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessage;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import io.paradaux.hiberniadiscord.HiberniaDiscord;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;

public class GenericWebhook {

    WebhookClient client;
    WebhookClientBuilder builder;
    WebhookMessageBuilder messageBuilder;

    final private String userName;
    final private String avatarUrl;

    public GenericWebhook(String webhookUrl, String avatarUrl, String userName) {
        this.userName = userName;
        this.avatarUrl = avatarUrl;

        client = createClient(webhookUrl);
        messageBuilder = new WebhookMessageBuilder();
    }

    public WebhookClient createClient(String webhookUrl) {

        if (isValidURL(webhookUrl)) {
            builder = new WebhookClientBuilder(webhookUrl);
        } else {
            HiberniaDiscord.getMainLogger().log(Level.SEVERE, "Invalid Webhook supplied. Please check the configuration file, is it valid? \n If this is the first time you're running the plugin please configure the webhook field.");
            return null;
        }

        builder.setThreadFactory((job) -> {
            Thread thread = new Thread(job);
            thread.setName("hiberniareport");
            thread.setDaemon(true);
            return thread;
        });

        builder.setWait(true);
        return builder.build();
    }

    private boolean isValidURL(String url) {
        try {
            new URI(url).parseServerAuthority();
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }

    public WebhookClient getClient() {
        return client;
    }

    public WebhookEmbed createEmbed() {
        return new WebhookEmbedBuilder().build();
    }

    public void sendWebhook(WebhookMessage message) {
        try {
            getClient().send(message).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public WebhookMessage newMessage(WebhookEmbed embed) {
        return messageBuilder.setUsername(userName).setAvatarUrl(avatarUrl).addEmbeds(embed).build();
    }

    public void execute() {}

}
