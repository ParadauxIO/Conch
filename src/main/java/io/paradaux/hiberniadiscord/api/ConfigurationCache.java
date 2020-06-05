package io.paradaux.hiberniadiscord.api;

import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigurationCache {

    String serverName;
    String serverPrefix;
    String serverIcon;

    String avatarApi;
    String eventPlayerJoin;
    String eventPlayerLeave;
    String eventServerStartup;
    String eventServerShutdown;

    boolean updateCheck;
    boolean updateNotify;

    double configVersion;

    public ConfigurationCache(YamlConfiguration config) {
        this.serverName = serverName;
        this.serverPrefix = serverPrefix;
        this.serverIcon = serverIcon;
        this.avatarApi = avatarApi;
        this.eventPlayerJoin = eventPlayerJoin;
        this.eventPlayerLeave = eventPlayerLeave;
        this.eventServerStartup = eventServerStartup;
        this.eventServerShutdown = eventServerShutdown;
        this.updateCheck = updateCheck;
        this.updateNotify = updateNotify;
        this.configVersion = configVersion;
    }
}
