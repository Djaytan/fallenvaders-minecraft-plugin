package fr.fallenvaders.minecraft.mini_events;

import org.bukkit.plugin.java.JavaPlugin;

import fr.fallenvaders.minecraft.mini_events.commands.CommandEVENT;
import fr.fallenvaders.minecraft.mini_events.commands.TabComplete;
import fr.fallenvaders.minecraft.mini_events.events.bowspleef.listeners.BowSpleefArrowHitListener;
import fr.fallenvaders.minecraft.mini_events.events.bowspleef.listeners.BowSpleefEntityExplodeListener;
import fr.fallenvaders.minecraft.mini_events.events.enclume.listeners.EnclumeEntityDamageListener;
import fr.fallenvaders.minecraft.mini_events.events.spleef.listeners.SpleefBlockBreakListener;
import fr.fallenvaders.minecraft.mini_events.listeners.InventoryClickListener;
import fr.fallenvaders.minecraft.mini_events.listeners.PlayerJoinListener;
import fr.fallenvaders.minecraft.mini_events.listeners.PlayerQuitListener;
import fr.fallenvaders.minecraft.mini_events.listeners.PlayerTeleportListener;

public class MiniEvents extends JavaPlugin {

    // TODO: refactor

    public static JavaPlugin PLUGIN;
    public static final String PREFIX = "§7[§6MiniEvents§7] "; // Prefix pour les messages dans le chat.

    private static Game game;

    public static void enableModule(JavaPlugin plugin) {
        PLUGIN = plugin;
        plugin.saveDefaultConfig();

        game = new Game();
        activeCommands();
        activeGeneralListeners();
        activeGameListeners();
    }

    public static void disableModule(JavaPlugin plugin) {
    }

    public static Game getGame() {
        return game;
    }

    private static void activeCommands() {
        PLUGIN.getCommand("event").setExecutor(new CommandEVENT());
        PLUGIN.getCommand("event").setTabCompleter(new TabComplete());
    }

    private static void activeGeneralListeners() {
        PLUGIN.getServer().getPluginManager().registerEvents(new InventoryClickListener(), PLUGIN);
        PLUGIN.getServer().getPluginManager().registerEvents(new PlayerQuitListener(), PLUGIN);
        PLUGIN.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), PLUGIN);
        PLUGIN.getServer().getPluginManager().registerEvents(new PlayerTeleportListener(), PLUGIN);
    }

    private static void activeGameListeners() {
        PLUGIN.getServer().getPluginManager().registerEvents(new SpleefBlockBreakListener(), PLUGIN);
        PLUGIN.getServer().getPluginManager().registerEvents(new EnclumeEntityDamageListener(), PLUGIN);
        PLUGIN.getServer().getPluginManager().registerEvents(new BowSpleefArrowHitListener(), PLUGIN);
        PLUGIN.getServer().getPluginManager().registerEvents(new BowSpleefEntityExplodeListener(), PLUGIN);
    }
}
