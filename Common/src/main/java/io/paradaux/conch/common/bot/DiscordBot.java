package io.paradaux.conch.common.bot;

import io.paradaux.conch.common.api.I18NLogger;
import io.paradaux.conch.common.api.StringUtils;
import io.paradaux.conch.common.api.config.CachedBotSettings;
import io.paradaux.conch.common.api.config.ConfigurationUtil;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;

public class DiscordBot {

    private final String token;
    private JDA client;

    public DiscordBot() throws LoginException {
        CachedBotSettings config = ConfigurationUtil.getBotSettings();
        this.token = config.getToken();
    }

    public void addNewListener(EventListener listener) {
        I18NLogger.rawInfo("Registered new event listener: " + listener.getClass().getSimpleName());
        client.addEventListener(listener);
    }

    public void connect() throws LoginException {
        client = login(token);
    }

    private JDA login(String token) throws LoginException {
        // TODO Envoirnment varialbe support
        JDABuilder builder = JDABuilder.createDefault(token)
                .setBulkDeleteSplittingEnabled(false)
                .addEventListeners(new ReadyListener());

        return builder.build();
    }

    private static class ReadyListener extends ListenerAdapter {

        @Override
        public void onReady(@NotNull ReadyEvent event) {

            int guildCount = event.getGuildAvailableCount();
            int userCount = 0;

            for (Guild guild : event.getJDA().getGuilds()) {
                userCount += guild.getMemberCount();
            }

            I18NLogger.rawInfo("Bot Started: Serving {} user{} in {} guild{}", String.valueOf(userCount), StringUtils.pluralise(userCount),
                    String.valueOf(guildCount), StringUtils.pluralise(guildCount)); // TODO ADD TO LOCALE
        }

    }


}
