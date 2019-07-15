package co.paradaux.hdiscord.utils;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import co.paradaux.hdiscord.core.Configuration;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ninja.egg82.service.ServiceLocator;
import ninja.egg82.service.ServiceNotFoundException;
import org.bukkit.ChatColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceUtil {
    private static final Logger logger = LoggerFactory.getLogger(ServiceUtil.class);

    // Ripped from JDA
    private static final Pattern WEBHOOK_PATTERN = Pattern.compile("(?:https?://)?(?:\\w+\\.)?discordapp\\.com/api(?:/v\\d+)?/webhooks/(\\d+)/([\\w-]+)(?:/(?:\\w+)?)?");

    private ServiceUtil() {}

    public static void registerDiscord() {
        Configuration config;
        try {
            config = ServiceLocator.get(Configuration.class);
        } catch (InstantiationException | IllegalAccessException | ServiceNotFoundException ex) {
            logger.error(ex.getMessage(), ex);
            return;
        }

        String webhookURL = config.getNode("discord", "webhook-url").getString("");
        Matcher matcher = WEBHOOK_PATTERN.matcher(webhookURL);

        if (!matcher.matches()) {
            logger.error(LogUtil.getHeading() + ChatColor.RED + "A valid Discord webhook URL was not found in the config. Please add one and then reload the plugin.");
            return;
        }

        ServiceLocator.register(
                new WebhookClientBuilder(webhookURL)
                        .setDaemon(true)
                        .setThreadFactory(new ThreadFactoryBuilder().setNameFormat("HiberniaDiscord-%d").build())
                        .build()
        );
    }

    public static void unregisterDiscord() {
        Set<? extends WebhookClient> clients = ServiceLocator.remove(WebhookClient.class);
        for (WebhookClient client : clients) {
            client.close();
        }
    }
}
