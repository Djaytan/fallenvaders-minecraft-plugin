package fr.fallenvaders.minecraft.plugin.modules;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * This exception is thrown when a problem occurs during the registration process of a module (e.g.
 * register a module avec the {@link ModuleRegisterService} was launched).
 *
 * @author Voltariuss
 * @since 0.1.0
 */
public class ModuleRegisterException extends Exception {

  /**
   * Constructor.
   *
   * @param message The mandatory exception message.
   */
  public ModuleRegisterException(@NotNull String message) {
    super(message);
    Objects.requireNonNull(message);
  }

  /**
   * Constructor.
   *
   * @param message The mandatory exception message.
   * @param cause The cause exception.
   */
  public ModuleRegisterException(@NotNull String message, @NotNull Throwable cause) {
    super(message, cause);
    Objects.requireNonNull(message);
    Objects.requireNonNull(cause);
  }
}
