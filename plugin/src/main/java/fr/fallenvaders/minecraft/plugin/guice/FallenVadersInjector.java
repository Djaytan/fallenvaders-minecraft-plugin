package fr.fallenvaders.minecraft.plugin.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import fr.fallenvaders.minecraft.plugin.FallenVadersPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * Applies Dependency Injection pattern through Guice library.
 *
 * @author Voltariuss
 * @since 0.2.0
 */
public class FallenVadersInjector {
  /**
   * Injects dependencies through Guice to existing instances (e.g. {@link FallenVadersPlugin}.
   *
   * @param plugin The unique {@link FallenVadersPlugin} instance where to inject dependencies.
   */
  public void inject(@NotNull FallenVadersPlugin plugin) {
    Module module = new FallenVadersModule(plugin);
    Injector injector = Guice.createInjector(module);
    injector.injectMembers(plugin);
  }
}
