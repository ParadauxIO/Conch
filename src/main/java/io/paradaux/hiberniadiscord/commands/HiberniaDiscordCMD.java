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

        if (!sender.hasPermission("hiberniadiscord.admin")) {
            sender.sendMessage(LocaleCache.colorise(locale.getSevereNoPermission()));
            return true;
        }

        if (args.length <= 0) {
            sender.sendMessage(colorise(EventUtils.parsePlaceholders(locale, locale.getHiberniaDiscordDefault())));
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload": {
                HiberniaDiscord.updateConfigurationCache();
                HiberniaDiscord.updateLocaleCache();
                sender.sendMessage(colorise(EventUtils.parsePlaceholders(locale, locale.getHiberniaDiscordReloadSuccess())));
                return true;
            }

            // TODO: implement
            case "discord2mc": {
                sender.sendMessage(ChatColor.RED + "Unimplemented.");
            }

            default: {
                sender.sendMessage(ChatColor.RED + "/hdiscord <reload/discord2mc>");
                return true;
            }
        }


    }

}
