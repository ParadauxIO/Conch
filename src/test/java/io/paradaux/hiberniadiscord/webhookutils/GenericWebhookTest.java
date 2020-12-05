package io.paradaux.hiberniadiscord.webhookutils;

import org.junit.Before;
import org.junit.Test;

public class GenericWebhookTest {

    GenericWebhook webhook;

    String webhookUrl = "https://discordapp.com/api/webhooks/763065395833602048/xUUX016wrPYPGWRJyfsGaDtwtxHJFrbWHrEfj4XMa5PvFT0jSc-kgcA9qF3ZP9cpH5Mv";
    String webhookUserName = "HiberniaDiscord Unit Test";
    String webhookAvatarUrl = "https://cdn.paradaux.io/static/plugin-branding/hiberniadiscord/hibernia-discord.png";
    String webhookMessageContent = "This is a test for 3.0.1";

    @Before
    public void setUp() {
        webhook = new GenericWebhook(webhookUrl, webhookUserName, webhookAvatarUrl, webhookMessageContent);
    }

    @Test
    public void sendWebhookTest() {
        webhook.sendWebhook();
    }


}
