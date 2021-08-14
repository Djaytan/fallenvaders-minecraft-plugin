package fr.fallenvaders.minecraft.core;

import fr.fallenvaders.minecraft.core.fight_session.PluginController;
import fr.fallenvaders.minecraft.core.fight_session.commands.CmdFightSession;
import fr.fallenvaders.minecraft.core.fight_session.listeners.FightsEvents;
import org.bukkit.plugin.java.JavaPlugin;

public class FallenVadersCorePlugin extends JavaPlugin {
    public static FallenVadersCorePlugin main;

    @Override
    public void onEnable() {
        FallenVadersCorePlugin.main = this;
        this.saveDefaultConfig();
        enableFightSession(this);
    }

    @Override
    public void onDisable() {
        PluginController.getSessionManager().stopAll();
    }

    public void enableFightSession(JavaPlugin main) {
        PluginController.init();

        //Register commands
        this.getCommand(CmdFightSession.CMD_LABEL).setExecutor(new CmdFightSession());

        //Register listeners
        this.getServer().getPluginManager().registerEvents(new FightsEvents(), this);

    }
}
