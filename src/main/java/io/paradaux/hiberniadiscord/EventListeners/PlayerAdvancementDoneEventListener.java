package io.paradaux.hiberniadiscord.EventListeners;

import org.bukkit.advancement.Advancement;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class PlayerAdvancementDoneEventListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void Listener (PlayerAdvancementDoneEvent event) {
        Advancement advancement = event.getAdvancement();
        
    }

}
