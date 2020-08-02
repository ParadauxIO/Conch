/*
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 */

package io.paradaux.hiberniadiscord.api;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class LocaleCache {

    String prefix;
    List<String> loadingMessage, shutdownMessage;

    String severeOldConfigVersion, severeConfigNotExist, severeBadBackup;

    String hiberniaDiscordReloadSuccess;
    List<String> hiberniaDiscordDefault;

    List<String> discordDefault;
    String discordLink;

    double localeVersion;

    public LocaleCache(FileConfiguration locale) {
        this.prefix = locale.getString("prefix");
        this.loadingMessage = locale.getStringList("loading-message");
        this.shutdownMessage = locale.getStringList("shutdown-message");
        this.severeOldConfigVersion = locale.getString("severe-messages.old-version-config");
        this.severeConfigNotExist = locale.getString("severe-messages.config-not-exist");
        this.severeBadBackup = locale.getString("severe-messages.bad-backup");
        this.hiberniaDiscordReloadSuccess = locale.getString("hiberniadiscord.reload-success");
        this.hiberniaDiscordDefault = locale.getStringList("hiberniadiscord.default");
        this.discordDefault = locale.getStringList("discord.default");
        this.discordLink = locale.getString("discord.link");
        this.localeVersion = locale.getDouble("discord.link");
    }

    public String getPrefix() {
        return prefix;
    }

    public String getLoadingMessage() {
        return "\n" + String.join("\n", loadingMessage);
    }

    public String getShutdownMessage() {
        return "\n" + String.join("\n", shutdownMessage);
    }

    public String getSevereOldConfigVersion() {
        return severeOldConfigVersion;
    }

    public String getSevereConfigNotExist() {
        return severeConfigNotExist;
    }

    public String getSevereBadBackup() {
        return severeBadBackup;
    }

    public String getHiberniaDiscordReloadSuccess() {
        return hiberniaDiscordReloadSuccess;
    }

    public String getHiberniaDiscordDefault() {
        return String.join("\n", hiberniaDiscordDefault);
    }

    public String getDiscordDefault() {
        return String.join("\n", discordDefault);
    }

    public String getDiscordLink() {
        return discordLink;
    }

    public double getLocaleVersion() {
        return localeVersion;
    }

}
