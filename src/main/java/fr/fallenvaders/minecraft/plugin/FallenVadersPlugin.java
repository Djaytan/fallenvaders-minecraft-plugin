package fr.fallenvaders.minecraft.plugin;

import org.bukkit.plugin.java.JavaPlugin;

public class FallenVadersPlugin extends JavaPlugin {

  @Override
  public void onEnable() {
    getSLF4JLogger().warn("HELLO WORLD!");
  }
}
