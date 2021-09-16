package fr.fallenvaders.minecraft.plugin.modules.declarers;

import fr.fallenvaders.minecraft.core.FallenVadersCore;
import fr.fallenvaders.minecraft.plugin.modules.ModuleDeclarer;
import fr.fallenvaders.minecraft.plugin.modules.ModuleEnum;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

/**
 * This class represents the {@link ModuleDeclarer} for the "FallenVadersCore" module.
 *
 * @author Voltariuss
 * @since 0.1.0
 */
public class FallenVadersCoreModuleDeclarer extends ModuleDeclarer {

  /**
   * Constructor.
   *
   * @param javaPlugin The Java Bukkit plugin.
   */
  public FallenVadersCoreModuleDeclarer(@NotNull JavaPlugin javaPlugin) {
    super(javaPlugin, ModuleEnum.FALLEN_VADERS_CORE.getModuleName());
  }

  @Override
  public void onEnable() {
    FallenVadersCore.enableModule(getJavaPlugin());
  }

  @Override
  public void onDisable() {
    FallenVadersCore.disableModule(getJavaPlugin());
  }
}
