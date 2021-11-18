package fr.fallenvaders.minecraft.justicehands;

import fr.fallenvaders.minecraft.commons.CriticalErrorRaiser;
import fr.fallenvaders.minecraft.commons.FvModule;
import fr.fallenvaders.minecraft.commons.sql.DatabaseInitializer;
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
  private final DatabaseInitializer databaseInitializer;
  private final ListenersInitializer listenersInitializer;

  /**
   * Constructor.
   *
   * @param commandsInitializer The {@link CommandsInitializer}.
   * @param criticalErrorRaiser The {@link CriticalErrorRaiser}.
   * @param databaseInitializer The {@link DatabaseInitializer}.
   * @param listenersInitializer The {@link ListenersInitializer}.
   */
  @Inject
  public JusticeHandsModule(
      @NotNull CommandsInitializer commandsInitializer,
      @NotNull CriticalErrorRaiser criticalErrorRaiser,
      @NotNull DatabaseInitializer databaseInitializer,
      @NotNull ListenersInitializer listenersInitializer) {
    super(MODULE_NAME);
    this.commandsInitializer = commandsInitializer;
    this.criticalErrorRaiser = criticalErrorRaiser;
    this.databaseInitializer = databaseInitializer;
    this.listenersInitializer = listenersInitializer;
  }

  @Override
  public void onEnable() {
    try {
      databaseInitializer.initialize(this.getClass(), SQL_INITIALIZE_SCRIPT);
    } catch (IOException | SQLException e) {
      criticalErrorRaiser.raise(
          "Fail to execute SQL initialize script. Raise a critical error to prevent bad effects.");
    }
    commandsInitializer.initialize();
    listenersInitializer.initialize();
  }
}
