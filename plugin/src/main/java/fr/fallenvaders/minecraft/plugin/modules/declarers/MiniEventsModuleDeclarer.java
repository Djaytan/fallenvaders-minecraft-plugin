package fr.fallenvaders.minecraft.plugin.modules.declarers;

import fr.fallenvaders.minecraft.plugin.modules.ModuleDeclarer;
import fr.fallenvaders.minecraft.plugin.modules.ModuleEnum;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents the {@link ModuleDeclarer} for the "MiniEvents" module.
 *
 * @author Glynix
 * @since 0.1.0
 */
public class MiniEventsModuleDeclarer extends ModuleDeclarer {

  /**
   * Constructor.
   *
   * @param javaPlugin The Bukkit plugin.
   */
  public MiniEventsModuleDeclarer(@NotNull JavaPlugin javaPlugin) {
    super(javaPlugin, ModuleEnum.MINI_EVENTS.getModuleName());
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
