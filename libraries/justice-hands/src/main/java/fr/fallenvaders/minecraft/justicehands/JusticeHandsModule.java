package fr.fallenvaders.minecraft.justicehands;

import co.aikar.commands.PaperCommandManager;
import fr.fallenvaders.minecraft.commons.FvModule;
import fr.fallenvaders.minecraft.justicehands.view.commands.CriminalRecordCommand;
import fr.fallenvaders.minecraft.justicehands.view.commands.SanctionManagerCommand;
import fr.fallenvaders.minecraft.justicehands.view.listeners.AsyncChatListener;
import fr.fallenvaders.minecraft.justicehands.view.listeners.PlayerLoginListener;
import fr.fallenvaders.minecraft.justicehands.view.viewmodel.CategoriesList;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

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

  private final FileConfiguration config;
  private final JavaPlugin plugin;
  private final PaperCommandManager paperCommandManager;
  private final PluginManager pluginManager;

  private final SanctionManagerCommand sanctionManagerCommand;
  private final CriminalRecordCommand criminalRecordCommand;

  private final AsyncChatListener asyncChatListener;
  private final PlayerLoginListener playerLoginListener;

  /**
   * Constructor.
   *
   * @param config The Bukkit plugin config file instance.
   * @param plugin The Bukkit plugin.
   * @param paperCommandManager The Paper command manager of aïkar lib.
   * @param pluginManager The Bukkit plugin manager.
   * @param sanctionManagerCommand The sanction manager Bukkit command.
   * @param criminalRecordCommand The criminal record Bukkit command.
   * @param asyncChatListener The async chat listener.
   * @param playerLoginListener The player login listener.
   */
  @Inject
  public JusticeHandsModule(
      @NotNull FileConfiguration config,
      @NotNull JavaPlugin plugin,
      @NotNull PaperCommandManager paperCommandManager,
      @NotNull PluginManager pluginManager,
      @NotNull SanctionManagerCommand sanctionManagerCommand,
      @NotNull CriminalRecordCommand criminalRecordCommand,
      @NotNull AsyncChatListener asyncChatListener,
      @NotNull PlayerLoginListener playerLoginListener) {
    super(MODULE_NAME);
    this.config = config;
    this.plugin = plugin;
    this.paperCommandManager = paperCommandManager;
    this.pluginManager = pluginManager;
    this.sanctionManagerCommand = sanctionManagerCommand;
    this.criminalRecordCommand = criminalRecordCommand;
    this.asyncChatListener = asyncChatListener;
    this.playerLoginListener = playerLoginListener;
  }

  @Override
  public void onEnable() {
    activeCommands();
    activeListeners();

    // Load sanctions from config folder
    CategoriesList.getSanctionsConfig(config);
  }

  private void activeListeners() {
    pluginManager.registerEvents(asyncChatListener, plugin);
    pluginManager.registerEvents(playerLoginListener, plugin);
  }

  private void activeCommands() {
    plugin.getCommand("cr").setExecutor(criminalRecordCommand);
    plugin.getCommand("sm").setExecutor(sanctionManagerCommand);
  }
}
