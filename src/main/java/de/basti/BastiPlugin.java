package de.basti;

import de.basti.commands.StartCommand;
import de.basti.listeners.*;
import org.bukkit.plugin.java.JavaPlugin;

public class BastiPlugin extends JavaPlugin {

    private GameManager gameManager;

    @Override
    public void onEnable() {
        // GameManager initialisieren
        gameManager = new GameManager(this);

        // Commands registrieren
        getCommand("start").setExecutor(new StartCommand(gameManager));

        // Event-Listener registrieren
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new ItemDropListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new ItemPickupListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(gameManager), this);

        getLogger().info("BastiPlugin loaded successfully!");
    }

    @Override
    public void onDisable() {
        if (gameManager != null) {
            gameManager.stopGame();
        }
        getLogger().info("BastiPlugin has been unloaded!");
    }
}
