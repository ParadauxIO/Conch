

package io.paradaux.hiberniadiscord.api;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Consumer;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Logger;

public class VersionChecker {

    Plugin plugin;
    int resourceId;

    public VersionChecker(Plugin plugin, int resourceId) {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

    public VersionChecker(int resourceId) {
        this.plugin = null;
        this.resourceId = resourceId;
    }

    public void getVersion(final Consumer<String> consumer) {
        if (plugin != null) {
            Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
                try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream();
                     Scanner scanner = new Scanner(inputStream)) {
                    if (scanner.hasNext()) {
                        consumer.accept(scanner.next());
                    }
                } catch (IOException exception) {
                    this.plugin.getLogger().info("Cannot look for updates: " + exception.getMessage());
                }
            });
        } else {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream();
                 Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch (IOException exception) {
                System.out.println("Cannot look for updates: " + exception.getMessage());
            }
        }
    }

    public void getGsonFromJson(InputStreamReader reader) {
        Logger logger = Bukkit.getLogger();

        Gson gson = new Gson();

        try {
            gson.fromJson(reader.toString(), versionJson.class);
        } catch (JsonSyntaxException e) {
            logger.severe(e.getMessage());
        }

    }

    @Nullable
    public InputStreamReader gsonFromUrl(String urlString) {

        Logger logger = Bukkit.getLogger();

        try {

            URL url = null;

            // Validate URL
            try {
                url = new URL(urlString);
            } catch (MalformedURLException e) {
                logger.severe(e.getMessage());
                return null;
            }

            InputStreamReader reader;

            // Validate stream
            try {
                reader = new InputStreamReader(url.openStream());
            } catch (IOException e) {
                logger.severe(e.getMessage());
                return null;
            }

            return reader;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Gson Models
    private class versionJson {
        String version;
        HiberniaDiscordVersion hiberniaDiscordVersion;
    }

    private class HiberniaDiscordVersion {
        String version, releaseDate;
    }

}
