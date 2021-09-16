package fr.fallenvaders.minecraft.plugin.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import fr.fallenvaders.minecraft.plugin.guice.FallenVadersModule;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * Test injector class from Guice.
 *
 * @author Voltariuss
 * @since 0.2.0
 */
public class TestInjector {

  /**
   * Constructor.
   */
  private TestInjector() {}

  /**
   * Injects dependencies through Guice.
   *
   * @param javaPlugin The mocked Bukkit plugin.
   * @param testObject The test object to inject dependencies.
   */
  public static void inject(@NotNull JavaPlugin javaPlugin, @NotNull Object testObject) {
    FallenVadersModule module = new FallenVadersModule(javaPlugin);
    Injector injector = Guice.createInjector(module);
    injector.injectMembers(testObject);
  }
}
