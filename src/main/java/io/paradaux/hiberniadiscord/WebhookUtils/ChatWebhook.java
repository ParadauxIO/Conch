package io.paradaux.hiberniadiscord.WebhookUtils;

import club.minnced.discord.webhook.send.WebhookMessage;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;

public class ChatWebhook {

    WebhookMessage chatWebhook;
    String userName;
    String avatarUrl;
    String messageContent;


    public ChatWebhook(String userName, String avatarUrl, String messageContent) {
        this.userName = userName;
        this.avatarUrl = avatarUrl;
        this.messageContent = messageContent;

        chatWebhook = new WebhookMessageBuilder()
                .setUsername(this.userName)
                .setAvatarUrl(this.avatarUrl)
                .setContent(this.messageContent)
                .build();

    }

    public WebhookMessage getWebhookMessage() {
        return chatWebhook;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }


}
