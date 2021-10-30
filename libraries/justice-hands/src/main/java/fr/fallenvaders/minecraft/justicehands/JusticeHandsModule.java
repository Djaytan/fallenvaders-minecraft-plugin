package fr.fallenvaders.minecraft.justicehands;

import co.aikar.commands.PaperCommandManager;
import fr.fallenvaders.minecraft.commons.FvModule;
import fr.fallenvaders.minecraft.justicehands.view.viewmodel.CategoriesList;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.configuration.file.FileConfiguration;
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

  private final CommandsInitializer commandsInitializer;
  private final ListenersInitializer listenersInitializer;

  /**
   * Constructor.
   *
   * @param config The Bukkit plugin config file instance.
   * @param paperCommandManager The Paper command manager of aïkar lib.
   * @param commandsInitializer The initializer of Bukkit commands.
   * @param listenersInitializer The initializer of Bukkit listeners.
   */
  @Inject
  public JusticeHandsModule(
      @NotNull FileConfiguration config,
      @NotNull PaperCommandManager paperCommandManager,
      @NotNull CommandsInitializer commandsInitializer,
      @NotNull ListenersInitializer listenersInitializer) {
    super(MODULE_NAME);
    this.config = config;
    this.paperCommandManager = paperCommandManager;
    this.commandsInitializer = commandsInitializer;
    this.listenersInitializer = listenersInitializer;
  }

  @Override
  public void onEnable() {
    commandsInitializer.initialize();
    listenersInitializer.initialize();

    // Load sanctions from config folder
    CategoriesList.getSanctionsConfig(config);
  }
}
