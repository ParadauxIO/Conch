package io.paradaux.hiberniadiscord.discord2mc.listeners;

import io.paradaux.hiberniadiscord.HiberniaDiscord;
import io.paradaux.hiberniadiscord.discord2mc.api.Discord2McConfigurationCache;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.utils.Checks;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MessageListener extends ListenerAdapter {

    Discord2McConfigurationCache configurationCache;

    List<String> monitoredChannels;

    public MessageListener(Discord2McConfigurationCache configurationCache) {
        this.configurationCache = configurationCache;
        monitoredChannels = configurationCache.getMonitoredChannels();
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        HiberniaDiscord.ne
        Member member = event.getMember();

        if (!monitoredChannels.contains(event.getChannel().getId())) return; // If the message wasn't in a monitored channel, ignore it.
        if (event.getAuthor().isBot() && !configurationCache.isDoSendBotMessages()) return; // If ignore bots is enabled, respect it.
        if (member == null) return; // The message received event is triggered by the webhook, and thus has to ignore webhooks.


        String username = event.getAuthor().getName();
        String discriminator = event.getAuthor().getDiscriminator(); // The numbers after a user's username (after the #)
        String guildname = event.getGuild().getName();
        String message = event.getMessage().getContentDisplay();
        String channel = event.getMessage().getChannel().getName();
        String nickname = member.getNickname() != null ? member.getNickname() : ""; // If the user doesn't have a username, let it be empty string.

        Role mainRole = getHighestFrom(member);
        String role = mainRole != null ? mainRole.getName() : "";

        String chatMessage = configurationCache.getMessageFormat();
        chatMessage = ChatColor.translateAlternateColorCodes('&', chatMessage);
        chatMessage = chatMessage.replace("%username%", username);
        chatMessage = chatMessage.replace("%discriminator%", discriminator);
        chatMessage = chatMessage.replace("%nickname%", nickname);
        chatMessage = chatMessage.replace("%guildname%", guildname);
        chatMessage = chatMessage.replace("%message%", message);
        chatMessage = chatMessage.replace("%channel%", channel);
        chatMessage = chatMessage.replace("%mainrole%", role);

        Bukkit.broadcastMessage(chatMessage);

    }

    public Role getHighestFrom(Member member) {
        Checks.notNull(member, "Member object can not be null");

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
