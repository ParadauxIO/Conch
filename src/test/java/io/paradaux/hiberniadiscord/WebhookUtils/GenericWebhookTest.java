package io.paradaux.hiberniadiscord.WebhookUtils;

import org.junit.Before;
import org.junit.Test;

public class GenericWebhookTest {

    GenericWebhook webhook;

    String webhookUrl = "";
    String webhookUserName = "HiberniaDiscord Unit Test";
    String webhookAvatarUrl = "https://cdn.paradaux.io/static/plugin-branding/hiberniadiscord/hibernia-discord.png";
    String webhookMessageContent = "This is a test";

    @Before
    public void setUp() {
        webhook = new GenericWebhook(webhookUrl, webhookUserName, webhookAvatarUrl, webhookMessageContent);
    }

    @Test
    public void sendWebhookTest() {
        webhook.sendWebhook();
    }


}
