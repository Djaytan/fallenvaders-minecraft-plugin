package fr.fallenvaders.minecraft.plugin.modules;

import fr.fallenvaders.minecraft.mail_box.MailBox;
import fr.fallenvaders.minecraft.mail_box.commands.CmdMailbox;
import fr.fallenvaders.minecraft.mail_box.data_manager.MailBoxController;
import fr.fallenvaders.minecraft.mail_box.listeners.JoinListener;
import fr.fallenvaders.minecraft.mail_box.listeners.QuitListener;
import fr.fallenvaders.minecraft.mail_box.player_manager.PlayerManager;
import fr.fallenvaders.minecraft.mail_box.sql.SQLConnection;
import fr.fallenvaders.minecraft.mail_box.utils.LangManager;
import fr.fallenvaders.minecraft.plugin.FallenVadersPlugin;
import fr.minuskube.inv.InventoryManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

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
