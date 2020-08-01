/*
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 */

package io.paradaux.hiberniadiscord.commands;

import io.paradaux.hiberniadiscord.HiberniaDiscord;
import io.paradaux.hiberniadiscord.api.EventUtils;
import io.paradaux.hiberniadiscord.api.LocaleCache;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HiberniaDiscordCMD implements CommandExecutor {

    LocaleCache locale = HiberniaDiscord.getLocaleCache();

    public String colorise(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(args.length == 0)) {
            if ("reload".equals(args[0])) {
                HiberniaDiscord.updateConfigurationCache();
                HiberniaDiscord.updateLocaleCache();
                sender.sendMessage(colorise(EventUtils.parsePlaceholders(locale, locale.getHiberniaDiscordReloadSuccess())));
                return true;
            }
        }
        sender.sendMessage(colorise(EventUtils.parsePlaceholders(locale, locale.getHiberniaDiscordDefault())));
        return true;
    }

}
