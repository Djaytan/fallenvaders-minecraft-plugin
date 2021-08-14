package fr.fallenvaders.minecraft.plugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import fr.fallenvaders.minecraft.mc_enhancer.controller.commands.PingCmd;
import org.bukkit.plugin.java.JavaPlugin;

public class FallenVadersPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("ping").setExecutor(new PingCmd());
        System.out.println("FallenVadersPlugin: TEST");
    }
}
