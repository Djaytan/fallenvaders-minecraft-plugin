package fr.fallenvaders.minecraft.plugin.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import fr.fallenvaders.minecraft.plugin.FallenVadersPlugin;
import fr.fallenvaders.minecraft.plugin.modules.ModuleRegister;
import org.jetbrains.annotations.NotNull;

/**
 * Applies Dependency Injection pattern through Guice library.
 *
 * @author Voltariuss
 * @since 0.2.0
 */
public class FallenVadersInjector {

  /**
   * Injects dependencies through Guice.
   *
   * @param plugin The unique {@link FallenVadersPlugin} instance to inject.
   */
  public void inject(@NotNull FallenVadersPlugin plugin) {
    Module module = new FallenVadersModule();
    Injector injector = Guice.createInjector(module);
    injector.injectMembers(plugin);
  }
}
