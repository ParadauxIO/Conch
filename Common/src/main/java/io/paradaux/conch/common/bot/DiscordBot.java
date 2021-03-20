package io.paradaux.conch.common.bot;

import io.paradaux.conch.common.api.config.CachedBotSettings;
import io.paradaux.conch.common.api.config.ConfigurationUtil;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DiscordBot {

    private final List<Object> listeners;
    private final String token;
    private JDA client;

    public DiscordBot() throws LoginException {
        CachedBotSettings config = ConfigurationUtil.getBotSettings();
        this.token = config.getToken();
        this.listeners = new ArrayList<>();
        createDefaultListeners();
        reconnect();
    }

    public void addNewListener(Object listener) {
        listeners.add(listener);
    }

    public void reconnect() throws LoginException {
        client = login(token, listeners);
    }

    public void createDefaultListeners() {
        Collections.addAll(listeners, new MessageEventListener());
    }

    private JDA login(String token, List<Object> listeners) throws LoginException {

        JDABuilder builder = JDABuilder.createDefault(token, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setBulkDeleteSplittingEnabled(false)
                .addEventListeners(listeners);

        return builder.build();
    }

}
