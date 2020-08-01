package io.paradaux.hiberniadiscord;

import io.paradaux.hiberniadiscord.EventListeners.*;
import io.paradaux.hiberniadiscord.api.ConfigurationCache;
import io.paradaux.hiberniadiscord.api.ConfigurationUtils;
import io.paradaux.hiberniadiscord.api.LocaleCache;
import io.paradaux.hiberniadiscord.api.VersionChecker;
import io.paradaux.hiberniadiscord.commands.HiberniaDiscordCMD;
import io.paradaux.hiberniadiscord.events.ServerStopEvent;
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


    @Override
    public void onEnable() {
        plugin = this;

        ConfigurationUtils.updateConfigurationFile(this.getConfig());
        configurationCache = new ConfigurationCache(this.getConfig());

        ConfigurationUtils.deployLocale(this);
        localeCache = new LocaleCache(ConfigurationUtils.getLocale());

        getLogger().info(localeCache.getLoadingMessage());

        registerCommands();
        registerEvents(getServer().getPluginManager());

        versionChecker();

    }

    @Override
    public void onDisable() {
        getLogger().info(localeCache.getShutdownMessage());
        Bukkit.getPluginManager().callEvent(new ServerStopEvent());
    }

    public void registerCommands() {
        this.getCommand("hiberniadiscord").setExecutor(new HiberniaDiscordCMD());
    }
    public void registerEvents(PluginManager pm) {
        pm.registerEvents(new AsyncPlayerChatEventListener(), this);
        pm.registerEvents(new PlayerAdvancementDoneEventListener(), this);
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
