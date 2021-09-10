package fr.fallenvaders.minecraft.plugin.guice;

import com.google.inject.AbstractModule;
import fr.fallenvaders.minecraft.plugin.FallenVadersPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public class FallenVadersModule extends AbstractModule {

  @Override
  public void configure() {
    bind(JavaPlugin.class).to(FallenVadersPlugin.class);
  }
}
