package fr.fallenvaders.minecraft.mc_enhancer;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import fr.fallenvaders.minecraft.mc_enhancer.controller.commands.PingCmd;
import org.bukkit.plugin.java.JavaPlugin;

public class McEnhancerPlugin extends JavaPlugin {

  private ProtocolManager protocolManager;

  @Override
  public void onEnable() {
    this.protocolManager = ProtocolLibrary.getProtocolManager();
    this.getCommand("ping").setExecutor(new PingCmd());
    System.out.println("TEST");
  }

  public ProtocolManager getProtocolManager() {
    return this.protocolManager;
  }
}
