package fr.fallenvaders.minecraft.plugin.modules.declarers;

import fr.fallenvaders.minecraft.mail_box.MailBox;
import fr.fallenvaders.minecraft.plugin.modules.ModuleDeclarer;

public class MailBoxModuleDeclarer extends ModuleDeclarer {

  public static final String MODULE_NAME = "mail-box";

  public MailBoxModuleDeclarer() {
    super(MODULE_NAME);
  }

  @Override
  public void onEnable() {
    MailBox.activateModule(this.getPlugin());
  }
}
