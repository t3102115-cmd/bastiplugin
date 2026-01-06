package de.basti.listeners;

import de.basti.GameManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    private final GameManager gameManager;

    public BlockBreakListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        if (!gameManager.isGameStarted(event.getPlayer())) {
            // Block-Abbau verhindern wenn Spiel nicht gestartet wurde
            event.setCancelled(true);
        } else {
            // Border um 1 erhöhen wenn Block abgebaut wird
            gameManager.increaseBorder(event.getPlayer());
        }
    }
}
