/*
 * Copyright (c) 2021, Rían Errity. All rights reserved.
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

package io.paradaux.conch.velocity.listeners;

import com.velocitypowered.api.proxy.Player;
import io.paradaux.conch.common.api.config.ConfigurationUtil;
import org.jetbrains.annotations.NotNull;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

public class GenericListener {

    /**
     * Represents a non-specific bukkit listener with the configuration values required to run.
     * */
    public GenericListener() {

    }

    /**
     * Parses purely the custom placeholders.
     * */
    protected String parseCustomPlaceholders(Player player, String serverName, String avatarApi, String str) {
        if (avatarApi == null) {
            avatarApi = "";
        }

        // The proxy has no idea of what the display name is.
        return str.replace("%playerUserName%", player.getUsername())
                .replace("%playerUUID", player.getUniqueId().toString())
                .replace("%avatarApi%", parseAvatarApi(player, avatarApi))
                .replace("%serverName%", serverName);

    }

    @Nullable
    @CheckReturnValue
    protected String parseAvatarApi(Player player, String avatarApiUrl) {
        if (player == null) {
            throw new IllegalStateException("This player is not available.");
        }

        String uuid = player.getUniqueId().toString();

        avatarApiUrl = avatarApiUrl.replace("%avatarApi%", ConfigurationUtil.getGeneralSettings().getAvatarApi());
        return avatarApiUrl.replace("%playerUUID%", ConfigurationUtil.getGeneralSettings().isAvatarApiHyphen() ? uuid : uuid.replace("-", ""));
    }


}
