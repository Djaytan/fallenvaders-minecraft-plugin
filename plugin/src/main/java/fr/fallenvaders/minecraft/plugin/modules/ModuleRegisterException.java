package fr.fallenvaders.minecraft.plugin.modules;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * This exception is thrown when a problem occurs during the registration
 * process of a module (e.g. register a module avec the {@link ModuleRegister} was launched).
 *
 * @author Voltariuss
 * @since 0.1.0
 */
public class ModuleRegisterException extends Exception {

  public ModuleRegisterException(@NotNull String message) {
    super(message);
    Objects.requireNonNull(message);
  }
}
