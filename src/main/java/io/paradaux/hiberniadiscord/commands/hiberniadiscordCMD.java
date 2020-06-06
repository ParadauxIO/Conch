package io.paradaux.hiberniadiscord.commands;

import io.paradaux.hiberniadiscord.api.ConfigurationUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class hiberniadiscordCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args[0] == "reload") {
            ConfigurationUtils.reloadConfig();
            sender.sendMessage("");
        }

        return false;
    }

}
