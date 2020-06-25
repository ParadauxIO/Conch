package io.paradaux.hiberniadiscord.EventListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;

public class ServerLoadEventListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void Listener (ServerLoadEvent event) {

    }
}
