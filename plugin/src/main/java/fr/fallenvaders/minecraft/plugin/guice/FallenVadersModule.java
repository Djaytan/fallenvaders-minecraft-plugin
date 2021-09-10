package fr.fallenvaders.minecraft.plugin.guice;

import com.google.inject.AbstractModule;
import fr.fallenvaders.minecraft.plugin.FallenVadersPlugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Guice module of the project.
 *
 * @author Voltariuss
 * @since 0.2.0
 */
public class FallenVadersModule extends AbstractModule {

  @Override
  public void configure() {
    bind(JavaPlugin.class).to(FallenVadersPlugin.class);
  }
}
