package io.paradaux.hiberniadiscord.WebhookUtils;

import club.minnced.discord.webhook.WebhookClientBuilder;
import io.paradaux.hiberniadiscord.HiberniaDiscord;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;


public class WebhookClient {
    WebhookClientBuilder builder;
    club.minnced.discord.webhook.WebhookClient client;
    String webhookURL;

    public WebhookClient() {

        String webhookURL = "https://discordapp.com/api/webhooks/713452488342372462/1t7c4DY5c7IFZp_jgXMGyRjKHY4A3EN4kIG4Uz5IfUVOGyrIyn5K6138i0hkY3UbGf-K";

        if (isValidURL(webhookURL)) {
            builder = new WebhookClientBuilder(webhookURL);
        } else {
            HiberniaDiscord.getMainLogger().log(Level.SEVERE, "Invalid Webhook supplied. Please check the configuration file, is it valid? \n If this is the first time you're running the plugin please configure the webhook field.");
            return;
        }

        builder.setThreadFactory((job) -> {
            Thread thread = new Thread(job);
            thread.setName("Chat Webhook");
            thread.setDaemon(true);
            return thread;
        });

        builder.setWait(true);
        client = builder.build();
    }

    public WebhookClientBuilder getBuilder() {
        return builder;
    }

    public void setBuilder(WebhookClientBuilder builder) {
        this.builder = builder;
    }

    public String getWebhookURL() {
        return webhookURL;
    }

    public void setWebhookURL(String webhookURL) {
        this.webhookURL = webhookURL;
    }

    boolean isValidURL(String url) {
        try {
            new URI(url).parseServerAuthority();
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }

    public club.minnced.discord.webhook.WebhookClient getWebhookClient() {
        return client;
    }
}
