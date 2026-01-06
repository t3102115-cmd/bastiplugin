package de.basti.listeners;

import de.basti.GameManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    private final GameManager gameManager;

    public PlayerMoveListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!gameManager.isGameStarted(event.getPlayer())) {
            // Bewegung verhindern wenn Spiel nicht gestartet wurde
            event.setCancelled(true);
        }
    }
}
