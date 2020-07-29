package io.paradaux.hiberniadiscord;

import io.paradaux.hiberniadiscord.EventListeners.*;
import io.paradaux.hiberniadiscord.api.ConfigurationCache;
import io.paradaux.hiberniadiscord.api.ConfigurationUtils;
import io.paradaux.hiberniadiscord.commands.hiberniadiscordCMD;
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

    private static Plugin plugin;
    public static Plugin getPlugin() { return plugin; }

    @Override
    public void onEnable() {
        plugin = this;

        ConfigurationUtils.updateConfigurationFile(this.getConfig());

        configurationCache = new ConfigurationCache(this.getConfig());

        registerCommands();
        registerEvents(getServer().getPluginManager());

    }

    @Override
    public void onDisable() {
        Bukkit.getPluginManager().callEvent(new ServerStopEvent());
    }

    public void registerCommands() {
        this.getCommand("hiberniadiscord").setExecutor(new hiberniadiscordCMD());
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

}
