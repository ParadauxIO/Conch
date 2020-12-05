/*
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 */

package io.paradaux.hiberniadiscord.webhookutils;

public class ChatWebhook extends GenericWebhook {

    public ChatWebhook(String webhookUserName, String webhookAvatarUrl, String webhookMessageContent) {
        // This differentiation is unnecessary in this version, but it won't be in the next.
        super(webhookUserName, webhookAvatarUrl, webhookMessageContent);
    }
}
