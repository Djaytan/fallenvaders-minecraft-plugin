package fr.fallenvaders.minecraft.plugin.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import fr.fallenvaders.minecraft.plugin.modules.CompleteModuleRegisterInitializer;
import fr.fallenvaders.minecraft.plugin.modules.ModuleRegisterInitializer;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.Objects;

/**
 * Guice module of the project.
 *
 * @author Voltariuss
 * @since 0.2.0
 */
public final class FallenVadersModule extends AbstractModule {

  private final JavaPlugin plugin;

  /**
   * Constructor.
   *
   * @param plugin The Bukkit plugin.
   */
  public FallenVadersModule(@NotNull JavaPlugin plugin) {
    Objects.requireNonNull(plugin);
    this.plugin = plugin;
  }

  @Override
  public void configure() {
    bind(ModuleRegisterInitializer.class).to(CompleteModuleRegisterInitializer.class);
  }

  @Provides
  @Singleton
  public @NotNull JavaPlugin providesPlugin() {
    return plugin;
  }

  @Provides
  @Singleton
  public @NotNull Logger getSLF4JLogger() {
    return plugin.getSLF4JLogger();
  }
}
