package fr.fallenvaders.minecraft.plugin.modules;

import fr.fallenvaders.minecraft.plugin.FallenVadersPlugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

// TODO: migrate to Guice DI usage

/**
 * This class represents the declaration of a module in order to activate of deactivate it.
 *
 * @author Voltariuss
 * @since 0.1.0
 */
public abstract class ModuleDeclarer {

  private final String moduleName;
  private final JavaPlugin plugin;

  protected ModuleDeclarer(@NotNull String moduleName) {
    Objects.requireNonNull(moduleName);

    this.moduleName = moduleName;
    this.plugin = JavaPlugin.getPlugin(FallenVadersPlugin.class);
  }

  // TODO: use logger instead of System.out

  /**
   * Executes the necessary statements to enable the targeted module.
   */
  public void onEnable() {
    System.out.println("Enabling of the module " + moduleName);
  }

  /**
   * Executes the necessary statements to disable the targeted module.
   */
  public void onDisable() {
    System.out.println("Disabling of the module " + moduleName);
  }

  /**
   * Returns the module's name.
   *
   * @return the module's name.
   */
  public final @NotNull String getModuleName() {
    return moduleName;
  }

  /**
   * Returns the singleton instance {@link JavaPlugin} associated with the current execution context.
   *
   * @return the singleton instance {@link JavaPlugin} associated with the current execution context.
   */
  public @NotNull JavaPlugin getPlugin() {
    return plugin;
  }
}
