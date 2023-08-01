package fr.cel.cachecache.manager.arena.state.providers.pregame;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

import fr.cel.cachecache.CacheCache;
import fr.cel.cachecache.manager.CCArena;
import fr.cel.cachecache.manager.arena.state.providers.StateListenerProvider;

public class PreGameListenerProvider extends StateListenerProvider {

    public PreGameListenerProvider(CCArena arena) {
        super(arena);
    }

    @Override
    public void onEnable(CacheCache main) {
        super.onEnable(main);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (!getArena().isPlayerInArena((Player) event.getEntity())) return;
            event.setCancelled(true);
        }
    }
    
}