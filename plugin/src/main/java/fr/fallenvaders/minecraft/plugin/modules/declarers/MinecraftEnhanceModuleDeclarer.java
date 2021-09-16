package fr.fallenvaders.minecraft.plugin.modules.declarers;

import fr.fallenvaders.minecraft.mc_enhancer.controller.commands.PingCmd;
import fr.fallenvaders.minecraft.plugin.modules.ModuleDeclarer;
import fr.fallenvaders.minecraft.plugin.modules.ModuleEnum;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents the {@link ModuleDeclarer} for the "MinecraftEnhance" module.
 *
 * @author Voltariuss
 * @since 0.1.0
 */
public class MinecraftEnhanceModuleDeclarer extends ModuleDeclarer {

  /**
   * Constructor.
   *
   * @param javaPlugin The Bukkit plugin.
   */
  public MinecraftEnhanceModuleDeclarer(@NotNull JavaPlugin javaPlugin) {
    super(javaPlugin, ModuleEnum.MINECRAFT_ENHANCE.getModuleName());
  }

  @Override
  public void onEnable() {
    getJavaPlugin().getCommand("ping").setExecutor(new PingCmd());
  }

  @Override
  public void onDisable() {
    // Nothing to do
  }
}
