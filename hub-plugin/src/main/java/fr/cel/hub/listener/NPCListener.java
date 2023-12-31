package fr.cel.hub.listener;

import fr.cel.hub.Hub;
import fr.cel.hub.manager.NPC;
import fr.cel.hub.manager.NPCManager;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class NPCListener extends HListener {

    public NPCListener(Hub main) {
        super(main);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Location loc = event.getTo();

        for (NPC npc : NPCManager.getNpcs()) {
            if (loc.getWorld() != npc.getLocation().getWorld()) {
                continue;
            }

            Location newLoc = loc.clone();
            newLoc.setDirection(newLoc.subtract(npc.getNpc().getBukkitEntity().getLocation()).toVector());
            npc.lookAt(event.getPlayer(), newLoc);
        }
    }

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        for (NPC npc : NPCManager.getNpcs()) {
            npc.spawn(event.getPlayer());
        }
    }

}