/*
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 */

package io.paradaux.hiberniadiscord.commands;

import io.paradaux.hiberniadiscord.HiberniaDiscord;
import io.paradaux.hiberniadiscord.api.EventUtils;
import io.paradaux.hiberniadiscord.api.LocaleCache;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DiscordCMD implements CommandExecutor {

    LocaleCache locale = HiberniaDiscord.getLocaleCache();
    Player player;

    public String colorise(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return false;

        player = (Player) sender;

        BaseComponent[] messageText = TextComponent.fromLegacyText(colorise(EventUtils.parsePlaceholders(locale, locale.getDiscordDefault())));
        TextComponent message = new TextComponent(messageText);
        message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, locale.getDiscordLink()));

        player.sendMessage(message);
        return true;

    }

}
