package co.paradaux.hdiscord.utils;

import co.paradaux.hdiscord.core.CachedConfigValues;
import co.paradaux.hdiscord.core.Configuration;
import java.io.*;
import java.nio.file.Files;
import ninja.egg82.service.ServiceLocator;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.DumperOptions;

public class ConfigurationFileUtil {
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationFileUtil.class);

    private ConfigurationFileUtil() {}

    public static void reloadConfig(Plugin plugin) {
        Configuration config;
        try {
            config = getConfig(plugin, "config.yml", new File(plugin.getDataFolder(), "config.yml"));
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
            return;
        }

        boolean debug = config.getNode("debug").getBoolean(false);

        if (debug) {
            logger.info(LogUtil.getHeading() + ChatColor.YELLOW + "Debug " + ChatColor.WHITE + "enabled");
        }

        String avatarURL = config.getNode("avatar", "url").getString("https://crafatar.com/renders/head/");
        if (avatarURL.charAt(avatarURL.length() - 1) != '/') {
            avatarURL = avatarURL + "/";
        }

        String avatarOptions = config.getNode("avatar", "options").getString("size=10&overlay");
        if (avatarOptions.charAt(0) == '&') {
            avatarOptions = avatarOptions.substring(1);
        }
        if (avatarOptions.charAt(0) != '?') {
            avatarOptions = "?" + avatarOptions;
        }

        // v2.1 Specific Strings

        String serverIcon = config.getNode("avatar", "server-icon").getString("https://paradaux.co/hiberniadiscord/default_servericon.png");
        String joinEventMsg = config.getNode("events", "player-join").getString("");
        String leaveEventMsg = config.getNode("events", "player-leave").getString("");



        if (debug) {
            logger.info(LogUtil.getHeading() + ChatColor.YELLOW + "Avatar URL: " + ChatColor.WHITE + avatarURL);
            logger.info(LogUtil.getHeading() + ChatColor.YELLOW + "Avatar options: " + ChatColor.WHITE + avatarOptions);
        }

        CachedConfigValues cachedValues = CachedConfigValues.builder()
                .debug(debug)
                .avatarURL(avatarURL)
                .avatarOptions(avatarOptions)
                .serverIcon(serverIcon)
                .joinEventMsg(joinEventMsg)
                .leaveEventMsg(leaveEventMsg)
                .build();

        ServiceLocator.register(config);
        ServiceLocator.register(cachedValues);

        if (debug) {
            logger.info(LogUtil.getHeading() + ChatColor.YELLOW + "Discord webhook URL: " + config.getNode("discord", "webhook-url").getString(""));
        }
    }

    public static Configuration getConfig(Plugin plugin, String resourcePath, File fileOnDisk) throws IOException {
        File parentDir = fileOnDisk.getParentFile();
        if (parentDir.exists() && !parentDir.isDirectory()) {
            Files.delete(parentDir.toPath());
        }
        if (!parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                throw new IOException("Could not create parent directory structure.");
            }
        }
        if (fileOnDisk.exists() && fileOnDisk.isDirectory()) {
            Files.delete(fileOnDisk.toPath());
        }

        if (!fileOnDisk.exists()) {
            try (InputStreamReader reader = new InputStreamReader(plugin.getResource(resourcePath));
                 BufferedReader in = new BufferedReader(reader);
                 FileWriter writer = new FileWriter(fileOnDisk);
                 BufferedWriter out = new BufferedWriter(writer)) {
                String line;
                while ((line = in.readLine()) != null) {
                    out.write(line + System.lineSeparator());
                }
            }
        }

        ConfigurationLoader<ConfigurationNode> loader = YAMLConfigurationLoader.builder().setFlowStyle(DumperOptions.FlowStyle.BLOCK).setIndent(2).setFile(fileOnDisk).build();
        ConfigurationNode root = loader.load(ConfigurationOptions.defaults().setHeader("Comments are gone because update :(. Click here for new config + comments: https://www.spigotmc.org/resources/hiberniadiscord-%C2%BB-chat-discord-integration.67795/"));
        Configuration config = new Configuration(root);
        ConfigurationVersionUtil.conformVersion(loader, config, fileOnDisk);

        return config;
    }
}
