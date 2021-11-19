package fr.fallenvaders.minecraft.justicehands;

import fr.fallenvaders.minecraft.commons.CriticalErrorRaiser;
import fr.fallenvaders.minecraft.commons.FvModule;
import fr.fallenvaders.minecraft.commons.sql.ModuleDatabaseInitializer;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Entry point class for JusticeHands module.
 *
 * @author Voltariuss
 * @author Glynix
 * @version 0.3.0
 */
@Singleton
public final class JusticeHandsModule extends FvModule {

  /** This is the module's name. */
  public static final String MODULE_NAME = "justice-hands";

  private static final String SQL_INITIALIZE_SCRIPT = "initialize.sql";

  private final CommandsInitializer commandsInitializer;
  private final CriticalErrorRaiser criticalErrorRaiser;
  private final ListenersInitializer listenersInitializer;
  private final ModuleDatabaseInitializer moduleDatabaseInitializer;

  /**
   * Constructor.
   *
   * @param commandsInitializer The {@link CommandsInitializer}.
   * @param criticalErrorRaiser The {@link CriticalErrorRaiser}.
   * @param listenersInitializer The {@link ListenersInitializer}.
   * @param moduleDatabaseInitializer The {@link ModuleDatabaseInitializer}.
   */
  @Inject
  public JusticeHandsModule(
      @NotNull CommandsInitializer commandsInitializer,
      @NotNull CriticalErrorRaiser criticalErrorRaiser,
      @NotNull ListenersInitializer listenersInitializer,
      @NotNull ModuleDatabaseInitializer moduleDatabaseInitializer) {
    super(MODULE_NAME);
    this.commandsInitializer = commandsInitializer;
    this.criticalErrorRaiser = criticalErrorRaiser;
    this.listenersInitializer = listenersInitializer;
    this.moduleDatabaseInitializer = moduleDatabaseInitializer;
  }

  @Override
  public void onEnable() {
    try {
      moduleDatabaseInitializer.initialize(this.getClass(), SQL_INITIALIZE_SCRIPT);
    } catch (IOException | SQLException e) {
      criticalErrorRaiser.raise(
          "Fail to execute SQL initialize script. Raise a critical error to prevent bad effects.");
    }
    commandsInitializer.initialize();
    listenersInitializer.initialize();
  }
}
