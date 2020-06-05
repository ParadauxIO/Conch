package io.paradaux.hiberniadiscord;

import io.paradaux.hiberniadiscord.WebhookUtils.WebhookClient;
import io.paradaux.hiberniadiscord.events.ServerStopEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.Configuration;
import java.util.logging.Logger;

public class HiberniaDiscord extends JavaPlugin {

    private static Logger logger = Logger.getLogger("io.paradaux.hiberniadiscord.io.paradaux.hiberniadiscord.HiberniaDiscord");

    public static Logger getMainLogger() { return logger; }

    private static WebhookClient webhookClient;
    public static WebhookClient getWebhookClient() { return webhookClient; }

    private static io.paradaux.hiberniadiscord.api.ConfigurationCache configurationCache;
    public static ConfigurationCache getConfigurationCache() { return ConfigurationCache;
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        Bukkit.getPluginManager().callEvent(new ServerStopEvent());
    }

    public void registerCommands() {}
    public void registerEvents() {}



}
