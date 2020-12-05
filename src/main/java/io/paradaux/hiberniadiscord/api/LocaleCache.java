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

package io.paradaux.hiberniadiscord.api;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class LocaleCache {

    String prefix;
    List<String> loadingMessage;
    List<String> shutdownMessage;

    String severeOldConfigVersion;
    String severeConfigNotExist;
    String severeBadBackup;
    String severeNoPermission;

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
        this.severeNoPermission = locale.getString("severe-messages.no-permission");
        this.hiberniaDiscordReloadSuccess = locale.getString("hiberniadiscord.reload-success");
        this.hiberniaDiscordDefault = locale.getStringList("hiberniadiscord.default");
        this.discordDefault = locale.getStringList("discord.default");
        this.discordLink = locale.getString("discord.link");
        this.localeVersion = locale.getDouble("discord.link");
    }

    public static String colorise(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
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

    public String getSevereNoPermission() {
        return severeNoPermission;
    }
}
