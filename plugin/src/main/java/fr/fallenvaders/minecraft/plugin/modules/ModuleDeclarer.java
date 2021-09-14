package fr.fallenvaders.minecraft.plugin.modules;

import fr.fallenvaders.minecraft.plugin.FallenVadersPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class ModuleDeclarer {

  private final String moduleName;
  private final JavaPlugin plugin;

  public ModuleDeclarer(String moduleName) {
    this.moduleName = moduleName;
    this.plugin = JavaPlugin.getPlugin(FallenVadersPlugin.class);
  }

  public void onEnable() {
    System.out.println("Enabling of the module " + moduleName);
  }

  public void onDisable() {
    System.out.println("Disabling of the module " + moduleName);
  }

  public final String getModuleName() {
    return moduleName;
  }

  public JavaPlugin getPlugin() {
    return plugin;
  }
}
