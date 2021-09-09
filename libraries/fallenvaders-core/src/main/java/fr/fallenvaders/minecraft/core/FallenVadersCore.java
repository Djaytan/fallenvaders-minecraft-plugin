package fr.fallenvaders.minecraft.core;

import fr.fallenvaders.minecraft.core.fight_session.PluginController;
import fr.fallenvaders.minecraft.core.fight_session.commands.CmdFightSession;
import fr.fallenvaders.minecraft.core.fight_session.listeners.FightsEvents;
import org.bukkit.plugin.java.JavaPlugin;

public class FallenVadersCore extends JavaPlugin {

  public static JavaPlugin main;

  public static void enableModule(JavaPlugin plugin) {
    FallenVadersCore.main = plugin;
    PluginController.init();

    // Register commands
    plugin.getCommand(CmdFightSession.CMD_LABEL).setExecutor(new CmdFightSession());

    // Register listeners
    plugin.getServer().getPluginManager().registerEvents(new FightsEvents(), plugin);
  }

  public static void disableModule(JavaPlugin plugin) {
    PluginController.getSessionManager().stopAll();
  }
}
