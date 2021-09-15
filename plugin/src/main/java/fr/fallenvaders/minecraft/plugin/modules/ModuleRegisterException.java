package fr.fallenvaders.minecraft.plugin.modules;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

// TODO: add JavaDoc to this class
public class ModuleRegisterException extends Exception {

  public ModuleRegisterException(@NotNull String message) {
    super(message);
    Objects.requireNonNull(message);
  }
}
