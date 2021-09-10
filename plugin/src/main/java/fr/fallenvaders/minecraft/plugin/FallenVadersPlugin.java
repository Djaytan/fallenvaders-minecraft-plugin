package fr.fallenvaders.minecraft.plugin;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import fr.fallenvaders.minecraft.plugin.guice.FallenVadersInjector;
import fr.fallenvaders.minecraft.plugin.modules.CompleteModuleRegisterInitializer;
import fr.fallenvaders.minecraft.plugin.modules.ModuleRegister;
import fr.fallenvaders.minecraft.plugin.modules.ModuleRegisterException;
import fr.fallenvaders.minecraft.plugin.modules.ModuleRegisterInitializer;
import org.bukkit.plugin.java.JavaPlugin;

@Singleton
public class FallenVadersPlugin extends JavaPlugin {

  @Inject private ModuleRegister moduleRegister;

  @Override
  public void onEnable() {
    FallenVadersInjector fvInjector = new FallenVadersInjector();
    fvInjector.inject(this);

    this.saveDefaultConfig();

    try {
      ModuleRegisterInitializer moduleInitializer = new CompleteModuleRegisterInitializer();
      moduleRegister = moduleInitializer.initialize();
      moduleRegister.enableModules();
      System.out.println("FallenVaders plugin enabled.");
    } catch (ModuleRegisterException e) {
      // TODO: better error management
      e.printStackTrace();
    }
  }

  @Override
  public void onDisable() {
    moduleRegister.disableModules();
    System.out.println("FallenVaders plugin disabled.");
  }
}
