package io.paradaux.conch.velocity.bot;

import io.paradaux.conch.common.api.config.CachedBotSettings;
import io.paradaux.conch.velocity.managers.TaskManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DiscordMessageListener extends ListenerAdapter {

    private final CachedBotSettings config;
    private final TaskManager tasks;

    /**
     * Handles discord messages coming in.
     * */
    public DiscordMessageListener(TaskManager tasks, CachedBotSettings config) {

        this.tasks = tasks;
        this.config = config;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Member member = event.getMember();

        // If the message wasn't in a monitored channel, ignore it.
        if (!config.getMonitoredChannels().contains(event.getChannel().getId())) {
            return;
        }

        // If ignore bots is enabled, respect it.
        if (event.getAuthor().isBot() && !config.isSendBotMessages()) {
            return;
        }

        tasks.newTask(() -> {
            // Member is null when the event is triggered by a webhook rather than a bot/user
            if (member == null) {
                return;
            }

            Role highestRole = getHighestFrom(member);








            Bukkit.getServer().broadcastMessage(config.getMessageFormat()
                    .replace("%discord_nickname%", member.getEffectiveName())
                    .replace("%discord_tag%", event.getAuthor().getAsTag())
                    .replace("%discord_username%", event.getAuthor().getName())
                    .replace("%discord_discriminator%", event.getAuthor().getDiscriminator())
                    .replace("%discord_message%", event.getMessage().getContentStripped())
                    .replace("%discord_topRole%",  highestRole == null ? "" : highestRole.getName())
                    .replace("%discord_channel%", event.getChannel().getName())
                    .replace("%discord_guild%", event.getGuild().getName()));
        }).schedule();
    }

    /**
     * Determines the highest role (i.e the role displayed when the user speaks) of the member provided.
     * */
    @Nullable
    public Role getHighestFrom(Member member) {
        if (member == null) {
            return null;
        }

        List<Role> roles = member.getRoles();

        if (roles.isEmpty()) {
            return null;
        }

        return roles.stream().min((first, second) -> {
            if (first.getPosition() == second.getPosition()) {
                return 0;
            }

            return first.getPosition() > second.getPosition() ? -1 : 1;
        }).get();
    }

}
