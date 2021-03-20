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

package io.paradaux.conch.common.bot.listeners;

import co.aikar.taskchain.TaskChainFactory;
import io.paradaux.conch.common.api.config.CachedBotSettings;
import io.paradaux.conch.common.api.events.DiscordMessageReceivedEvent;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DiscordMessageListener extends ListenerAdapter {

    


    TaskChainFactory taskChainFactory;
    String messageFormat;
    List<String> monitoredChannels;
    boolean ignoreBots;

    /**
     * Handles discord messages coming in.
     * */
    public DiscordMessageListener(CachedBotSettings config) {

    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Member member = event.getMember();

        // If the message wasn't in a monitored channel, ignore it.
        if (!monitoredChannels.contains(event.getChannel().getId())) {
            return;
        }

        // If ignore bots is enabled, respect it.
        if (event.getAuthor().isBot() && ignoreBots) {
            return;
        }

        taskChainFactory.newChain().async(() -> {
            String message = event.getMessage().getContentStripped();

            // Member is null when the event is triggered by a webhook rather than a bot/user
            if (member == null) {
                return;
            }

            Role mainRole = getHighestFrom(member);

            DiscordMessageReceivedEvent messageEvent = new DiscordMessageReceivedEvent()
                    .setUsername(event.getAuthor().getName())
                    .setNickname(member.getNickname() == null ? "" : member.getNickname())
                    .setGuildName(event.getMessage().getGuild().getName())
                    .setRole(mainRole == null ? "" : mainRole.getName());


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
