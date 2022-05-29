package fr.fallenvaders.minecraft.core.plugin;

import fr.fallenvaders.minecraft.core.controller.listener.PlayerRespawnListener;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

// TODO: autoregistering of listeners with @Listener annotation
@Singleton
public class ListenerRegister {

  private final PlayerRespawnListener playerRespawnListener;
  private final JavaPlugin plugin;
  private final PluginManager pluginManager;

  @Inject
  public ListenerRegister(
      @NotNull PlayerRespawnListener playerRespawnListener,
      @NotNull JavaPlugin plugin,
      @NotNull PluginManager pluginManager) {
    this.playerRespawnListener = playerRespawnListener;
    this.plugin = plugin;
    this.pluginManager = pluginManager;
  }

  public void registerListeners() {
    pluginManager.registerEvents(playerRespawnListener, plugin);
  }
}
