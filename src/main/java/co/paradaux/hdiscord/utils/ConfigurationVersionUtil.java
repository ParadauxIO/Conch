package co.paradaux.hdiscord.utils;

import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class ConfigurationVersionUtil {
    private ConfigurationVersionUtil() {}

    public static void conformVersion(ConfigurationLoader<ConfigurationNode> loader, ConfigurationNode config, File fileOnDisk) throws IOException {
        double oldVersion = config.getNode("version").getDouble(1.0d);

        if (config.getNode("version").getDouble(1.0d) == 1.0d) {
            to20(config);
        }
        if (config.getNode("version").getDouble() == 2.0d) {
            to21(config);
        }

        if (config.getNode("version").getDouble() != oldVersion) {
            File backupFile = new File(fileOnDisk.getParent(), fileOnDisk.getName() + ".bak");
            if (backupFile.exists()) {
                java.nio.file.Files.delete(backupFile.toPath());
            }

            Files.copy(fileOnDisk, backupFile);
            loader.save(config);
        }
    }

    private static void to20(ConfigurationNode config) {
        // config-version -> version
        config.removeChild("config-version");

        // webhook_url -> discord.webhook-url
        String webhookURL = config.getNode("webhook_url").getString("");
        config.removeChild("webhook_url");
        config.getNode("discord", "webhook-url").setValue(webhookURL);

        // crafatar_url -> avatar.url
        String craftarURL = config.getNode("craftar_url").getString("https://crafatar.com/renders/head/");
        config.removeChild("craftar_url");
        config.getNode("avatar", "url").setValue(craftarURL);

        // crafatar_options -> avatar.options
        String craftarOptions = config.getNode("craftar_options").getString("size=10&overlay");
        config.removeChild("craftar_options");
        config.getNode("avatar", "options").setValue(craftarOptions);

        // Add debug
        config.getNode("debug").setValue(Boolean.FALSE);

        // Version
        config.getNode("version").setValue(2.0d);
    }

    public static void to21(ConfigurationNode config) {

        // Server Icon under avatar
        config.getNode("avatar", "server-icon").setValue("https://paradaux.co/hiberniadiscord/default_servericon.png");

        // Events Section
        config.getNode("events", "join").setValue("Server » %player% has joined the game.");
        config.getNode("events", "leave").setValue("Server » %player% has left the game.");

        config.getNode("version").setValue(2.1d);

    }
}
