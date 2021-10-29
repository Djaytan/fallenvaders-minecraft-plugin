package fr.fallenvaders.minecraft.justicehands;

import co.aikar.commands.PaperCommandManager;
import fr.fallenvaders.minecraft.commons.FvModule;
import fr.fallenvaders.minecraft.justicehands.view.commands.CommandCR;
import fr.fallenvaders.minecraft.justicehands.view.listeners.AsyncChatListener;
import fr.fallenvaders.minecraft.justicehands.view.listeners.PlayerLoginListener;
import fr.fallenvaders.minecraft.justicehands.view.viewmodel.CategoriesList;
import fr.fallenvaders.minecraft.justicehands.view.commands.CommandSM;
import org.bukkit.configuration.file.FileConfiguration;
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
public final class JusticeHandsModule extends FvModule {

  /** This is the module's name. */
  public static final String MODULE_NAME = "justice-hands";

  private final JavaPlugin plugin;
  private final PluginManager pluginManager;
  private final FileConfiguration config;

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
   * @param config The Bukkit plugin config file instance.
   */
  @Inject
  public JusticeHandsModule(
      @NotNull JavaPlugin plugin,
      @NotNull PluginManager pluginManager,
      @NotNull PaperCommandManager paperCommandManager,
      @NotNull CommandCR commandCR,
      @NotNull CommandSM commandSM,
      @NotNull FileConfiguration config) {
    super(MODULE_NAME);
    this.plugin = plugin;
    this.pluginManager = pluginManager;
    this.paperCommandManager = paperCommandManager;
    this.commandCR = commandCR;
    this.commandSM = commandSM;
    this.config = config;
  }

  @Override
  public void onEnable() {
    activeCommands();
    activeListeners();

    // Load sanctions from config folder
    CategoriesList.getSanctionsConfig(config);
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
