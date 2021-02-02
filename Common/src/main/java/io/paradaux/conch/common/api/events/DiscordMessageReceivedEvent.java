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

package io.paradaux.hiberniadiscord.common.api.events;

/**
 * Represents a message which was sent into a monitored discord channel so it can then be
 * viewed in game.
 * */
public class DiscordMessageReceivedEvent {

    String username;
    String nickname;
    String discriminator;
    String tag = "%s#%s";

    String guildName;
    String channel;

    String messageContent;
    String role;

    /**
     * Builder constructor.
     * */
    public DiscordMessageReceivedEvent() {

    }

    /**
     * Default constructor.
     * */
    public DiscordMessageReceivedEvent(String username, String nickname, String discriminator, String guildName, String channel,
                                       String messageContent, String role) {
        this.username = username;
        this.nickname = nickname;
        this.discriminator = discriminator;
        this.guildName = guildName;
        this.channel = channel;
        this.messageContent = messageContent;
        this.role = role;
    }

    public String getMessageContent() {
        return messageContent;
    }



    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public String getTag() {
        return tag;
    }

    public String getGuildName() {
        return guildName;
    }

    public String getChannel() {
        return channel;
    }

    public String getRole() {
        return role;
    }

    public DiscordMessageReceivedEvent setUsername(String username) {
        this.username = username;
        return this;
    }

    public DiscordMessageReceivedEvent setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public DiscordMessageReceivedEvent setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
        return this;
    }

    public DiscordMessageReceivedEvent setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public DiscordMessageReceivedEvent setGuildName(String guildName) {
        this.guildName = guildName;
        return this;
    }

    public DiscordMessageReceivedEvent setChannel(String channel) {
        this.channel = channel;
        return this;
    }

    public DiscordMessageReceivedEvent setMessageContent(String messageContent) {
        this.messageContent = messageContent;
        return this;
    }

    public DiscordMessageReceivedEvent setRole(String role) {
        this.role = role;
        return this;
    }
}
