package io.paradaux.hiberniadiscord;

import co.aikar.commands.PaperCommandManager;
import co.aikar.commands.RegisteredCommand;
import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChainFactory;
import io.paradaux.hiberniadiscord.events.PlayerJoinEventHandler;
import io.paradaux.hiberniadiscord.events.PlayerLoginUpdateNotifyHandler;
import io.paradaux.hiberniadiscord.events.PlayerQuitEventHandler;
import io.paradaux.hiberniadiscord.hooks.PlaceholderAPIHook;
import co.paradaux.hdiscord.utils.ConfigurationFileUtil;
import io.paradaux.hiberniadiscord.utils.LogUtil;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.SetMultimap;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.paradaux.hiberniadiscord.events.AsyncPlayerChatHandler;
import ninja.egg82.events.BukkitEventSubscriber;
import ninja.egg82.events.BukkitEvents;
import ninja.egg82.service.ServiceLocator;
import ninja.egg82.service.ServiceNotFoundException;
import ninja.egg82.updater.SpigotUpdater;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ExecutorService workPool = Executors.newFixedThreadPool(1, new ThreadFactoryBuilder().setNameFormat("io.paradaux.hiberniadiscord.HiberniaDiscord-%d").build());

    private TaskChainFactory taskFactory;
    private PaperCommandManager commandManager;

    private List<BukkitEventSubscriber<?>> events = new ArrayList<>();

    private final Plugin plugin;
    private final boolean isBukkit;

    public Main(Plugin plugin) {
        isBukkit = Bukkit.getName().equals("Bukkit") || Bukkit.getName().equals("CraftBukkit");
        this.plugin = plugin;
    }

    public void onEnable() {
        taskFactory = BukkitTaskChainFactory.create(plugin);
        commandManager = new PaperCommandManager(plugin);
        commandManager.enableUnstableAPI("help");

        loadServices();
        loadCommands();
        loadEvents();
        loadHooks();

        plugin.getServer().getConsoleSender().sendMessage(LogUtil.getHeading() + ChatColor.GREEN + "Enabled");

        plugin.getServer().getConsoleSender().sendMessage(LogUtil.getHeading()
                        + ChatColor.YELLOW + "[" + ChatColor.AQUA + "Version " + ChatColor.WHITE + plugin.getDescription().getVersion() + ChatColor.YELLOW +  "] "
                        + ChatColor.YELLOW + "[" + ChatColor.WHITE + commandManager.getRegisteredRootCommands().size() + ChatColor.GOLD + " Commands" + ChatColor.YELLOW +  "] "
                        + ChatColor.YELLOW + "[" + ChatColor.WHITE + events.size() + ChatColor.BLUE + " Events" + ChatColor.YELLOW +  "]"
        );

        workPool.submit(this::checkUpdate);
    }

    public void onDisable() {
        taskFactory.shutdown(8, TimeUnit.SECONDS);
        commandManager.unregisterCommands();

        for (BukkitEventSubscriber<?> event : events) {
            event.cancel();
        }
        events.clear();

        unloadHooks();
        unloadServices();

        plugin.getServer().getConsoleSender().sendMessage(LogUtil.getHeading() + ChatColor.DARK_RED + "Disabled");
    }

    private void loadServices() {
        ConfigurationFileUtil.reloadConfig(plugin);
        ServiceUtil.registerDiscord();
        ServiceLocator.register(new SpigotUpdater(plugin, 67795));
    }

    private void loadCommands() {
        commandManager.getCommandCompletions().registerCompletion("subcommand", c -> {
            String lower = c.getInput().toLowerCase();
            Set<String> commands = new LinkedHashSet<>();
            SetMultimap<String, RegisteredCommand> subcommands = commandManager.getRootCommand("discord").getSubCommands();
            for (Map.Entry<String, RegisteredCommand> kvp : subcommands.entries()) {
                if (!kvp.getValue().isPrivate() && (lower.isEmpty() || kvp.getKey().toLowerCase().startsWith(lower)) && kvp.getValue().getCommand().indexOf(' ') == -1) {
                    commands.add(kvp.getValue().getCommand());
                }
            }
            return ImmutableList.copyOf(commands);
        });

        commandManager.registerCommand(new DiscordCommand(plugin, taskFactory));
    }

    private void loadEvents() {
        events.add(BukkitEvents.subscribe(plugin, AsyncPlayerChatEvent.class, EventPriority.LOWEST).handler(e -> new AsyncPlayerChatHandler().accept(e)));
        events.add(BukkitEvents.subscribe(plugin, PlayerLoginEvent.class, EventPriority.LOW).handler(e -> new PlayerLoginUpdateNotifyHandler(plugin).accept(e)));
        events.add(BukkitEvents.subscribe(plugin, PlayerJoinEvent.class, EventPriority.LOW).handler(e -> new PlayerJoinEventHandler().accept(e)));
        events.add(BukkitEvents.subscribe(plugin, PlayerQuitEvent.class, EventPriority.LOW).handler(e -> new PlayerQuitEventHandler().accept(e)));
        events.add(BukkitEvents.subscribe(plugin, PlayerQuitEvent.class, EventPriority.LOW).handler(e -> new PlayerQuitEventHandler().accept(e)));
    }

    private void loadHooks() {
        PluginManager manager = plugin.getServer().getPluginManager();

        if (manager.getPlugin("PlaceholderAPI") != null) {
            plugin.getServer().getConsoleSender().sendMessage(LogUtil.getHeading() + ChatColor.GREEN + "Enabling support for PlaceholderAPI.");
            ServiceLocator.register(new PlaceholderAPIHook());
        } else {
            plugin.getServer().getConsoleSender().sendMessage(LogUtil.getHeading() + ChatColor.YELLOW + "PlaceholderAPI was not found. Skipping support for placeholders.");
        }
    }

    private void checkUpdate() {
        Configuration config;
        SpigotUpdater updater;
        try {
            config = ServiceLocator.get(Configuration.class);
            updater = ServiceLocator.get(SpigotUpdater.class);
        } catch (InstantiationException | IllegalAccessException | ServiceNotFoundException ex) {
            logger.error(ex.getMessage(), ex);
            return;
        }

        if (!config.getNode("update", "check").getBoolean(true)) {
            return;
        }

        updater.isUpdateAvailable().thenAccept(v -> {
            if (!v) {
                return;
            }

            try {
                plugin.getServer().getConsoleSender().sendMessage(LogUtil.getHeading() + ChatColor.AQUA + " has an " + ChatColor.GREEN + "update" + ChatColor.AQUA + " available! New version: " + ChatColor.YELLOW + updater.getLatestVersion().get());
            } catch (ExecutionException ex) {
                logger.error(ex.getMessage(), ex);
            } catch (InterruptedException ex) {
                logger.error(ex.getMessage(), ex);
                Thread.currentThread().interrupt();
            }
        });

        try {
            Thread.sleep(60L * 60L * 1000L);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }

        workPool.submit(this::checkUpdate);
    }

    private void unloadHooks() {
        Set<? extends PluginHook> hooks = ServiceLocator.remove(PluginHook.class);
        for (PluginHook hook : hooks) {
            hook.cancel();
        }
    }

    private void unloadServices() {
        ServiceUtil.unregisterDiscord();
    }
}
