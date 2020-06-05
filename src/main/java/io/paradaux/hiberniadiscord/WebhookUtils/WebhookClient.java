package io.paradaux.hiberniadiscord.WebhookUtils;

import club.minnced.discord.webhook.WebhookClientBuilder;
import io.paradaux.hiberniadiscord.HiberniaDiscord;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;


public class WebhookClient {
    WebhookClientBuilder builder;
    String webhookURL;

    public WebhookClient(String webhookURL) {
        if (isValidURL(webhookURL)) {
            builder = new WebhookClientBuilder(webhookURL);
        } else {
            HiberniaDiscord.getMainLogger().log(Level.SEVERE, "Invalid Webhook supplied. Please check the configuration file, is it valid? \n If this is the first time you're running the plugin please configure the webhook field.");
        }
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
}
