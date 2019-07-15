package co.paradaux.hdiscord.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import co.aikar.taskchain.TaskChainFactory;
import co.paradaux.hdiscord.commands.internal.ReloadCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

@CommandAlias("discord|hdiscord|hiberniadiscord")
public class DiscordCommand extends BaseCommand {
    private final Plugin plugin;
    private final TaskChainFactory taskFactory;

    public DiscordCommand(Plugin plugin, TaskChainFactory taskFactory) {
        this.plugin = plugin;
        this.taskFactory = taskFactory;
    }

    @Subcommand("reload")
    @CommandPermission("hdiscord.admin")
    @Description("Reloads the plugin.")
    public void onReload(CommandSender sender) {
        new ReloadCommand(plugin, taskFactory.newChain(), sender).run();
    }

    @CatchUnknown @Default
    @CommandCompletion("@subcommand")
    public void onDefault(CommandSender sender, String[] args) {
        Bukkit.getServer().dispatchCommand(sender, "discord help");
    }

    @HelpCommand
    @Syntax("[command]")
    public void onHelp(CommandSender sender, CommandHelp help) {
        help.showHelp();
    }
}
