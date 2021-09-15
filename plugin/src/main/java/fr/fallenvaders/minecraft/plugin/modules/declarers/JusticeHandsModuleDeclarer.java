package fr.fallenvaders.minecraft.plugin.modules.declarers;

import fr.fallenvaders.minecraft.justice_hands.JusticeHands;
import fr.fallenvaders.minecraft.plugin.modules.ModuleDeclarer;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents the {@link ModuleDeclarer} for the "JusticeHands" module.
 *
 * @author Glynix
 * @since 0.1.0
 */
public class JusticeHandsModuleDeclarer extends ModuleDeclarer {

  public static final String MODULE_NAME = "justice-hands";

  /**
   * Constructor.
   *
   * @param javaPlugin The Java Bukkit plugin.
   */
  public JusticeHandsModuleDeclarer(@NotNull JavaPlugin javaPlugin) {
    super(javaPlugin, MODULE_NAME);
  }

  @Override
  public void onEnable() {
    JusticeHands.enableModule(getJavaPlugin());
  }

  @Override
  public void onDisable() {
    JusticeHands.disableModule(getJavaPlugin());
  }
}
