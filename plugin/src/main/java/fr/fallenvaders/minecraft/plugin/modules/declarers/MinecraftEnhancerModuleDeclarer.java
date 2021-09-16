package fr.fallenvaders.minecraft.plugin.modules.declarers;

import fr.fallenvaders.minecraft.mc_enhancer.controller.commands.PingCmd;
import fr.fallenvaders.minecraft.plugin.modules.ModuleDeclarer;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents the {@link ModuleDeclarer} for the "MinecraftEnhance" module.
 *
 * @author Voltariuss
 * @since 0.1.0
 */
public final class MinecraftEnhancerModuleDeclarer extends ModuleDeclarer {

  public static final String MODULE_NAME = "minecraft-enhancer";

  /**
   * Constructor.
   *
   * @param javaPlugin The Bukkit plugin.
   */
  public MinecraftEnhancerModuleDeclarer(@NotNull JavaPlugin javaPlugin) {
    super(javaPlugin, MODULE_NAME);
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
