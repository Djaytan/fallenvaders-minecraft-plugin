package fr.fallenvaders.minecraft.core.model.dao.api;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

/**
 * An interface that represents the player DAO.
 *
 * @author Djaytan
 * @since 0.3.0
 */
public interface PlayerDao {

  @NotNull
  Optional<Duration> getDurationSinceLastHeal(@NotNull UUID playerUuid);

  @NotNull
  Optional<Duration> getDurationSinceLastFeed(@NotNull UUID playerUuid);

  void startHealCooldown(@NotNull UUID playerUuid);

  void startFeedCooldown(@NotNull UUID playerUuid);
}
