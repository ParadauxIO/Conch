package io.paradaux.hiberniadiscord.api;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigurationCache {

    String serverName;
    String serverPrefix;
    String serverIcon;
    String discordMessage;
    String avatarApi;
    String eventPlayerJoin;
    String eventPlayerLeave;
    String eventServerStartup;
    String eventServerShutdown;

    boolean updateCheck;
    boolean updateNotify;

    double configVersion;

    public ConfigurationCache(FileConfiguration config) {
        this.serverName = serverName;
        this.serverPrefix = serverPrefix;
        this.serverIcon = serverIcon;
        this.discordMessage = discordMessage;
        this.avatarApi = avatarApi;
        this.eventPlayerJoin = eventPlayerJoin;
        this.eventPlayerLeave = eventPlayerLeave;
        this.eventServerStartup = eventServerStartup;
        this.eventServerShutdown = eventServerShutdown;
        this.updateCheck = updateCheck;
        this.updateNotify = updateNotify;
        this.configVersion = configVersion;
    }

    public String colorise(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public String parsePlaceholders(String s) {
        return s;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerPrefix() {
        return serverPrefix;
    }

    public void setServerPrefix(String serverPrefix) {
        this.serverPrefix = serverPrefix;
    }

    public String getServerIcon() {
        return serverIcon;
    }

    public void setServerIcon(String serverIcon) {
        this.serverIcon = serverIcon;
    }

    public String getDiscordMessage() {
        return discordMessage;
    }

    public void setDiscordMessage(String discordMessage) {
        this.discordMessage = discordMessage;
    }

    public String getAvatarApi() {
        return avatarApi;
    }

    public void setAvatarApi(String avatarApi) {
        this.avatarApi = avatarApi;
    }

    public String getEventPlayerJoin() {
        return eventPlayerJoin;
    }

    public void setEventPlayerJoin(String eventPlayerJoin) {
        this.eventPlayerJoin = eventPlayerJoin;
    }

    public String getEventPlayerLeave() {
        return eventPlayerLeave;
    }

    public void setEventPlayerLeave(String eventPlayerLeave) {
        this.eventPlayerLeave = eventPlayerLeave;
    }

    public String getEventServerStartup() {
        return eventServerStartup;
    }

    public void setEventServerStartup(String eventServerStartup) {
        this.eventServerStartup = eventServerStartup;
    }

    public String getEventServerShutdown() {
        return eventServerShutdown;
    }

    public void setEventServerShutdown(String eventServerShutdown) {
        this.eventServerShutdown = eventServerShutdown;
    }

    public boolean isUpdateCheck() {
        return updateCheck;
    }

    public void setUpdateCheck(boolean updateCheck) {
        this.updateCheck = updateCheck;
    }

    public boolean isUpdateNotify() {
        return updateNotify;
    }

    public void setUpdateNotify(boolean updateNotify) {
        this.updateNotify = updateNotify;
    }

    public double getConfigVersion() {
        return configVersion;
    }

    public void setConfigVersion(double configVersion) {
        this.configVersion = configVersion;
    }
}
