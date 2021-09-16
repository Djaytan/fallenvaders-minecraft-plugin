package fr.fallenvaders.minecraft.plugin.modules;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * This class represents the declaration of a module in order to activate of deactivate it.
 *
 * @author Voltariuss
 * @since 0.1.0
 */
public abstract class ModuleDeclarer {

  /* Dependencies */
  private final JavaPlugin javaPlugin;

  /* Data */
  private final String moduleName;

  /**
   * Constructor.
   *
   * @param javaPlugin The Bukkit Java plugin.
   * @param moduleName The name of the module.
   */
  protected ModuleDeclarer(@NotNull JavaPlugin javaPlugin, @NotNull String moduleName) {
    Objects.requireNonNull(javaPlugin);
    Objects.requireNonNull(moduleName);

    this.javaPlugin = javaPlugin;
    this.moduleName = moduleName;
  }

  /** Executes the necessary statements to enable the targeted module. */
  public abstract void onEnable();

  /** Executes the necessary statements to disable the targeted module. */
  public abstract void onDisable();

  /**
   * Returns the singleton instance {@link JavaPlugin} associated with the current execution
   * context.
   *
   * @return the singleton instance {@link JavaPlugin} associated with the current execution
   *     context.
   */
  @NotNull
  public JavaPlugin getJavaPlugin() {
    return javaPlugin;
  }

  /**
   * Returns the module's name.
   *
   * @return the module's name.
   */
  @NotNull
  public final String getModuleName() {
    return moduleName;
  }
}
