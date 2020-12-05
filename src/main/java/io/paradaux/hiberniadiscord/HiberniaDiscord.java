/*
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 */

package io.paradaux.hiberniadiscord;

import club.minnced.discord.webhook.WebhookClient;
import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChain;
import co.aikar.taskchain.TaskChainFactory;
import io.paradaux.hiberniadiscord.eventlisteners.*;
import io.paradaux.hiberniadiscord.api.ConfigurationCache;
import io.paradaux.hiberniadiscord.api.ConfigurationUtils;
import io.paradaux.hiberniadiscord.api.LocaleCache;
import io.paradaux.hiberniadiscord.api.VersionChecker;
import io.paradaux.hiberniadiscord.commands.DiscordCmd;
import io.paradaux.hiberniadiscord.commands.HiberniaDiscordCmd;
import io.paradaux.hiberniadiscord.discord2mc.Discord2Mc;
import io.paradaux.hiberniadiscord.events.ServerStopEvent;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.CheckReturnValue;
import java.io.File;
import java.util.Objects;

public class HiberniaDiscord extends JavaPlugin {

    final private static Logger logger = LoggerFactory.getLogger("io.paradaux.hibernia"
            + "discord");
    public static Logger getMainLogger() { return logger; }

    private static ConfigurationCache configurationCache;
    public static ConfigurationCache getConfigurationCache() { return configurationCache; }

    private static LocaleCache localeCache;
    public static LocaleCache getLocaleCache() { return localeCache; }

    private static Plugin plugin;
    public static Plugin getPlugin() { return plugin; }

    private static Discord2Mc discord2Mc;

    private static TaskChainFactory taskChainFactory;
    public static <T> TaskChain<T> newChain() { return taskChainFactory.newChain(); }

    @Override
    public void onEnable() {
        taskChainFactory = BukkitTaskChainFactory.create(this);
        plugin = this;

        ConfigurationUtils.updateConfigurationFile(this.getConfig());
        configurationCache = new ConfigurationCache(this.getConfig());

        // Test validity of webhook, if invalid; disable the plugin.
        if (!testWebhookURL()) {
            logger.error("Your webhook url is invalid! Please see: https://github"
                    + ".com/ParadauxIO/HiberniaDiscord/wiki/Creating-a-webhook\n for instructions"
                    + " as to how to create one!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Prepare event listeners, so they can talk to discord.
        WebhookListener.createClient();

        ConfigurationUtils.deployLocale(this);
        localeCache = new LocaleCache(ConfigurationUtils.getLocale());

        ConfigurationUtils.deployDiscord2McConfiguration(this);
        discord2Mc = new Discord2Mc(ConfigurationUtils.getDiscord2McConfigurationFile());

        getLogger().info(localeCache.getLoadingMessage());

        registerCommands();
        registerEvents(getServer().getPluginManager());

        // bStats: I respect your decision to disable this!
        // See config.yml for my statement.
        if (!getConfig().getBoolean("settings.bstats")) return;
        Metrics metrics = new Metrics(this, 8386);

        new VersionChecker(this, 67795).getVersion(version -> {
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
        getLogger().info(localeCache.getShutdownMessage());
        Bukkit.getPluginManager().callEvent(new ServerStopEvent());
    }

    public void registerCommands() {
        this.getCommand("hiberniadiscord").setExecutor(new HiberniaDiscordCmd());
        this.getCommand("hdiscord").setExecutor(new HiberniaDiscordCmd());
        this.getCommand("discord").setExecutor(new DiscordCmd());
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
        File configurationFile = new File(Objects.requireNonNull(Bukkit.getServer()
                .getPluginManager().getPlugin("Hiber" + "niaDiscord")).getDataFolder(),
                "config.yml");
        configurationCache = new ConfigurationCache(YamlConfiguration
                .loadConfiguration(configurationFile));
    }

    public static void updateLocaleCache() {
        File localeFile = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager()
                .getPlugin("HiberniaDiscord")).getDataFolder(), "locale.yml");
        localeCache = new LocaleCache(YamlConfiguration.loadConfiguration(localeFile));
    }

    @CheckReturnValue
    private boolean testWebhookURL() {
        logger.info("Testing validity of your webhook.");
        try (WebhookClient client =
                     WebhookClient.withUrl(configurationCache.getDiscordWebhookUrl())) {
            return true;
        } catch (IllegalArgumentException e) {
            logger.warn("You have not setup your webhook in the configuration file!");
            return false;
        }
    }


}
