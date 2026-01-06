package de.basti.listeners;

import de.basti.GameManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    private final GameManager gameManager;

    public PlayerDeathListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        
        if (gameManager.isGameStarted(player)) {
            // Chat-Nachricht senden
            Component deathMessage = Component.text(player.getName() + " ist gestorben! Die Challenge ist vorbei!", NamedTextColor.RED);
            event.getEntity().getServer().broadcast(deathMessage);
            
            // Spiel beenden
            gameManager.stopGame(player);
        }
    }
}
