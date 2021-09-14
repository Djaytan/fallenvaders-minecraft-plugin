package fr.fallenvaders.minecraft.plugin.modules.declarers;

import fr.fallenvaders.minecraft.mc_enhancer.controller.commands.PingCmd;
import fr.fallenvaders.minecraft.plugin.modules.ModuleDeclarer;

public class MinecraftEnhanceModuleDeclarer extends ModuleDeclarer {

  public static final String MODULE_NAME = "minecraft-enhancer";

  public MinecraftEnhanceModuleDeclarer() {
    super(MODULE_NAME);
  }

  @Override
  public void onEnable() {
    System.out.println("Module: TEST");
    getPlugin().getCommand("ping").setExecutor(new PingCmd());
  }
}
