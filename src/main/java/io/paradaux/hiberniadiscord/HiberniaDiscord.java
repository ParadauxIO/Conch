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

package io.paradaux.hiberniadiscord;

import io.paradaux.hiberniadiscord.api.VersionChecker;
import io.paradaux.hiberniadiscord.commands.DiscordCmd;
import io.paradaux.hiberniadiscord.commands.HiberniaDiscordCmd;
import io.paradaux.hiberniadiscord.controllers.*;
import io.paradaux.hiberniadiscord.eventlisteners.*;
import io.paradaux.hiberniadiscord.events.ServerStopEvent;
import io.paradaux.hiberniadiscord.webhookutils.GenericWebhook;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

import java.util.Locale;
import java.util.Objects;

public class HiberniaDiscord extends JavaPlugin {

    private static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;


        LogController.createLogger(getConfig().getBoolean("settings.debug"));

        Logger logger = LogController.getLogger();
        logger.error("AHHHHH");


        new ConfigurationController();
        new MetricsController(this);
        new TaskController(this);
        new VersionNotificationController();

        System.out.println(I18nController.INSTANCE.getMessage(Locale.ENGLISH, "test.property"));

        registerCommands();
        registerEvents(getServer().getPluginManager());

        System.out.println("WEBHOOK: " + ConfigurationController.getPluginConfiguration().getDiscordWebhookUrl());
        // Test validity of webhook, if invalid; disable the plugin.
        if (GenericWebhook.testWebhookURL(ConfigurationController.getPluginConfiguration().getDiscordWebhookUrl())) {
            logger.error("Your webhook url is invalid! Please see: https://github"
                    + ".com/ParadauxIO/HiberniaDiscord/wiki/Creating-a-webhook\n for instructions"
                    + " as to how to create one!");
//            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Prepare event listeners, so they can talk to discord.
        WebhookListener.createClient();

        GenericWebhook.setWebhookUrl(ConfigurationController.getPluginConfiguration()
                .getDiscordWebhookUrl());

        GenericWebhook.createClient();

        new VersionChecker(67795).getVersion(version -> {
            if (!this.getDescription().getVersion().equalsIgnoreCase(version)) {
                logger.info("There is a new update available. \n Please update: "
                        + "https://www.spigotmc.org/resources/hiberniadiscord-%C2%BB-chat-to-disco"
                        + "rd-integration.67795/");
                return;
            }
            logger.info("There are no new updates available");
        });

    }

    @Override
    public void onDisable() {
        Bukkit.getPluginManager().callEvent(new ServerStopEvent());
    }

    public void registerCommands() {
        Objects.requireNonNull(getCommand("hiberniadiscord")).setExecutor(new HiberniaDiscordCmd());
        Objects.requireNonNull(getCommand("discord")).setExecutor(new DiscordCmd());
    }
    public void registerEvents(PluginManager pm) {
        pm.registerEvents(new AsyncPlayerChatEventListener(), this);


        try {
            pm.registerEvents(new PlayerAchievementAwardedEventListener(), this);
        } catch (Exception ok) {
            // OK
        }

        pm.registerEvents(new PlayerJoinEventListener(), this);
        pm.registerEvents(new PlayerQuitEventListener(), this);
        pm.registerEvents(new ServerLoadEventListener(), this);
        pm.registerEvents(new ServerStopEventListener(), this);
    }

    public static void log(String error) {
        Bukkit.getLogger().warning(error);
    }

    public static Plugin getPlugin() {
        return plugin;
    }
}
