package io.paradaux.hiberniadiscord.WebhookUtils;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookMessage;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import io.paradaux.hiberniadiscord.HiberniaDiscord;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;

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
        this.webhookUrl = HiberniaDiscord.getConfigurationCache().getDiscord_webhookURL();
        this.webhookUserName = webhookUserName;
        this.webhookAvatarUrl = webhookAvatarUrl;
        this.webhookMessageContent = webhookMessageContent;

        client = createClient(webhookUrl);
        messageBuilder = new WebhookMessageBuilder();
    }


    public WebhookClient createClient(String webhookUrl) {

        if (isValidURL(webhookUrl)) {
            System.out.println(webhookUrl);
            clientBuilder = new WebhookClientBuilder(webhookUrl);
        } else {
            HiberniaDiscord.getMainLogger().log(Level.SEVERE, "Invalid Webhook supplied. Please check the configuration file, is it valid? \n If this is the first time you're running the plugin please configure the webhook field.");
            return null;
        }

        clientBuilder.setThreadFactory((job) -> {
            Thread thread = new Thread(job);
            thread.setName("hiberniadiscord");
            thread.setDaemon(true);
            return thread;
        });

        clientBuilder.setWait(true);
        return clientBuilder.build();

    }

    private boolean isValidURL(String url) {
        try {
            new URI(url).parseServerAuthority();
        } catch (URISyntaxException e) {
            return false;
        }
        return true;
    }

    public WebhookClient getClient() {
        return client;
    }

    public void sendWebhook() {
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
