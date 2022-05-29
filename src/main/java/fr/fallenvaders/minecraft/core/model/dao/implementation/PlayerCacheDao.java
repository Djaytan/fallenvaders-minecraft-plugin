package fr.fallenvaders.minecraft.core.model.dao.implementation;

import com.google.common.base.Preconditions;
import fr.fallenvaders.minecraft.core.model.dao.api.PlayerDao;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.jetbrains.annotations.NotNull;

/**
 * A class that represents the cache implementation of the player DAO interface.
 *
 * @author Djaytan
 * @since 0.3.0
 */
@Singleton
public class PlayerCacheDao implements PlayerDao {

  private final Clock clock;

  private final Map<UUID, LocalDateTime> healCooldown = new HashMap<>();

  @Inject
  public PlayerCacheDao(@NotNull Clock clock) {
    this.clock = clock;
  }

  @Override
  public @NotNull Optional<Duration> getDurationSinceLastHeal(@NotNull UUID playerUuid) {
    Preconditions.checkNotNull(playerUuid);

    LocalDateTime initialDateTime = healCooldown.get(playerUuid);

    if (initialDateTime == null) {
      return Optional.empty();
    }

    LocalDateTime finalDateTime = LocalDateTime.now(clock);

    return Optional.of(Duration.between(initialDateTime, finalDateTime));
  }

  @Override
  public void startHealCooldown(@NotNull UUID playerUuid) {
    Preconditions.checkNotNull(playerUuid);

    LocalDateTime currentDateTime = LocalDateTime.now(clock);
    healCooldown.put(playerUuid, currentDateTime);
  }
}
