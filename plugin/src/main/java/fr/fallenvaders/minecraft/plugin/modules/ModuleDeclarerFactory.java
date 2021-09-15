package fr.fallenvaders.minecraft.plugin.modules;

import fr.fallenvaders.minecraft.plugin.modules.declarers.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Objects;

/**
 * This is a factory to instantiate the {@link ModuleDeclarer} class.
 *
 * @author Voltariuss
 * @since 0.2.0
 */
@Singleton
public class ModuleDeclarerFactory {

  private final JavaPlugin javaPlugin;

  /**
   * Constructor.
   *
   * @param javaPlugin The Java Bukkit plugin.
   */
  @Inject
  public ModuleDeclarerFactory(@NotNull JavaPlugin javaPlugin) {
    Objects.requireNonNull(javaPlugin);

    this.javaPlugin = javaPlugin;
  }

  /**
   * Creates a {@link ModuleDeclarer} according to the specified arguments.
   *
   * @param moduleEnum The module to create.
   * @return a {@link ModuleDeclarer} according to the specified arguments.
   */
  @NotNull
  public ModuleDeclarer createModule(@NotNull ModuleEnum moduleEnum) {
    Objects.requireNonNull(moduleEnum);
    return switch (moduleEnum) {
      case FALLEN_VADERS_CORE -> new FallenVadersCoreModuleDeclarer(javaPlugin);
      case JUSTICE_HANDS -> new JusticeHandsModuleDeclarer(javaPlugin);
      case MAIL_BOX -> new MailBoxModuleDeclarer(javaPlugin);
      case MINECRAFT_ENHANCE -> new MinecraftEnhanceModuleDeclarer(javaPlugin);
      case MINI_EVENTS -> new MiniEventsModuleDeclarer(javaPlugin);
    };
  }
}
