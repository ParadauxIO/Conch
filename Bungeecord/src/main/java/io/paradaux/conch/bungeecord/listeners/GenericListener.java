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

package io.paradaux.conch.bungeecord.listeners;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import org.jetbrains.annotations.NotNull;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

public class GenericListener implements Listener {

    private final String avatarApiUrl;
    private final String serverName;
    private final String userNameFormat;

    /**
     * Represents a non-specific bukkit listener with the configuration values required to run.
     * */
    public GenericListener(String avatarApiUrl, String userNameFormat, String serverName) {
        this.avatarApiUrl = avatarApiUrl;
        this.userNameFormat = userNameFormat;
        this.serverName = serverName;
    }

    /**
     * Formats the string provided accounting for the various placeholders, which are injected into from the provided OfflinePlayer Object.
     * */
    @Nullable
    @CheckReturnValue
    protected String parsePlaceholders(ProxiedPlayer player, @NotNull String str) {
        if (player == null || player.getName() == null) {
            return null;
        }

        String avatarUrl = parseAvatarApi(player);

        if (avatarUrl == null) {
            avatarUrl = "";
        }

        return str.replace("%playername%", player.getName())
                .replace("%playeruuid%", player.getUniqueId().toString())
                .replace("%servername%", serverName)
                .replace("%avatarapi%", avatarUrl);
    }

    @Nullable
    @CheckReturnValue
    protected String parseAvatarApi(ProxiedPlayer player) {
        return avatarApiUrl.replace("%playeruuid%", player.getUniqueId().toString());
    }

    public String getAvatarApiUrl() {
        return avatarApiUrl;
    }

    public String getServerName() {
        return serverName;
    }

    public String getUserNameFormat() {
        return userNameFormat;
    }

}
