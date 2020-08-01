package io.paradaux.hiberniadiscord.commands;

import io.paradaux.hiberniadiscord.HiberniaDiscord;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HiberniaDiscordCMD implements CommandExecutor {

    // Need locale cache to be done to make it pretty
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args[0].equalsIgnoreCase("reload")) {
            HiberniaDiscord.updateConfigurationCache();
            sender.sendMessage("Reloaded Configuration.");
        }
        System.out.println(Bukkit.getBukkitVersion());
        System.out.println(Bukkit.getVersion());
        return true;
    }

}
