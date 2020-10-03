/*
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 */

package io.paradaux.hiberniadiscord.discord2mc.api;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Discord2McConfigurationCache {

    boolean isEnabled;

    String token;

    String commandPrefix;

    boolean doSendBotMessages;

    List<String> monitoredChannels;

    String messageFormat;

    double version;

    public Discord2McConfigurationCache(FileConfiguration config) {
        this.isEnabled = config.getBoolean("enabled");
        this.token = config.getString("token");
        this.commandPrefix = config.getString("command-prefix");
        this.doSendBotMessages = config.getBoolean("send-messages-from-bots");
        this.monitoredChannels = config.getStringList("monitored-channels");
        this.messageFormat = config.getString("message-format");
        this.version = config.getDouble("version");
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public String getToken() {
        return token;
    }

    public boolean isDoSendBotMessages() {
        return doSendBotMessages;
    }

    public List<String> getMonitoredChannels() {
        return monitoredChannels;
    }

    public String getMessageFormat() {
        return messageFormat;
    }

    public double getVersion() {
        return version;
    }
}
