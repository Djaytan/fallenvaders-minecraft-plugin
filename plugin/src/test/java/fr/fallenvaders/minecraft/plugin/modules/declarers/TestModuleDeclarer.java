package fr.fallenvaders.minecraft.plugin.modules.declarers;

import fr.fallenvaders.minecraft.plugin.modules.ModuleDeclarer;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents a test {@link ModuleDeclarer} implementation.
 *
 * @author Voltariuss
 * @since 0.2.0
 */
public class TestModuleDeclarer extends ModuleDeclarer {

  public static final String MODULE_NAME = "test-module";

  public TestModuleDeclarer(@NotNull JavaPlugin javaPlugin) {
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
