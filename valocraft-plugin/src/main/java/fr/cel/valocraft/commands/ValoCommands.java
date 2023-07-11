package fr.cel.valocraft.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.cel.valocraft.manager.GameManager;
import fr.cel.valocraft.manager.arena.Arena;
import fr.cel.valocraft.manager.arena.state.pregame.PreGameArenaState;
import fr.cel.valocraft.manager.arena.state.pregame.StartingArenaState;

public class ValoCommands implements CommandExecutor {

    private final GameManager gameManager;

    public ValoCommands(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Vous devez etre un joueur pour faire cette commande.");
            return false;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("valocraft.valocommand")) {
            player.sendMessage(gameManager.getPrefix() + "Tu n'as pas la permission de faire cette commande.");
            return false;
        }

        if (args.length == 0) {
            player.sendMessage(gameManager.getPrefix() + "Vous devez mettre : /valocraft <start/list/listplayer/reload>");
            return false;
        }

        if (args[0].equalsIgnoreCase("start")) {

            if (!gameManager.getArenaManager().isPlayerInArena(player)) {
                player.sendMessage(gameManager.getPrefix() + "Vous n'êtes pas dans une arène.");
                return false;
            }

            Arena arena = gameManager.getArenaManager().getArenaByPlayer(player);
            if (arena.getPlayers().size() >= 2 && arena.getArenaState() instanceof PreGameArenaState) {
                arena.setArenaState(new StartingArenaState(arena));
                return false;
            } else {
                player.sendMessage(gameManager.getPrefix() + "La partie est déjà lancée.");
                return false;
            }
        }

        else if (args[0].equalsIgnoreCase("list")) {
            if (gameManager.getArenaManager().getArenas().size() == 0) {
                player.sendMessage(gameManager.getPrefix() + "Aucune arène a été installée.");
                return false;
            }
            gameManager.getArenaManager().getArenas().forEach(arena -> { player.sendMessage(gameManager.getPrefix() + "Map " + arena.getDisplayName() + " | " + arena.getArenaState()); });
            return false;
        }

        else if (args[0].equalsIgnoreCase("listplayer")) {

            if (!gameManager.getArenaManager().isPlayerInArena(player)) {
                player.sendMessage(gameManager.getPrefix() + "Vous n'êtes pas dans une arène.");
                return false;
            }

            Arena arena = gameManager.getArenaManager().getArenaByPlayer(player);

            List<String> players = new ArrayList<>();
            List<String> blueTeam = new ArrayList<>();
            List<String> redTeam = new ArrayList<>();

            arena.getPlayers().forEach(uuid -> { players.add(Bukkit.getPlayer(uuid).getName()); });
            arena.getBlueTeam().getPlayers().forEach(uuid -> { blueTeam.add(Bukkit.getPlayer(uuid).getName()); });
            arena.getRedTeam().getPlayers().forEach(uuid -> { redTeam.add(Bukkit.getPlayer(uuid).getName()); });

            player.sendMessage(gameManager.getPrefix() + "Joueurs :" + players + "\nTeam Bleu : " + blueTeam + "\nTeam Rouge : " + redTeam);
            return false;
        }

        else if (args[0].equalsIgnoreCase("reload")) {
            player.sendMessage(gameManager.getPrefix() + "Les fichiers de configuration des arènes ValoCraft ont été rechargées.");
            gameManager.reloadArenaManager();
            return false;
        }

        else if (args[0].equalsIgnoreCase("setround")) {
            if (!gameManager.getArenaManager().isPlayerInArena(player)) {
                player.sendMessage(gameManager.getPrefix() + "Vous n'êtes pas dans une arène.");
                return false;
            }

            Arena arena = gameManager.getArenaManager().getArenaByPlayer(player);

            if (args[1].equalsIgnoreCase("blue")) {
                arena.getBlueTeam().setRoundWin(Integer.parseInt(args[2]));
            } else if (args[1].equalsIgnoreCase("red")) {
                arena.getRedTeam().setRoundWin(Integer.parseInt(args[2]));
            }

            return false;
        }

        return false;
    }

}