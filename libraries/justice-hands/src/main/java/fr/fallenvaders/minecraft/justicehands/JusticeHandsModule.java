package fr.fallenvaders.minecraft.justicehands;

import fr.fallenvaders.minecraft.commons.FvModule;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.jetbrains.annotations.NotNull;

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

  private final CommandsInitializer commandsInitializer;
  private final ListenersInitializer listenersInitializer;

  /**
   * Constructor.
   *
   * @param commandsInitializer The {@link CommandsInitializer}.
   * @param listenersInitializer The {@link ListenersInitializer}.
   */
  @Inject
  public JusticeHandsModule(
      @NotNull CommandsInitializer commandsInitializer,
      @NotNull ListenersInitializer listenersInitializer) {
    super(MODULE_NAME);
    this.commandsInitializer = commandsInitializer;
    this.listenersInitializer = listenersInitializer;
  }

  @Override
  public void onEnable() {
    commandsInitializer.initialize();
    listenersInitializer.initialize();
  }
}
