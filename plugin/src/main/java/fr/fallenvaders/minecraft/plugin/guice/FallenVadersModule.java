package fr.fallenvaders.minecraft.plugin.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import fr.fallenvaders.minecraft.plugin.FallenVadersPlugin;
import fr.fallenvaders.minecraft.plugin.modules.CompleteModuleRegisterInitializer;
import fr.fallenvaders.minecraft.plugin.modules.ModuleRegisterInitializer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * Guice module of the project.
 *
 * @author Voltariuss
 * @since 0.2.0
 */
public class FallenVadersModule extends AbstractModule {
  private final JavaPlugin plugin;

  public FallenVadersModule(JavaPlugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public void configure() {
    bind(JavaPlugin.class).to(FallenVadersPlugin.class);
    bind(ModuleRegisterInitializer.class).to(CompleteModuleRegisterInitializer.class);
  }

  @Provides
  @Singleton
  public JavaPlugin providesPlugin() {
    return plugin;
  }

  @Provides
  @Singleton
  public Logger providesLogger() {
    return plugin.getLogger();
  }
}
