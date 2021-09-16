package fr.fallenvaders.minecraft.plugin.modules.declarers;

import fr.fallenvaders.minecraft.mail_box.MailBox;
import fr.fallenvaders.minecraft.plugin.modules.ModuleDeclarer;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents the {@link ModuleDeclarer} for the "MailBox" module.
 *
 * @author bletrazer
 * @since 0.1.0
 */
public class MailBoxModuleDeclarer extends ModuleDeclarer {

  public static final String MODULE_NAME = "mail-box";

  /**
   * Constructor.
   *
   * @param javaPlugin The Bukkit plugin.
   */
  public MailBoxModuleDeclarer(@NotNull JavaPlugin javaPlugin) {
    super(javaPlugin, MODULE_NAME);
  }

  @Override
  public void onEnable() {
    MailBox.activateModule(this.getJavaPlugin());
  }

  @Override
  public void onDisable() {
    // Nothing to do
  }
}
