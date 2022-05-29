package fr.fallenvaders.minecraft.core.model.config;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

@Singleton
public class FallenVadersConfig {

  private final Server server;

  @Inject
  public FallenVadersConfig(@NotNull Server server) {
    this.server = server;
  }

  private static final double SPAWN_LOCATION_X = 0.0D;
  private static final double SPAWN_LOCATION_Y = 67.0D;
  private static final double SPAWN_LOCATION_Z = 17.0D;
  private static final float SPAWN_LOCATION_YAW = 180.0f;
  private static final float SPAWN_LOCATION_PITCH = 0.0f;
  private static final String SPAWN_LOCATION_WORLD_NAME = "Faction";

  public @NotNull Location getSpawnLocation() {
    World world = server.getWorld(SPAWN_LOCATION_WORLD_NAME);
    return new Location(
        world,
        SPAWN_LOCATION_X,
        SPAWN_LOCATION_Y,
        SPAWN_LOCATION_Z,
        SPAWN_LOCATION_YAW,
        SPAWN_LOCATION_PITCH);
  }
}
