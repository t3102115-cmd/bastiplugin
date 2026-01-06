package de.basti.listeners;

import de.basti.GameManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    private final GameManager gameManager;

    public BlockPlaceListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!gameManager.isGameStarted(event.getPlayer())) {
            // Block-Platzierung verhindern wenn Spiel nicht gestartet wurde
            event.setCancelled(true);
        } else {
            // Border um 1 verringern wenn Block platziert wird
            gameManager.decreaseBorder(event.getPlayer());
        }
    }
}
