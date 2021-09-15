package fr.fallenvaders.minecraft.plugin.modules.declarers;

import fr.fallenvaders.minecraft.justice_hands.JusticeHands;
import fr.fallenvaders.minecraft.plugin.modules.ModuleDeclarer;

/**
 * This class represents the {@link ModuleDeclarer} for the "JusticeHands" module.
 *
 * @author Glynix
 * @since 0.1.0
 */
public class JusticeHandsModuleDeclarer extends ModuleDeclarer {

  public static final String MODULE_NAME = "justice-hands";

  public JusticeHandsModuleDeclarer() {
    super(MODULE_NAME);
  }

  @Override
  public void onEnable() {
    JusticeHands.enableModule(getPlugin());
  }

  @Override
  public void onDisable() {
    JusticeHands.disableModule(getPlugin());
  }
}
