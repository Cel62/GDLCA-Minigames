package fr.cel.hub.listener;

import fr.cel.hub.Hub;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class MiniGameListener extends HListener {

    public MiniGameListener(Hub main) {
        super(main);
    }

    @EventHandler
    public void interact(final PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!main.getPlayerManager().containsPlayerInHub(player)) return;
        Action action = event.getAction();
        ItemStack itemStack = event.getItem();

        if (itemStack == null) return;
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (itemStack.getItemMeta() == null || !itemStack.hasItemMeta()) return;
        if (!itemStack.getItemMeta().hasDisplayName()) return;

        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            if (itemStack.getItemMeta().getDisplayName().equals("Sélectionneur de mini-jeux")) {
                player.openInventory(main.getInventoryManager().getInventories().get("minigames").getInv());
            }
        }
    }

    @EventHandler
    public void interactInv(final InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!main.getPlayerManager().containsPlayerInHub(player)) return;
        if (player.isOp()) return;

        ItemStack item = event.getCurrentItem();
        if (item == null) return;
        if (item.getItemMeta() == null) return;
        if (item.getItemMeta().getDisplayName().equals("Sélectionneur de mini-jeux")) {
            event.setCancelled(true);
        }
    }

}