package fr.cel.valocraft.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.cel.valocraft.ValoCraft;
import fr.cel.valocraft.manager.arena.Arena;
import fr.cel.hub.utils.ChatUtility;
import fr.cel.valocraft.utils.Config;
import lombok.Getter;

public class ArenaManager {
    
    @Getter private List<Arena> arenas = new ArrayList<>();
    private final ValoCraft main;
    private final GameManager gameManager;

    public ArenaManager(ValoCraft main, GameManager gameManager) {
        this.main = main;
        this.gameManager = gameManager;
        this.loadArenas();
    }

    public Arena getArenaByDisplayName(String name) {
        for (Arena arena : arenas) {
            if (arena.getDisplayName().equalsIgnoreCase(name)) return arena;
        }
        return null;
    }

    public Arena getArenaByPlayer(Player player) {
        for (Arena arena : arenas) {
            if (arena.getPlayers().contains(player.getUniqueId())) return arena;
        }
        return null;
    }

    public boolean isPlayerInArena(Player player) {
        for (Arena arena : arenas) {
            if (arena.getPlayers().contains(player.getUniqueId())) return true;
        }
        return false;
    }

    public void loadArenas() {
        arenas.clear();
        File folder = new File(main.getDataFolder(), "arenas");
        if (!folder.exists()) folder.mkdirs();
        if (folder.isDirectory()) {
            for (File file : folder.listFiles()) {
                Config config = new Config(main, gameManager, file.getName().replace(".yml", ""));
                arenas.add(config.getArena());
                Bukkit.getConsoleSender().sendMessage(ChatUtility.format("&6[Valocraft] &fChargement de l'arène ValoCraft ") + file.getName().replace(".yml", ""));
            }
        }
    }

}