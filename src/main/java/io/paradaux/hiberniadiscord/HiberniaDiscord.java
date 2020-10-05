/*
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 */

package io.paradaux.hiberniadiscord;

import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChain;
import co.aikar.taskchain.TaskChainFactory;
import io.paradaux.hiberniadiscord.EventListeners.*;
import io.paradaux.hiberniadiscord.api.ConfigurationCache;
import io.paradaux.hiberniadiscord.api.ConfigurationUtils;
import io.paradaux.hiberniadiscord.api.LocaleCache;
import io.paradaux.hiberniadiscord.api.VersionChecker;
import io.paradaux.hiberniadiscord.commands.DiscordCMD;
import io.paradaux.hiberniadiscord.commands.HiberniaDiscordCMD;
import io.paradaux.hiberniadiscord.discord2mc.Discord2Mc;
import io.paradaux.hiberniadiscord.events.ServerStopEvent;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public class HiberniaDiscord extends JavaPlugin {

    final private static Logger logger = Logger.getLogger("io.paradaux.hiberniadiscord");
    public static Logger getMainLogger() { return logger; }

    private static ConfigurationCache configurationCache;
    public static ConfigurationCache getConfigurationCache() { return configurationCache; }

    private static LocaleCache localeCache;
    public static LocaleCache getLocaleCache() { return localeCache; }

    private static Plugin plugin;
    public static Plugin getPlugin() { return plugin; }

    private static boolean upgradeRequired;
    public static boolean getUpgradeRequired() { return upgradeRequired; }

    private static Discord2Mc discord2Mc;
    public static Discord2Mc getDiscord2Mc() { return discord2Mc; }

    private static TaskChainFactory taskChainFactory;
    public static <T> TaskChain<T> newChain() { return taskChainFactory.newChain(); }
    public static <T> TaskChain<T> newSharedChain(String name) { return taskChainFactory.newSharedChain(name); }



    @Override
    public void onEnable() {
        taskChainFactory = BukkitTaskChainFactory.create(this);
        plugin = this;

        ConfigurationUtils.updateConfigurationFile(this.getConfig());
        configurationCache = new ConfigurationCache(this.getConfig());

        ConfigurationUtils.deployLocale(this);
        localeCache = new LocaleCache(ConfigurationUtils.getLocale());

        ConfigurationUtils.deployDiscord2McConfiguration(this);
        discord2Mc = new Discord2Mc(ConfigurationUtils.getDiscord2McConfigurationFile());

        getLogger().info(localeCache.getLoadingMessage());

        registerCommands();
        registerEvents(getServer().getPluginManager());

        versionChecker();

        // bStats: I respect your decision to disable this!
        // See config.yml for my statement.
        if (!getConfig().getBoolean("settings.bstats")) return;
        Metrics metrics = new Metrics(this, 8386);

    }

    @Override
    public void onDisable() {
        getLogger().info(localeCache.getShutdownMessage());
        Bukkit.getPluginManager().callEvent(new ServerStopEvent());
    }

    public void registerCommands() {
        this.getCommand("hiberniadiscord").setExecutor(new HiberniaDiscordCMD());
        this.getCommand("hdiscord").setExecutor(new HiberniaDiscordCMD());
        this.getCommand("discord").setExecutor(new DiscordCMD());
    }
    public void registerEvents(PluginManager pm) {
        pm.registerEvents(new AsyncPlayerChatEventListener(), this);
        pm.registerEvents(new PlayerAchievementAwardedEventListener(), this);
        pm.registerEvents(new PlayerJoinEventListener(), this);
        pm.registerEvents(new PlayerQuitEventListener(), this);
        pm.registerEvents(new ServerLoadEventListener(), this);
        pm.registerEvents(new ServerStopEventListener(), this);
    }

    public static void log(String error) {
        Bukkit.getLogger().warning(error);
    }

    public static void updateConfigurationCache() {
        File configurationFile = new File(Bukkit.getServer().getPluginManager().getPlugin("HiberniaDiscord").getDataFolder(), "config.yml");
        configurationCache = new ConfigurationCache(YamlConfiguration.loadConfiguration(configurationFile));
    }

    public static void updateLocaleCache() {
        File localeFile = new File(Bukkit.getServer().getPluginManager().getPlugin("HiberniaDiscord").getDataFolder(), "locale.yml");
        localeCache = new LocaleCache(YamlConfiguration.loadConfiguration(localeFile));
    }

    public void versionChecker() {
        new VersionChecker(this, 67795).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                logger.info("There are no new updates available");
                upgradeRequired = false;
            } else {
                logger.info("There is a new update available. \n Please update: https://www.spigotmc.org/resources/hiberniadiscord-%C2%BB-chat-to-discord-integration.67795/");
                upgradeRequired = true;
            }
        });
    }


}
