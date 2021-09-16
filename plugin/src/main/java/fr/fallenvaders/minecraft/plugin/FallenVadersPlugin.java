package fr.fallenvaders.minecraft.plugin;

import fr.fallenvaders.minecraft.plugin.guice.FallenVadersInjector;
import fr.fallenvaders.minecraft.plugin.modules.ModuleRegisterService;
import fr.fallenvaders.minecraft.plugin.modules.ModuleRegisterException;
import fr.fallenvaders.minecraft.plugin.modules.ModuleRegisterInitializer;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

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
  @Inject private ModuleRegisterService moduleRegisterService;
  @Inject private Logger slf4jLogger;

  @Override
  public void onEnable() {
    // Guice setup
    FallenVadersInjector.inject(this);

    // Config preparation
    this.saveDefaultConfig();

    // Modules initialization
    try {
      moduleRegInit.initialize();
      moduleRegisterService.enableModules();
      slf4jLogger.info("FallenVaders plugin successfully enabled.");
    } catch (ModuleRegisterException e) {
      slf4jLogger.error("An error has occurred during modules registration.", e);
    }
    // TODO: FV-94 - better error management (catch all exceptions and allow the launch of some modules even
    // if some other ones fail
  }

  @Override
  public void onDisable() {
    moduleRegisterService.disableModules();
    slf4jLogger.info("FallenVaders plugin disabled.");
  }
}
