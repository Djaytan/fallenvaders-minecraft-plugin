package fr.fallenvaders.minecraft.plugin;

import fr.fallenvaders.minecraft.plugin.modules.CompleteModuleRegisterInitializer;
import fr.fallenvaders.minecraft.plugin.modules.ModuleRegister;
import fr.fallenvaders.minecraft.plugin.modules.ModuleRegisterException;
import fr.fallenvaders.minecraft.plugin.modules.ModuleRegisterInitializer;
import org.bukkit.plugin.java.JavaPlugin;

public class FallenVadersPlugin extends JavaPlugin {
  private ModuleRegister moduleRegister;

  @Override
  public void onEnable() {
    this.saveDefaultConfig();

    try {
      ModuleRegisterInitializer moduleInitializer = new CompleteModuleRegisterInitializer();
      moduleRegister = moduleInitializer.initialize();
      moduleRegister.enableModules();
      System.out.println("FallenVaders plugin enabled.");
    } catch (ModuleRegisterException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onDisable() {
    moduleRegister.disableModules();
    System.out.println("FallenVaders plugin disabled.");
  }
}
