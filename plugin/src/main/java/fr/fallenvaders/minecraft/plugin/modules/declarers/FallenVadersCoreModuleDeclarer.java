package fr.fallenvaders.minecraft.plugin.modules.declarers;

import fr.fallenvaders.minecraft.core.FallenVadersCore;
import fr.fallenvaders.minecraft.plugin.modules.ModuleDeclarer;

/**
 * This class represents the {@link ModuleDeclarer} for the "FallenVadersCore" module.
 *
 * @author Voltariuss
 * @since 0.1.0
 */
public class FallenVadersCoreModuleDeclarer extends ModuleDeclarer {

  public static final String MODULE_NAME = "fallenvaders-core";

  public FallenVadersCoreModuleDeclarer() {
    super(MODULE_NAME);
  }

  @Override
  public void onEnable() {
    FallenVadersCore.enableModule(getPlugin());
  }

  @Override
  public void onDisable() {
    FallenVadersCore.disableModule(getPlugin());
  }
}
