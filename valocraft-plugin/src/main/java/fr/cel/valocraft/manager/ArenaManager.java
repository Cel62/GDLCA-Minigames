package fr.cel.valocraft.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.cel.valocraft.ValoCraft;
import fr.cel.valocraft.manager.arena.ValoArena;
import fr.cel.hub.utils.ChatUtility;
import fr.cel.valocraft.utils.Config;
import lombok.Getter;

public class ArenaManager {
    
    @Getter private final List<ValoArena> arenas = new ArrayList<>();
    private final ValoCraft main;

    public ArenaManager(ValoCraft main, ValoGameManager gameManager) {
        this.main = main;
        this.loadArenas(gameManager);
    }

    public ValoArena getArenaByDisplayName(String name) {
        for (ValoArena arena : arenas) {
            if (arena.getDisplayName().equalsIgnoreCase(name)) return arena;
        }
        return null;
    }

    public ValoArena getArenaByPlayer(Player player) {
        for (ValoArena arena : arenas) {
            if (arena.getPlayers().contains(player.getUniqueId())) return arena;
        }
        return null;
    }

    public void loadArenas(ValoGameManager gameManager) {
        arenas.clear();
        File folder = new File(main.getDataFolder(), "arenas");
        if (!folder.exists()) folder.mkdirs();
        if (folder.isDirectory()) {
            for (File file : folder.listFiles()) {
                Config config = new Config(main, file.getName().replace(".yml", ""));
                arenas.add(config.getArena(gameManager));
                Bukkit.getConsoleSender().sendMessage(ChatUtility.format("&6[Valocraft] &fChargement de l'arène ValoCraft ") + file.getName().replace(".yml", ""));
            }
        }
    }

}