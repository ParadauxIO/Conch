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

package io.paradaux.hiberniadiscord.bukkit;

import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChainFactory;
import io.paradaux.hiberniadiscord.bukkit.listeners.AsyncPlayerChatEventListener;
import io.paradaux.hiberniadiscord.common.api.BotManager;
import io.paradaux.hiberniadiscord.common.api.DiscordManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class HiberniaDiscord extends JavaPlugin implements CommandExecutor {

    private static Logger logger;
    private static TaskChainFactory taskChainFactory;
    List<String> monitoredChannels = new ArrayList<>();

    String webhookUrl = "https://discord.com/api/webhooks/763065395833602048/xUUX016wrPYPGWRJyfsGaDtwtxHJFrbWHrEfj4XMa5PvFT0jSc-kgcA9qF3ZP9cpH5Mv";

    String iconUrl = "https://cdn.paradaux.io/static/plugin-branding/hiberniadiscord/hibernia-discord.png";
    String token = "";
    String messageFormat = "";

    @Override
    public void onEnable() {
        logger = LoggerFactory.getLogger("io.paradaux.hiberniadiscord");

        System.out.println("Hello World 2");

        taskChainFactory = BukkitTaskChainFactory.create(this);
        BotManager.initialise(token, logger, monitoredChannels, taskChainFactory, messageFormat, true);
        DiscordManager.initialise(webhookUrl, true, logger);
        DiscordManager.sendDiscordMessage("Test", iconUrl, "hello world");

        this.getCommand("disable").setExecutor(this);

        registerEvents();
    }

    @Override
    public void onDisable() {

    }

    public void registerEvents() {
        String avatarApiUrl = "https://mc-heads.net/combo/%playeruuid%";
        String userNameFormat = "%playername%";
        String serverName = "Test Server";
        String messagePrefix = null;

        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new AsyncPlayerChatEventListener(avatarApiUrl, userNameFormat, serverName, messagePrefix,
                                    logger, true), this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        System.out.println("Disabling.");
        getServer().getPluginManager().disablePlugin(this);
        return true;
    }

}
