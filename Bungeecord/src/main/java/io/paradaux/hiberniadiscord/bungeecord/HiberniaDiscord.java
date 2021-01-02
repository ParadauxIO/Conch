package io.paradaux.hiberniadiscord.bungeecord;

import io.paradaux.hiberniadiscord.bungeecord.listeners.ChatEventListener;
import io.paradaux.hiberniadiscord.common.api.DiscordManager;
import net.md_5.bungee.api.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public final class HiberniaDiscord extends Plugin {

    private static Logger logger;

    List<String> monitoredChannels = new ArrayList<>();

    String webhookUrl = "https://discord.com/api/webhooks/763065395833602048/xUUX016wrPYPGWRJyfsGaDtwtxHJFrbWHrEfj4XMa5PvFT0jSc-kgcA9qF3ZP9cpH5Mv";

    String iconUrl = "https://cdn.paradaux.io/static/plugin-branding/hiberniadiscord/hibernia-discord.png";
    String token = "";
    String messageFormat = "";


    @Override
    public void onEnable() {
        // Plugin startup logic
        logger = LoggerFactory.getLogger("io.paradaux.hiberniadiscord");

//        DiscordManager.initialise(webhookUrl, true, logger);
//        DiscordManager.sendDiscordMessage("Test", iconUrl, "hello world");

        registerEvents();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerEvents() {
        String avatarApiUrl = "https://mc-heads.net/combo/%playeruuid%";
        String userNameFormat = "%playername%";
        String serverName = "Test Server";
        String messagePrefix = null;

        getProxy().getPluginManager().registerListener(this,
                new ChatEventListener(avatarApiUrl, userNameFormat, serverName, messagePrefix,
                                      logger, true));

    }

}
