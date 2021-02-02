package io.paradaux.hiberniadiscord.sponge;

import com.google.inject.Inject;
import io.paradaux.hiberniadiscord.sponge.listeners.MessageChannelEventChatListener;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Plugin(id = "hiberniadiscord", name = "HiberniaDiscord", version = "4.0.0", description = "Discord to chat Bridge")
public class HiberniaDiscord {

    @Inject
    private Logger logger;

    @Inject
    private Game game;

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path configDir;

    List<String> monitoredChannels = new ArrayList<>();

    String webhookUrl = "https://discord.com/api/webhooks/763065395833602048/xUUX016wrPYPGWRJyfsGaDtwtxHJFrbWHrEfj4XMa5PvFT0jSc-kgcA9qF3ZP9cpH5Mv";

    String iconUrl = "https://cdn.paradaux.io/static/plugin-branding/hiberniadiscord/hibernia-discord.png";
    String token = "";
    String messageFormat = "";


    @Listener
    public void onServerStart(GameStartedServerEvent event) {
//        DiscordManager.initialise(webhookUrl, true, logger);
//        DiscordManager.sendDiscordMessage("Test", iconUrl, "hello world");

        registerEvents();
    }

    public void registerEvents() {
        String avatarApiUrl = "https://mc-heads.net/combo/%playeruuid%";
        String userNameFormat = "%playername%";
        String serverName = "Test Server";
        String messagePrefix = null;
        Sponge.getEventManager().registerListeners(this, new MessageChannelEventChatListener(avatarApiUrl, userNameFormat, serverName, messagePrefix,
                logger, true));
    }
}
