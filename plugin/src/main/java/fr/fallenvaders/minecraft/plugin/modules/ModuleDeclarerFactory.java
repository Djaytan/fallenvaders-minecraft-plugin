package fr.fallenvaders.minecraft.plugin.modules;

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
public final class ModuleDeclarerFactory {

  private final JavaPlugin javaPlugin;

  /**
   * Constructor.
   *
   * @param javaPlugin The Bukkit plugin.
   */
  @Inject
  public ModuleDeclarerFactory(@NotNull JavaPlugin javaPlugin) {
    Objects.requireNonNull(javaPlugin);
    this.javaPlugin = javaPlugin;
  }

  /**
   * Creates a {@link ModuleDeclarer} according to the specified module's class.
   *
   * @param moduleClass The module's class to instantiate.
   * @return a {@link ModuleDeclarer} according to the specified module's class.
   */
  @NotNull
  public ModuleDeclarer createModule(@NotNull Class<? extends ModuleDeclarer> moduleClass)
      throws ModuleRegisterException {
    Objects.requireNonNull(moduleClass);

    try {
      return moduleClass.getDeclaredConstructor(JavaPlugin.class).newInstance(javaPlugin);
    } catch (Exception e) {
      throw new ModuleRegisterException(
          String.format(
              "Something went wrong during the instantiation of the module class '%s'.",
              moduleClass.getName()),
          e);
    }
  }
}
