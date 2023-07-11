package fr.cel.valocraft.listener.state;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.cel.valocraft.ValoCraft;
import fr.cel.valocraft.manager.arena.Arena;
import lombok.Getter;

public abstract class StateListenerProvider implements Listener {

    @Getter private Arena arena;

    public StateListenerProvider(Arena arena) {
        this.arena = arena;
    }

    public void onEnable(ValoCraft main) {
        main = arena.getGameManager().getMain();
        main.getServer().getPluginManager().registerEvents(this, main);
    }
   
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (!getArena().isPlayerInArena(player)) return;
        getArena().removePlayer(player);
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (event.getMessage().equals("/hub")) {
            if (!getArena().isPlayerInArena(player)) return;
            getArena().removePlayer(player);
        }
    }

    @EventHandler
    public void foodChange(FoodLevelChangeEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            if (!getArena().isPlayerInArena((Player) entity)) return;
            event.setFoodLevel(20);
        }
    }

}
