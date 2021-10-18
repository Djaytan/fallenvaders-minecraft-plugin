package fr.fallenvaders.minecraft.justice_hands;

import co.aikar.commands.PaperCommandManager;
import fr.fallenvaders.minecraft.commons.FvModule;
import fr.fallenvaders.minecraft.justice_hands.criminalrecords.CommandCR;
import fr.fallenvaders.minecraft.justice_hands.criminalrecords.listeners.PlayerJoinListener;
import fr.fallenvaders.minecraft.justice_hands.keyskeeper.listeners.AsyncChatListener;
import fr.fallenvaders.minecraft.justice_hands.keyskeeper.listeners.PlayerLoginListener;
import fr.fallenvaders.minecraft.justice_hands.sanctionmanager.CategoriesList;
import fr.fallenvaders.minecraft.justice_hands.sanctionmanager.CommandSM;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Entry point class for JusticeHands module.
 *
 * @author FallenVaders' dev team
 * @version 0.3.0
 */
@Singleton
public class JusticeHands implements FvModule {

  private final JavaPlugin plugin;
  private final PluginManager pluginManager;
  private final PaperCommandManager paperCommandManager;

  private final CommandSM commandSM;
  private final CommandCR commandCR;

  /**
   * Constructor.
   *
   * @param plugin The Bukkit plugin.
   * @param pluginManager The Bukkit plugin manager.
   * @param paperCommandManager The Paper command manager of aïkar lib.
   * @param commandCR The criminal record Bukkit command.
   * @param commandSM The sanction manager Bukkit command.
   */
  @Inject
  public JusticeHands(@NotNull JavaPlugin plugin,
    @NotNull PluginManager pluginManager, @NotNull PaperCommandManager paperCommandManager, @NotNull CommandCR commandCR, @NotNull CommandSM commandSM) {
    this.plugin = plugin;
    this.pluginManager = pluginManager;
    this.paperCommandManager = paperCommandManager;
    this.commandCR = commandCR;
    this.commandSM = commandSM;
  }

  @Override
  public void onLoad() {
    /* Nothing to do */
  }

  @Override
  public void onEnable() {
    activeCommands();
    activeListeners();

    // Load sanctions from config folder
    CategoriesList.getSanctionsConfig(plugin.getConfig());
  }

  @Override
  public void onDisable() {
    /* Nothing to do */
  }

  private void activeListeners() {
    pluginManager.registerEvents(new PlayerJoinListener(), plugin);
    pluginManager.registerEvents(new AsyncChatListener(), plugin);
    pluginManager.registerEvents(new PlayerLoginListener(), plugin);
  }

  private void activeCommands() {
    plugin.getCommand("cr").setExecutor(commandCR);
    // getCommand("mt").setExecutor(new CommandMT(this));
    plugin.getCommand("sm").setExecutor(commandSM);
  }
}
