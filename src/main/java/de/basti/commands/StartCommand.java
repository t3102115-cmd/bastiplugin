package de.basti.commands;

import de.basti.GameManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCommand implements CommandExecutor {

    private final GameManager gameManager;

    public StartCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be used by players!");
            return true;
        }

        Player player = (Player) sender;

        if (gameManager.isGameStarted(player)) {
            player.sendMessage("§cThe game is already running!");
            return true;
        }

        gameManager.startGame(player);
        player.sendMessage("§a§lThe game has started!");
        player.sendMessage("§7The border was set to 1 block.");
        player.sendMessage("§7Removing blocks increases the border, placing blocks decreases it.");

        return true;
    }
}
