package fr.cel.parkour.utils;

import java.io.File;
import java.io.IOException;

import fr.cel.parkour.manager.area.ParkourMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.cel.parkour.Parkour;

public class Config {
    
    private YamlConfiguration config;
    private File file;

    private String arenaName;

    public Config(Parkour main, String arenaName) {
        this.file = new File(main.getDataFolder() + File.separator + "maps", arenaName + ".yml");
        this.config = YamlConfiguration.loadConfiguration(this.file);
        this.arenaName = arenaName;
        this.load();
    }

    public ParkourMap getArena() {
        String displayName = this.config.getString("displayName");

        String locSpawn = this.config.getString("locationSpawn");
        Location locationSpawn = parseStringToLoc(locSpawn);

        ParkourMap arena = new ParkourMap(arenaName, displayName, locationSpawn);
        return arena;
    }

    private void load() {
        try {
            this.config.load(this.file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public Location parseStringToLoc(String string) {
        String[] parsedLoc = string.split(",");

        return new Location(Bukkit.getWorld("world"), Double.parseDouble(parsedLoc[0]), Double.parseDouble(parsedLoc[1]), Double.parseDouble(parsedLoc[2]));
    }

}
