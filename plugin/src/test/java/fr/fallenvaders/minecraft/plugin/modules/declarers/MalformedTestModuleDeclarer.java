package fr.fallenvaders.minecraft.plugin.modules.declarers;

import fr.fallenvaders.minecraft.plugin.modules.ModuleDeclarer;

/**
 * This class represents a malformed test {@link ModuleDeclarer} implementation.
 *
 * @author Voltariuss
 * @since 0.2.0
 */
@SuppressWarnings("ConstantConditions")
public class MalformedTestModuleDeclarer extends ModuleDeclarer {

  public static final String MODULE_NAME = "malformed-test-module";

  public MalformedTestModuleDeclarer() {
    super(null, MODULE_NAME); // This code will never be executed
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
