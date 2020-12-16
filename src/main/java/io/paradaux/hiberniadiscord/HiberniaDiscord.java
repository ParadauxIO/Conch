/*
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 */

package io.paradaux.hiberniadiscord;

import club.minnced.discord.webhook.WebhookClient;
import co.aikar.taskchain.TaskChain;
import co.aikar.taskchain.TaskChainFactory;
import io.paradaux.hiberniadiscord.models.Locale;
import io.paradaux.hiberniadiscord.api.VersionChecker;
import io.paradaux.hiberniadiscord.commands.DiscordCmd;
import io.paradaux.hiberniadiscord.commands.HiberniaDiscordCmd;
import io.paradaux.hiberniadiscord.controllers.*;
import io.paradaux.hiberniadiscord.discord2mc.Discord2Mc;
import io.paradaux.hiberniadiscord.eventlisteners.*;
import io.paradaux.hiberniadiscord.events.ServerStopEvent;
import io.paradaux.hiberniadiscord.models.PluginConfiguration;
import io.paradaux.hiberniadiscord.webhookutils.GenericWebhook;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

import javax.annotation.CheckReturnValue;
import java.io.File;
import java.util.Objects;

public class HiberniaDiscord extends JavaPlugin {

    private static PluginConfiguration pluginConfiguration;
    private static TaskChainFactory taskChainFactory;
    private static Locale localeCache;
    private static Discord2Mc discord2Mc;
    private static Plugin plugin;

    @Override
    public void onEnable() {
        LogController.createLogger(getConfig().getBoolean("settings.debug"));
        Logger logger = LogController.getLogger();

        new ConfigurationController();
        new MetricsController();
        new TaskController();
        new VersionNotificationController();

        GenericWebhook.setWebhookUrl(ConfigurationController.getPluginConfiguration()
                .getDiscordWebhookUrl());

        GenericWebhook.createClient();

        plugin = this;




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
        localeCache = new Locale(ConfigurationUtils.getLocale());

        ConfigurationUtils.deployDiscord2McConfiguration(this);
        discord2Mc = new Discord2Mc(ConfigurationUtils.getDiscord2McConfigurationFile());

        getLogger().info(localeCache.getLoadingMessage());

        registerCommands();
        registerEvents(getServer().getPluginManager());

        new VersionChecker(this, 67795).getVersion(version -> {
            if (!this.getDescription().getVersion().equalsIgnoreCase(version)) {
                logger.info("There is a new update available. \n Please update: "
                        + "https://www.spigotmc.org/resources/hiberniadiscord-%C2%BB-chat-to-disco"
                        + "rd-integration.67795/");
                return;
            }
            logger.info("There are no new updates available");
        });


        // bStats: I respect your decision to disable this!
        if (getConfig().getBoolean("settings.bstats")) {
            Metrics metrics = new Metrics(this, 8386);
            metrics.addCustomChart(new Metrics.SimplePie("using_discord2mc",
                    () -> getConfig().getString("language", "en")));
            metrics.addCustomChart(new Metrics.SimplePie("using_discord_commands",
                    () -> getConfig().getString("language", "en")));

        }






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
        localeCache = new Locale(YamlConfiguration.loadConfiguration(localeFile));
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

    public static ConfigurationCache getConfigurationCache() {
        return configurationCache;
    }

    public static Locale getLocaleCache() {
        return localeCache;
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    public static <T> TaskChain<T> newChain() {
        return taskChainFactory.newChain();
    }


}
