package io.paradaux.hiberniadiscord.EventListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitEventListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void Listener (PlayerQuitEvent event) {}
}
