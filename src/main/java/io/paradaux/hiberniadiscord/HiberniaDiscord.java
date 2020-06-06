package io.paradaux.hiberniadiscord;

import io.paradaux.hiberniadiscord.WebhookUtils.WebhookClient;
import io.paradaux.hiberniadiscord.api.ConfigurationCache;
import io.paradaux.hiberniadiscord.api.ConfigurationUtils;
import io.paradaux.hiberniadiscord.commands.discordCMD;
import io.paradaux.hiberniadiscord.commands.hiberniadiscordCMD;
import io.paradaux.hiberniadiscord.events.ServerStopEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HiberniaDiscord extends JavaPlugin {

    private static Logger logger = Logger.getLogger("io.paradaux.hiberniadiscord.io.paradaux.hiberniadiscord.HiberniaDiscord");

    public static Logger getMainLogger() { return logger; }

    private static WebhookClient webhookClient;
    public static WebhookClient getWebhookClient() { return webhookClient; }

    private static io.paradaux.hiberniadiscord.api.ConfigurationCache configurationCache;
    public static ConfigurationCache getConfigurationCache() { return configurationCache; }

    private static Plugin plugin;
    public static Plugin getPlugin() { return plugin; }

    @Override
    public void onEnable() {

        ConfigurationUtils.checkIfOutOfDate(this.getConfig());


        if (this.getConfig().getDouble("config-version") == 2.1d) {
        }

        plugin = this;
        configurationCache = new ConfigurationCache(this.getConfig());
    }

    @Override
    public void onDisable() {
        Bukkit.getPluginManager().callEvent(new ServerStopEvent());
    }

    public void registerCommands() {
        this.getCommand("discord").setExecutor(new discordCMD());
        this.getCommand("hiberniadiscord").setExecutor(new hiberniadiscordCMD());
    }
    public void registerEvents() {

    }



}
