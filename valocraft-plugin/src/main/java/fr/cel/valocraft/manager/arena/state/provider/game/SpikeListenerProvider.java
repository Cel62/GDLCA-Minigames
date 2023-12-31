package fr.cel.valocraft.manager.arena.state.provider.game;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import fr.cel.valocraft.ValoCraft;
import fr.cel.valocraft.manager.arena.state.provider.StateListenerProvider;
import fr.cel.valocraft.manager.arena.ValoArena;
import fr.cel.valocraft.manager.arena.state.game.TimeOverArenaState;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class SpikeListenerProvider extends StateListenerProvider {

    public SpikeListenerProvider(ValoArena arena) {
        super(arena);
    }

    @Override
    public void onEnable(ValoCraft main) {
        super.onEnable(main);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (getArena().isPlayerInArena(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (!getArena().isPlayerInArena(player)) return;

        if (getArena().getDefenders().getTeam().isOnTeam(player.getUniqueId())) {
            if (block.getType() == Material.BREWING_STAND) {
                getArena().sendTitle("Spike désamorcé", "");
                getArena().addRoundDefender();
                getArena().setArenaState(new TimeOverArenaState(getArena()));
            } else {
                event.setCancelled(true);
            }
            return;
        }

        event.setCancelled(true);

    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player player) {
            if (!getArena().isPlayerInArena(player)) return;
            
            if (event.getCause() == DamageCause.PROJECTILE) {
                player.setHealth(0);
            }
            
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player victim =  event.getEntity();
        if (!getArena().isPlayerInArena(victim)) return;

        event.setDeathMessage("");

        if (victim.getGameMode() == GameMode.SURVIVAL) {
            getArena().eliminate(victim);
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (!getArena().isPlayerInArena(player)) return;

        if (event.getItemDrop().getItemStack().getType() != Material.BREWING_STAND) event.setCancelled(true);
    }

    @EventHandler
    public void onPotionSplash(PotionSplashEvent event) {
        Block block = event.getHitBlock();
        if (block == null) return;

        Location location = block.getLocation();
        for (int i = 0; i < 10; i++) {
            double offsetX = Math.random() * 4 - 2;
            double offsetY = Math.random() * 4 - 2;
            double offsetZ = Math.random() * 4 - 2;
            location.add(offsetX, offsetY, offsetZ);
            location.getWorld().spawnParticle(Particle.SMOKE_LARGE, location, 1);
            location.subtract(offsetX, offsetY, offsetZ);
        }
    }
    
}