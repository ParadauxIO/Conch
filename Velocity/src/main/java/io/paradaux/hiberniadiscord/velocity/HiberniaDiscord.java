package io.paradaux.hiberniadiscord.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import io.paradaux.hiberniadiscord.velocity.listeners.PlayerChatEventListener;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Plugin(id = "hiberniadiscord", name = "HiberniaDiscord", version = "4.0.0",
        url = "https://hiberniadiscord.paradaux.io", description = "Discord to chat Bridge", authors = {"Paradaux"})
public class HiberniaDiscord {


    private final ProxyServer server;
    private final Logger logger;
    private final Path dataDirectory;

    List<String> monitoredChannels = new ArrayList<>();

    String webhookUrl = "https://discord.com/api/webhooks/763065395833602048/xUUX016wrPYPGWRJyfsGaDtwtxHJFrbWHrEfj4XMa5PvFT0jSc-kgcA9qF3ZP9cpH5Mv";

    String iconUrl = "https://cdn.paradaux.io/static/plugin-branding/hiberniadiscord/hibernia-discord.png";
    String token = "";
    String messageFormat = "";

    @Inject
    public HiberniaDiscord(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        this.server = server;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {

//        DiscordManager.initialise(webhookUrl, true, logger);
//        DiscordManager.sendDiscordMessage("Test", iconUrl, "hello world");

        registerEvents();
    }

    public void registerEvents() {
        String avatarApiUrl = "https://mc-heads.net/combo/%playeruuid%";
        String userNameFormat = "%playername%";
        String serverName = "Test Server";
        String messagePrefix = null;

        server.getEventManager().register(this, new PlayerChatEventListener(avatarApiUrl, userNameFormat, serverName, messagePrefix,
                logger, true));
    }
}
