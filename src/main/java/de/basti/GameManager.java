package de.basti;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameManager {

    private final JavaPlugin plugin;
    private final Map<UUID, Boolean> gameStarted;
    private final Map<UUID, Long> gameStartTime;
    private final Map<UUID, BukkitTask> timerTasks;
    private final Map<UUID, WorldBorder> playerBorders;

    public GameManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.gameStarted = new HashMap<>();
        this.gameStartTime = new HashMap<>();
        this.timerTasks = new HashMap<>();
        this.playerBorders = new HashMap<>();
    }

    public boolean isGameStarted(Player player) {
        return gameStarted.getOrDefault(player.getUniqueId(), false);
    }

    public void startGame(Player player) {
        UUID uuid = player.getUniqueId();
        gameStarted.put(uuid, true);
        gameStartTime.put(uuid, System.currentTimeMillis()); // Startzeit in Millisekunden speichern

        // Border auf 1 Block setzen
        World world = player.getWorld();
        WorldBorder border = world.getWorldBorder();
        border.setSize(1.0);
        border.setCenter(player.getLocation());
        border.setDamageAmount(1000.0); // Border-Schaden auf 1000 setzen
        playerBorders.put(uuid, border);

        // Timer starten
        startTimer(player);
    }

    private void startTimer(Player player) {
        UUID uuid = player.getUniqueId();
        
        // Alten Timer stoppen falls vorhanden
        if (timerTasks.containsKey(uuid)) {
            timerTasks.get(uuid).cancel();
        }

        // Neuen Timer starten - läuft jeden Tick (50ms) für Millisekunden-Genauigkeit
        BukkitTask task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            if (!isGameStarted(player) || !player.isOnline()) {
                stopGame(player);
                return;
            }

            // Aktuelle Zeit in Millisekunden berechnen
            Long startTime = gameStartTime.get(uuid);
            if (startTime == null) {
                return;
            }
            
            long currentTime = System.currentTimeMillis();
            long elapsedMillis = currentTime - startTime;
            
            // Zeit formatieren: MM:SS.mmm
            long totalSeconds = elapsedMillis / 1000;
            long minutes = totalSeconds / 60;
            long seconds = totalSeconds % 60;
            long milliseconds = elapsedMillis % 1000;
            
            String timeString = String.format("%02d:%02d.%03d", minutes, seconds, milliseconds);
            
            Component actionBarMessage = Component.text("Time: " + timeString, NamedTextColor.YELLOW);
            player.sendActionBar(actionBarMessage);
        }, 0L, 1L); // Jeden Tick (50ms) für Millisekunden-Genauigkeit

        timerTasks.put(uuid, task);
    }

    public void stopGame(Player player) {
        UUID uuid = player.getUniqueId();
        gameStarted.put(uuid, false);

        // Timer stoppen
        if (timerTasks.containsKey(uuid)) {
            timerTasks.get(uuid).cancel();
            timerTasks.remove(uuid);
        }

        // Border zurücksetzen (optional - kann auch so bleiben)
        // WorldBorder border = playerBorders.get(uuid);
        // if (border != null) {
        //     border.setSize(60000000.0); // Standard Border Größe
        // }

        gameStartTime.remove(uuid);
        playerBorders.remove(uuid);
    }

    public void stopGame() {
        // Alle Spiele stoppen
        for (UUID uuid : gameStarted.keySet()) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                stopGame(player);
            }
        }
    }

    public void increaseBorder(Player player) {
        if (!isGameStarted(player)) return;

        WorldBorder border = playerBorders.get(player.getUniqueId());
        if (border != null) {
            double currentSize = border.getSize();
            border.setSize(currentSize + 1.0);
        }
    }

    public void decreaseBorder(Player player) {
        if (!isGameStarted(player)) return;

        WorldBorder border = playerBorders.get(player.getUniqueId());
        if (border != null) {
            double currentSize = border.getSize();
            if (currentSize > 1.0) {
                border.setSize(currentSize - 1.0);
            }
        }
    }
}

