package fr.fallenvaders.minecraft.mail_box;

import fr.fallenvaders.minecraft.mail_box.commands.CmdMailbox;
import fr.fallenvaders.minecraft.mail_box.data_manager.MailBoxController;
import fr.fallenvaders.minecraft.mail_box.listeners.JoinListener;
import fr.fallenvaders.minecraft.mail_box.listeners.QuitListener;
import fr.fallenvaders.minecraft.mail_box.player_manager.PlayerManager;
import fr.fallenvaders.minecraft.mail_box.utils.LangManager;
import fr.minuskube.inv.InventoryManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MailBox {

  private static InventoryManager manager;

  public static InventoryManager getManager() {
    return manager;
  }

  public static JavaPlugin main;

  public static void activateModule(JavaPlugin javaPlugin) {
    main = javaPlugin;

    LangManager.load();

    main.getCommand(CmdMailbox.CMD_LABEL).setExecutor(new CmdMailbox());

    manager = new InventoryManager(main);
    manager.init();
    PlayerManager.getInstance().init();
    MailBoxController.initialize();

    main.getServer().getPluginManager().registerEvents(new JoinListener(), main);
    main.getServer().getPluginManager().registerEvents(new QuitListener(), main);
  }
}
