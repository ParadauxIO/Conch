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
import io.paradaux.hiberniadiscord.bukkit.api.BukkitAPI;
import io.paradaux.hiberniadiscord.bukkit.api.BukkitConfigurationManager;
import io.paradaux.hiberniadiscord.common.api.I18NLogger;
import io.paradaux.hiberniadiscord.common.api.I18NManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.Locale;
import java.util.ResourceBundle;

public class HiberniaDiscord extends JavaPlugin {

    private static BukkitAPI API;

    private static Logger logger;


    @Override
    public void onEnable() {
        logger = LoggerFactory.getLogger("io.paradaux.hiberniadiscord");

        TaskChainFactory taskChainFactory = BukkitTaskChainFactory.create(this);

        Path configDir = this.getDataFolder().toPath();
        BukkitConfigurationManager configurationManager = new BukkitConfigurationManager(configDir, this);

        Locale locale = new Locale("en_US");
        I18NManager i18NManager = new I18NManager(ResourceBundle.getBundle(I18NManager.RESOURCES_PATH, locale));

        I18NLogger.setLogger(LoggerFactory.getLogger("io.paradaux.hiberniadiscord"));
        I18NLogger.setI18nManager(i18NManager);

        configurationManager.deployResource();

        API = new BukkitAPI();

        registerEvents();
    }

    @Override
    public void onDisable() {

    }

    public void loadConfiguration() {
        // TODO
    }


    public void registerEvents() {
        String avatarApiUrl = "https://mc-heads.net/combo/%playeruuid%";
        String userNameFormat = "%playername%";
        String serverName = "Test Server";
        String messagePrefix = null;

//        PluginManager pluginManager = getServer().getPluginManager();
//
//        pluginManager.registerEvents(new AsyncPlayerChatEventListener(avatarApiUrl, userNameFormat, serverName, messagePrefix,
//                                    logger, true), this);
    }



}
