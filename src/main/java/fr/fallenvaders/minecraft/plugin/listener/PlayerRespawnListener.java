package fr.fallenvaders.minecraft.plugin.listener;

import fr.fallenvaders.minecraft.plugin.FallenVadersConfig;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.jetbrains.annotations.NotNull;

@Singleton
public class PlayerRespawnListener implements Listener {

  private final FallenVadersConfig fallenVadersConfig;

  @Inject
  public PlayerRespawnListener(@NotNull FallenVadersConfig fallenVadersConfig) {
    this.fallenVadersConfig = fallenVadersConfig;
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onPlayerRespawn(@NotNull PlayerRespawnEvent event) {
    event.setRespawnLocation(fallenVadersConfig.getSpawnLocation());
  }
}
