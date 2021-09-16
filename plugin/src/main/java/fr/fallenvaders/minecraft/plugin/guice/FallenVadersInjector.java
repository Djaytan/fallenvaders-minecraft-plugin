package fr.fallenvaders.minecraft.plugin.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Applies Dependency Injection pattern through Guice library.
 *
 * @author Voltariuss
 * @since 0.2.0
 */
public final class FallenVadersInjector {

  /** Constructor. */
  private FallenVadersInjector() {}

  /**
   * Injects dependencies through Guice.
   *
   * @param plugin The Bukkit plugin.
   */
  public static void inject(@NotNull JavaPlugin plugin) {
    Objects.requireNonNull(plugin);

    Module module = new FallenVadersModule(plugin);
    Injector injector = Guice.createInjector(module);
    injector.injectMembers(plugin);
  }
}
