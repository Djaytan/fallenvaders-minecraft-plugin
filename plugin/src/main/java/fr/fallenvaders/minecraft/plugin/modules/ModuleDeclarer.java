package fr.fallenvaders.minecraft.plugin.modules;

import fr.fallenvaders.minecraft.plugin.FallenVadersPlugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

// TODO: add JavaDoc
// TODO: migrate to Guice DI usage
public abstract class ModuleDeclarer {

  private final String moduleName;
  private final JavaPlugin plugin;

  protected ModuleDeclarer(@NotNull String moduleName) {
    Objects.requireNonNull(moduleName);

    this.moduleName = moduleName;
    this.plugin = JavaPlugin.getPlugin(FallenVadersPlugin.class);
  }

  // TODO: use logger instead of System.out
  public void onEnable() {
    System.out.println("Enabling of the module " + moduleName);
  }

  public void onDisable() {
    System.out.println("Disabling of the module " + moduleName);
  }

  public final @NotNull String getModuleName() {
    return moduleName;
  }

  public @NotNull JavaPlugin getPlugin() {
    return plugin;
  }
}
