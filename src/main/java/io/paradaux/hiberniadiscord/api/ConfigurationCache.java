/*
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 */

package io.paradaux.hiberniadiscord.api;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigurationCache {

    boolean discordCommand;
    String discordWebhookURL;

    String serverName, avatarAPI;

    boolean chatMessageEnabled;
    String chatMessageUsernameFormat, chatMessageMessageFormat,chatMessageAvatarUrl;

    // Part of the new globalised-messages support in 3.0.1
    boolean messagePrefixDisabled;
    String messagePrefix;

    boolean playerJoinEnabled;
    String playerJoinUsernameFormat, playerJoinMessageFormat, playerJoinAvatarUrl;

    boolean playerLeaveEnabled;
    String playerLeaveUsernameFormat, playerLeaveMessageFormat, playerLeaveAvatarUrl;

    boolean advancementCompletedEnabled;
    String advancementCompletedUsernameFormat, advancementCompletedMessageFormat, advancementCompletedAvatarUrl;

    boolean serverStartupEnabled;
    String serverStartupUsernameFormat, serverStartupMessageFormat, serverStartupAvatarUrl;

    boolean serverShutdownEnabled;
    String serverShutdownUsernameFormat, serverShutdownMessageFormat, serverShutdownAvatarUrl;

    String discordCommandPermission, reloadCommandPermission;

    boolean updateCheck, updateNotify;
    double configVersion;

    public ConfigurationCache(boolean discordCommand, String discordWebhookURL, String serverName, String avatarAPI, boolean chatMessageEnabled, String chatMessageUsernameFormat, String chatMessageMessageFormat, String chatMessageAvatarUrl, boolean messagePrefixDisabled, String messagePrefix, boolean playerJoinEnabled, String playerJoinUsernameFormat, String playerJoinMessageFormat, String playerJoinAvatarUrl, boolean playerLeaveEnabled, String playerLeaveUsernameFormat, String playerLeaveMessageFormat, String playerLeaveAvatarUrl, boolean advancementCompletedEnabled, String advancementCompletedUsernameFormat, String advancementCompletedMessageFormat, String advancementCompletedAvatarUrl, boolean serverStartupEnabled, String serverStartupUsernameFormat, String serverStartupMessageFormat, String serverStartupAvatarUrl, boolean serverShutdownEnabled, String serverShutdownUsernameFormat, String serverShutdownMessageFormat, String serverShutdownAvatarUrl, String discordCommandPermission, String reloadCommandPermission, boolean updateCheck, boolean updateNotify, double configVersion) {

        this.discordCommand = discordCommand;
        this.discordWebhookURL = discordWebhookURL;

        this.serverName = serverName;
        this.avatarAPI = avatarAPI;

        this.chatMessageEnabled = chatMessageEnabled;
        this.chatMessageUsernameFormat = chatMessageUsernameFormat;
        this.chatMessageMessageFormat = chatMessageMessageFormat;
        this.chatMessageAvatarUrl = chatMessageAvatarUrl;

        this.messagePrefixDisabled = messagePrefixDisabled;
        this.messagePrefix = messagePrefix;

        this.playerJoinEnabled = playerJoinEnabled;
        this.playerJoinUsernameFormat = playerJoinUsernameFormat;
        this.playerJoinMessageFormat = playerJoinMessageFormat;
        this.playerJoinAvatarUrl = playerJoinAvatarUrl;

        this.playerLeaveEnabled = playerLeaveEnabled;
        this.playerLeaveUsernameFormat = playerLeaveUsernameFormat;
        this.playerLeaveMessageFormat = playerLeaveMessageFormat;
        this.playerLeaveAvatarUrl = playerLeaveAvatarUrl;

        this.advancementCompletedEnabled = advancementCompletedEnabled;
        this.advancementCompletedUsernameFormat = advancementCompletedUsernameFormat;
        this.advancementCompletedMessageFormat = advancementCompletedMessageFormat;
        this.advancementCompletedAvatarUrl = advancementCompletedAvatarUrl;

        this.serverStartupEnabled = serverStartupEnabled;
        this.serverStartupUsernameFormat = serverStartupUsernameFormat;
        this.serverStartupMessageFormat = serverStartupMessageFormat;
        this.serverStartupAvatarUrl = serverStartupAvatarUrl;

        this.serverShutdownEnabled = serverShutdownEnabled;
        this.serverShutdownUsernameFormat = serverShutdownUsernameFormat;
        this.serverShutdownMessageFormat = serverShutdownMessageFormat;
        this.serverShutdownAvatarUrl = serverShutdownAvatarUrl;

        this.discordCommandPermission = discordCommandPermission;
        this.reloadCommandPermission = reloadCommandPermission;

        this.updateCheck = updateCheck;
        this.updateNotify = updateNotify;

        this.configVersion = configVersion;
    }

    public ConfigurationCache(FileConfiguration config) {
        this.discordCommand = config.getBoolean("discord.discord-command");
        this.discordWebhookURL = config.getString("discord.webhook-url");

        this.serverName = config.getString("server-name");
        this.avatarAPI = config.getString("avatar-api");

        this.chatMessageEnabled = config.getBoolean("events.chat-message.enabled");
        this.chatMessageUsernameFormat = config.getString("events.chat-message.username-format");
        this.chatMessageMessageFormat = config.getString("events.chat-message.message-format");
        this.chatMessageAvatarUrl = config.getString("events.chat-message.avatar-url");

        this.messagePrefixDisabled = config.getBoolean("events.chat-message.message-prefix-disabled");
        this.messagePrefix = config.getString("events.chat-message.message-prefix");

        this.playerJoinEnabled = config.getBoolean("events.player-join.enabled");
        this.playerJoinUsernameFormat = config.getString("events.player-join.username-format");
        this.playerJoinMessageFormat = config.getString("events.player-join.message-format");
        this.playerJoinAvatarUrl = config.getString("events.player-join.avatar-url");

        this.playerLeaveEnabled = config.getBoolean("events.player-leave.enabled");
        this.playerLeaveUsernameFormat = config.getString("events.player-leave.username-format");
        this.playerLeaveMessageFormat = config.getString("events.player-leave.message-format");
        this.playerLeaveAvatarUrl = config.getString("events.player-leave.avatar-url");

        this.advancementCompletedEnabled = config.getBoolean("events.advancement-completed.enabled");
        this.advancementCompletedUsernameFormat = config.getString("events.advancement-completed.username-format");
        this.advancementCompletedMessageFormat = config.getString("events.advancement-completed.message-format");
        this.advancementCompletedAvatarUrl = config.getString("events.advancement-completed.avatar-url");

        this.serverStartupEnabled = config.getBoolean("events.server-startup.enabled");
        this.serverStartupUsernameFormat = config.getString("events.server-startup.username-format");
        this.serverStartupMessageFormat = config.getString("events.server-startup.message-format");
        this.serverStartupAvatarUrl = config.getString("events.server-startup.avatar-url");

        this.serverShutdownEnabled = config.getBoolean("events.server-shutdown.enabled");
        this.serverShutdownUsernameFormat = config.getString("events.server-shutdown.username-format");
        this.serverShutdownMessageFormat = config.getString("events.server-shutdown.message-format");
        this.serverShutdownAvatarUrl = config.getString("events.server-shutdown.avatar-url");

        this.discordCommandPermission = config.getString("settings.discord-command-permission");
        this.reloadCommandPermission = config.getString("settings.reload-command-permission");

        this.updateCheck = config.getBoolean("update.check");
        this.updateNotify = config.getBoolean("update.notify");

        this.configVersion = config.getDouble("config-version");
    }

    public boolean isDiscordCommand() {
        return discordCommand;
    }

    public String getDiscordWebhookURL() {
        return discordWebhookURL;
    }

    public String getServerName() {
        return serverName;
    }

    public String getAvatarAPI() {
        return avatarAPI;
    }

    public boolean isChatMessageEnabled() {
        return chatMessageEnabled;
    }

    public String getChatMessageUsernameFormat() {
        return chatMessageUsernameFormat;
    }

    public String getChatMessageMessageFormat() {
        return chatMessageMessageFormat;
    }

    public String getChatMessageAvatarUrl() {
        return chatMessageAvatarUrl;
    }

    public boolean isPlayerJoinEnabled() {
        return playerJoinEnabled;
    }

    public String getPlayerJoinUsernameFormat() {
        return playerJoinUsernameFormat;
    }

    public String getPlayerJoinMessageFormat() {
        return playerJoinMessageFormat;
    }

    public String getPlayerJoinAvatarUrl() {
        return playerJoinAvatarUrl;
    }

    public boolean isPlayerLeaveEnabled() {
        return playerLeaveEnabled;
    }

    public String getPlayerLeaveUsernameFormat() {
        return playerLeaveUsernameFormat;
    }

    public String getPlayerLeaveMessageFormat() {
        return playerLeaveMessageFormat;
    }

    public String getPlayerLeaveAvatarUrl() {
        return playerLeaveAvatarUrl;
    }

    public boolean isAdvancementCompletedEnabled() {
        return advancementCompletedEnabled;
    }

    public String getAdvancementCompletedUsernameFormat() {
        return advancementCompletedUsernameFormat;
    }

    public String getAdvancementCompletedMessageFormat() {
        return advancementCompletedMessageFormat;
    }

    public String getAdvancementCompletedAvatarUrl() {
        return advancementCompletedAvatarUrl;
    }

    public boolean isServerStartupEnabled() {
        return serverStartupEnabled;
    }

    public String getServerStartupUsernameFormat() {
        return serverStartupUsernameFormat;
    }

    public String getServerStartupMessageFormat() {
        return serverStartupMessageFormat;
    }

    public String getServerStartupAvatarUrl() {
        return serverStartupAvatarUrl;
    }

    public boolean isServerShutdownEnabled() {
        return serverShutdownEnabled;
    }

    public String getServerShutdownUsernameFormat() {
        return serverShutdownUsernameFormat;
    }

    public String getServerShutdownMessageFormat() {
        return serverShutdownMessageFormat;
    }

    public String getServerShutdownAvatarUrl() {
        return serverShutdownAvatarUrl;
    }

    public String getDiscordCommandPermission() {
        return discordCommandPermission;
    }

    public String getReloadCommandPermission() {
        return reloadCommandPermission;
    }

    public boolean isUpdateCheck() {
        return updateCheck;
    }

    public boolean isUpdateNotify() {
        return updateNotify;
    }

    public double getConfigVersion() {
        return configVersion;
    }

    public boolean isMessagePrefixDisabled() {
        return messagePrefixDisabled;
    }

    public String getMessagePrefix() {
        return messagePrefix;
    }
}
