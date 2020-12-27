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

package io.paradaux.hiberniadiscord.controllers;

import io.paradaux.hiberniadiscord.models.BotConfiguration;
import io.paradaux.hiberniadiscord.models.PluginConfiguration;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;


public class MetricsController {

    private static final int PLUGIN_ID = 8386;

    public static MetricsController INSTANCE;

    PluginConfiguration configuration = ConfigurationController.getPluginConfiguration();
    BotConfiguration botConfiguration = ConfigurationController.getBotConfiguration();
    Logger logger = LogController.getLogger();
    Metrics metrics;

    /**
     * Creates the Metrics Instance which relays to BStats various things about the host server.
     * */
    public MetricsController(Plugin plugin) {
        if (!configuration.isBstatsEnabled()) {
            return;
        }

        metrics = new Metrics(plugin, PLUGIN_ID);

        metrics.addCustomChart(new Metrics.SimplePie("using_discord2mc", () -> parseBoolean(botConfiguration.isEnabled())));
        metrics.addCustomChart(new Metrics.SimplePie("using_discord_commands", () -> parseBoolean(botConfiguration.isCommandsEnabled())));

        INSTANCE = this;
    }

    /**
     * Turns a boolean TRUE into "Yes" and FALSE into "No".
     * */
    private String parseBoolean(boolean b) {
        return b ? "Yes" : "No";
    }
}
