package fr.fallenvaders.minecraft.core;

import fr.fallenvaders.minecraft.core.mail_box.commands.Cmd_Mailbox;
import fr.fallenvaders.minecraft.core.mail_box.data_manager.MailBoxController;
import fr.fallenvaders.minecraft.core.mail_box.listeners.JoinListener;
import fr.fallenvaders.minecraft.core.mail_box.listeners.QuitListener;
import fr.fallenvaders.minecraft.core.mail_box.player_manager.PlayerManager;
import fr.fallenvaders.minecraft.core.mail_box.sql.SQLConnection;
import fr.fallenvaders.minecraft.core.mail_box.utils.LangManager;
import fr.minuskube.inv.InventoryManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class FallenVadersCorePlugin extends JavaPlugin {

    public static FallenVadersCorePlugin main;

    private static InventoryManager manager;

    public static InventoryManager getManager() {
        return manager;
    }

    @Override
    public void onEnable() {
        FallenVadersCorePlugin.main = this;
        this.saveDefaultConfig();
        enableMailbox(this);
    }

    public void enableMailbox(JavaPlugin main) {
        LangManager.load();

        SQLConnection.getInstance().setJdbc(SQLConnection.SGBD_TYPE_ROOT).setHost(main.getConfig().getString("database.host")).setDatabase(main.getConfig().getString("database.database"))
            .setUser(main.getConfig().getString("database.user")).setPassword(main.getConfig().getString("database.password")).connect();

        main.getCommand(Cmd_Mailbox.CMD_LABEL).setExecutor(new Cmd_Mailbox());

        if (SQLConnection.getInstance().getConnection() != null && SQLConnection.getInstance().isConnected()) {
            manager = new InventoryManager(main);
            manager.init();
            PlayerManager.getInstance().init();
            MailBoxController.initialize();


            main.getServer().getPluginManager().registerEvents(new JoinListener(), main);
            main.getServer().getPluginManager().registerEvents(new QuitListener(), main);

        } else {
            main.getLogger().log(Level.SEVERE, LangManager.getValue("connection_needed"));

        }
    }
}
