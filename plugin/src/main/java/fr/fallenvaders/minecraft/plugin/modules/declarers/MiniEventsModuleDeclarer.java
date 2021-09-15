package fr.fallenvaders.minecraft.plugin.modules.declarers;

import fr.fallenvaders.minecraft.plugin.modules.ModuleDeclarer;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents the {@link ModuleDeclarer} for the "MiniEvents" module.
 *
 * @author Glynix
 * @since 0.1.0
 */
public class MiniEventsModuleDeclarer extends ModuleDeclarer {

  public static final String MODULE_NAME = "mini-events";

  /**
   * Constructor.
   *
   * @param javaPlugin The Java Bukkit plugin.
   */
  public MiniEventsModuleDeclarer(@NotNull JavaPlugin javaPlugin) {
    super(javaPlugin, MODULE_NAME);
  }

  @Override
  public void onEnable() {
    // Nothing to do
  }

  @Override
  public void onDisable() {
    // Nothing to do
  }
}
