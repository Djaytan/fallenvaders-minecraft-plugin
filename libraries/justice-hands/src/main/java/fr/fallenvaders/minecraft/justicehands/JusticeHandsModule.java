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
  private final PaperCommandManager paperCommandManager;

  private final SanctionManagerCommand sanctionManagerCommand;
  private final CriminalRecordCommand criminalRecordCommand;

  private final ListenersInitializer listenersInitializer;

  /**
   * Constructor.
   *
   * @param config The Bukkit plugin config file instance.
   * @param paperCommandManager The Paper command manager of aïkar lib.
   * @param sanctionManagerCommand The sanction manager Bukkit command.
   * @param criminalRecordCommand The criminal record Bukkit command.
   * @param listenersInitializer The listeners initializer.
   */
  @Inject
  public JusticeHandsModule(
      @NotNull FileConfiguration config,
      @NotNull PaperCommandManager paperCommandManager,
      @NotNull SanctionManagerCommand sanctionManagerCommand,
      @NotNull CriminalRecordCommand criminalRecordCommand,
      @NotNull ListenersInitializer listenersInitializer) {
    super(MODULE_NAME);
    this.config = config;
    this.paperCommandManager = paperCommandManager;
    this.sanctionManagerCommand = sanctionManagerCommand;
    this.criminalRecordCommand = criminalRecordCommand;
    this.listenersInitializer = listenersInitializer;
  }

  @Override
  public void onEnable() {
    activeCommands();
    listenersInitializer.initialize();

    // Load sanctions from config folder
    CategoriesList.getSanctionsConfig(config);
  }

  private void activeCommands() {
    plugin.getCommand("cr").setExecutor(criminalRecordCommand);
    plugin.getCommand("sm").setExecutor(sanctionManagerCommand);
  }
}
