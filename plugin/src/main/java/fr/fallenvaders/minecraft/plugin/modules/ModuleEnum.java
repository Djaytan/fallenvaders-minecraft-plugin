package fr.fallenvaders.minecraft.plugin.modules;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * This enum represents the list of modules actually available.
 *
 * @author Voltariuss
 * @since 0.2.0
 */
public enum ModuleEnum {
  FALLEN_VADERS_CORE("fallenvaders-core"),
  JUSTICE_HANDS("justice-hands"),
  MAIL_BOX("mail-box"),
  MINECRAFT_ENHANCE("minecraft-enhance"),
  MINI_EVENTS("mini-events");

  private final String moduleName;

  ModuleEnum(@NotNull String moduleName) {
    Objects.requireNonNull(moduleName);
    this.moduleName = moduleName;
  }

  @NotNull
  public String getModuleName() {
    return moduleName;
  }
}
