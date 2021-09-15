package fr.fallenvaders.minecraft.plugin.modules.declarers;

import fr.fallenvaders.minecraft.plugin.modules.ModuleDeclarer;

/**
 * This class represents the {@link ModuleDeclarer} for the "MiniEvents" module.
 *
 * @author Glynix
 * @since 0.1.0
 */
public class MiniEventsModuleDeclarer extends ModuleDeclarer {

  public static final String MODULE_NAME = "mini-events";

  public MiniEventsModuleDeclarer() {
    super(MODULE_NAME);
  }

  @Override
  public void onEnable() {}
}
