package io.paradaux.hiberniadiscord.api;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigurationCache {

    String discord_webhookURL;

    String serverName;
    String avatarAPI;

    boolean events_chatMessage_enabled;
    String events_chatMessage_format;

    boolean events_playerJoin_enabled;
    String events_playerJoin_format;

    boolean events_playerLeave_enabled;
    String events_playersLeave_format;

    boolean events_advancementCompleted_enabled;
    String events_advancementCompleted_format;

    boolean events_serverStartup_enabled;
    String getEvents_serverStartup_avatar;
    String events_serverStartup_format;

    boolean events_serverShutdown_enabled;
    String getEvents_serverShutdown_avatar;
    String events_serverShutdown_format;

    boolean update_check;
    boolean update_notify;

    double configVersion;

    public ConfigurationCache(String discord_webhookURL, String serverName, String avatarAPI, boolean events_chatMessage_enabled, String events_chatMessage_format, boolean events_playerJoin_enabled, String events_playerJoin_format, boolean events_playerLeave_enabled, String events_playersLeave_format, boolean events_advancementCompleted_enabled, String events_advancementCompleted_format, boolean events_serverStartup_enabled, String getEvents_serverStartup_avatar, String events_serverStartup_format, boolean events_serverShutdown_enabled, String getEvents_serverShutdown_avatar, String events_serverShutdown_format, boolean update_check, boolean update_notify, double configVersion) {
        this.discord_webhookURL = discord_webhookURL;
        this.serverName = serverName;
        this.avatarAPI = avatarAPI;
        this.events_chatMessage_enabled = events_chatMessage_enabled;
        this.events_chatMessage_format = events_chatMessage_format;
        this.events_playerJoin_enabled = events_playerJoin_enabled;
        this.events_playerJoin_format = events_playerJoin_format;
        this.events_playerLeave_enabled = events_playerLeave_enabled;
        this.events_playersLeave_format = events_playersLeave_format;
        this.events_advancementCompleted_enabled = events_advancementCompleted_enabled;
        this.events_advancementCompleted_format = events_advancementCompleted_format;
        this.events_serverStartup_enabled = events_serverStartup_enabled;
        this.getEvents_serverStartup_avatar = getEvents_serverStartup_avatar;
        this.events_serverStartup_format = events_serverStartup_format;
        this.events_serverShutdown_enabled = events_serverShutdown_enabled;
        this.getEvents_serverShutdown_avatar = getEvents_serverShutdown_avatar;
        this.events_serverShutdown_format = events_serverShutdown_format;
        this.update_check = update_check;
        this.update_notify = update_notify;
        this.configVersion = configVersion;
    }

    public ConfigurationCache(FileConfiguration config) {
        this.discord_webhookURL = config.getString("discord.webhook-url");
        this.serverName = config.getString("server-name");
        this.avatarAPI = config.getString("avatar-api");
        this.events_chatMessage_enabled = config.getBoolean("events.chat-message.enabled");
        this.events_chatMessage_format = config.getString("events.chat-message.format");
        this.events_playerJoin_enabled = config.getBoolean("events.player-join.enabled");
        this.events_playerJoin_format = config.getString("events.player-join.format");
        this.events_playerLeave_enabled = config.getBoolean("events.player-leave.enabled");
        this.events_playersLeave_format = config.getString("events.player-leave.format");
        this.events_advancementCompleted_enabled = config.getBoolean("events.advancement-completed.enabled");
        this.events_advancementCompleted_format = config.getString("events.advancement-completed.format");
        this.events_serverStartup_enabled = config.getBoolean("events.server-startup.enabled");
        this.getEvents_serverStartup_avatar = config.getString("events.server-startup.avatar-url");
        this.events_serverStartup_format = config.getString("events.server-startup.format");
        this.events_serverShutdown_enabled = config.getBoolean("events.server-shutdown.enabled");
        this.getEvents_serverShutdown_avatar = config.getString("events.server-shutdown.avatar-url");
        this.events_serverShutdown_format = config.getString("events.server-shutdown.format");
        this.update_check = config.getBoolean("update.check");
        this.update_notify = config.getBoolean("update.notify");
        this.configVersion = config.getDouble("version");
    }

    public String getDiscord_webhookURL() {
        return discord_webhookURL;
    }

    public String getServerName() {
        return serverName;
    }

    public String getAvatarAPI() {
        return avatarAPI;
    }

    public boolean isEvents_chatMessage_enabled() {
        return events_chatMessage_enabled;
    }

    public String getEvents_chatMessage_format() {
        return events_chatMessage_format;
    }

    public boolean isEvents_playerJoin_enabled() {
        return events_playerJoin_enabled;
    }

    public String getEvents_playerJoin_format() {
        return events_playerJoin_format;
    }

    public boolean isEvents_playerLeave_enabled() {
        return events_playerLeave_enabled;
    }

    public String getEvents_playersLeave_format() {
        return events_playersLeave_format;
    }

    public boolean isEvents_advancementCompleted_enabled() {
        return events_advancementCompleted_enabled;
    }

    public String getEvents_advancementCompleted_format() {
        return events_advancementCompleted_format;
    }

    public boolean isEvents_serverStartup_enabled() {
        return events_serverStartup_enabled;
    }

    public String getGetEvents_serverStartup_avatar() {
        return getEvents_serverStartup_avatar;
    }

    public String getEvents_serverStartup_format() {
        return events_serverStartup_format;
    }

    public boolean isEvents_serverShutdown_enabled() {
        return events_serverShutdown_enabled;
    }

    public String getGetEvents_serverShutdown_avatar() {
        return getEvents_serverShutdown_avatar;
    }

    public String getEvents_serverShutdown_format() {
        return events_serverShutdown_format;
    }

    public boolean isUpdate_check() {
        return update_check;
    }

    public boolean isUpdate_notify() {
        return update_notify;
    }

    public double getConfigVersion() {
        return configVersion;
    }
}
