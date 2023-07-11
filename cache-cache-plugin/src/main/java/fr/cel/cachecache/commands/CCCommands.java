package fr.cel.cachecache.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.cel.cachecache.manager.Arena;
import fr.cel.cachecache.manager.GameManager;
import fr.cel.cachecache.manager.arena.state.pregame.PreGameArenaState;
import fr.cel.cachecache.manager.arena.state.pregame.StartingArenaState;

public class CCCommands implements CommandExecutor {

    private final GameManager gameManager;

    public CCCommands(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Vous devez etre un joueur pour faire cette commande.");
            return false;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("cachecache.cachecommand")) {
            player.sendMessage(GameManager.getPrefix() + "Tu n'as pas la permission de faire cette commande.");
            return false;
        }

        if (args[0].equalsIgnoreCase("start")) {

            if (!gameManager.getArenaManager().isPlayerInArena(player)) {
                player.sendMessage(GameManager.getPrefix() + "Vous n'êtes pas dans une arène.");
                return false;
            }

            Arena arena = gameManager.getArenaManager().getArenaByPlayer(player);
            if (arena.getPlayers().size() >= 2 && arena.getArenaState() instanceof PreGameArenaState) {
                arena.setArenaState(new StartingArenaState(arena));
                return false;
            } else {
                player.sendMessage(GameManager.getPrefix() + "Il n'y a pas assez de joueurs (minimum 2 joueurs) ou la partie est déjà lancée !");
            }
        }

        else if (args[0].equalsIgnoreCase("list")) {
            if (gameManager.getArenaManager().getArenas().size() == 0) {
                player.sendMessage(GameManager.getPrefix() + "Aucune arène a été installée.");
                return false;
            }
            for (Arena arena : gameManager.getArenaManager().getArenas()) {
                player.sendMessage(GameManager.getPrefix() + "Map " + arena.getDisplayName() + " | " + arena.getArenaState());
            }
            return false;
        }

        else if (args[0].equalsIgnoreCase("listplayer")) {

            if (!gameManager.getArenaManager().isPlayerInArena(player)) {
                player.sendMessage(GameManager.getPrefix() + "Vous n'êtes pas dans une arène.");
                return false;
            }

            Arena arena = gameManager.getArenaManager().getArenaByPlayer(player);
            List<String> playersName = new ArrayList<>();
            arena.getPlayers().forEach(pls -> {
                Player pl = Bukkit.getPlayer(pls);
                playersName.add(pl.getName());
            });
            player.sendMessage(GameManager.getPrefix() + "Joueurs: " + playersName);
            return false;
        }

        else if (args[0].equalsIgnoreCase("reload")) {
            player.sendMessage(GameManager.getPrefix() + "Les fichiers de configuration des maps Cache-Cache ont été rechargés.");
            gameManager.reloadArenaManager();
        }

        return false;
    }

}