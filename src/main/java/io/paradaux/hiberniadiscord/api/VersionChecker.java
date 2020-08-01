

package io.paradaux.hiberniadiscord.api;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Consumer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

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

}
