/*
 * Copyright (c) 2021, Rían Errity. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 3 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 3 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 3 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Rían Errity <rian@paradaux.io> or visit https://paradaux.io
 * if you need additional information or have any questions.
 * See LICENSE.md for more details.
 */

package io.paradaux.conch.bukkit.bot;

import io.paradaux.conch.bukkit.managers.TaskManager;
import io.paradaux.conch.common.api.DiscordManager;
import io.paradaux.conch.common.api.config.CachedBotSettings;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DiscordMessageListener extends ListenerAdapter {

    private final DiscordManager discord;
    private final CachedBotSettings config;
    private final TaskManager tasks;

    /**
     * Handles discord messages coming in.
     * */
    public DiscordMessageListener(DiscordManager discord, TaskManager tasks, CachedBotSettings config) {
        this.discord = discord;
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

        tasks.newChain().async(() -> {
            // Member is null when the event is triggered by a webhook rather than a bot/user
            if (member == null) {
                return;
            }

            Role highestRole = getHighestFrom(member);

            String broadcast = config.getMessageFormat()
                    .replace("%discord_nickname%", member.getEffectiveName())
                    .replace("%discord_tag%", event.getAuthor().getAsTag())
                    .replace("%discord_username%", event.getAuthor().getName())
                    .replace("%discord_discriminator%", event.getAuthor().getDiscriminator())
                    .replace("%discord_message%", event.getMessage().getContentStripped())
                    .replace("%discord_topRole%",  highestRole == null ? "" : highestRole.getName())
                    .replace("%discord_channel%", event.getChannel().getName())
                    .replace("%discord_guild%", event.getGuild().getName());

            Bukkit.getServer().broadcastMessage(broadcast);
        }).execute();
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

