package fr.fallenvaders.minecraft.plugin;

import fr.fallenvaders.minecraft.plugin.guice.FallenVadersInjector;
import fr.fallenvaders.minecraft.plugin.modules.ModuleRegister;
import fr.fallenvaders.minecraft.plugin.modules.ModuleRegisterException;
import fr.fallenvaders.minecraft.plugin.modules.ModuleRegisterInitializer;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * This class represents the Bukkit plugin.
 *
 * @author Voltariuss
 * @since 0.1.0
 */
@Singleton
public class FallenVadersPlugin extends JavaPlugin {

  @Inject private ModuleRegisterInitializer moduleRegInit;
  @Inject private ModuleRegister moduleRegister;

  @Override
  public void onEnable() {
    // Guice setup
    FallenVadersInjector fvInjector = new FallenVadersInjector();
    fvInjector.inject(this);

    // Prepare config
    this.saveDefaultConfig();

    // Initializes modules
    try {
      moduleRegInit.initialize();
      moduleRegister.enableModules();
      getSLF4JLogger().info("FallenVaders plugin successfully enabled.");
    } catch (ModuleRegisterException e) {
      // TODO: better error management
      getSLF4JLogger().error("An error has occurred during modules registration.", e);
    }
  }

  @Override
  public void onDisable() {
    moduleRegister.disableModules();
    getSLF4JLogger().info("FallenVaders plugin disabled.");
  }
}
