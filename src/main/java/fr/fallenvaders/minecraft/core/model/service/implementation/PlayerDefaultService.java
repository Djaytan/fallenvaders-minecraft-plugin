package fr.fallenvaders.minecraft.core.model.service.implementation;

import com.google.common.base.Preconditions;
import fr.fallenvaders.minecraft.core.model.dao.api.PlayerDao;
import fr.fallenvaders.minecraft.core.model.service.api.PlayerService;
import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.jetbrains.annotations.NotNull;

/**
 * A class that represents the default implementation of {@link PlayerService} API.
 *
 * @author Djaytan
 * @since 0.3.0
 */
@Singleton
public class PlayerDefaultService implements PlayerService {

  /** The "heal" cooldown between each use. */
  private static final Duration HEAL_COOLDOWN = Duration.ofSeconds(300);
  /** The "feed" cooldown between each use. */
  private static final Duration FEED_COOLDOWN = Duration.ofSeconds(300);

  private final PlayerDao playerDao;

  @Inject
  public PlayerDefaultService(@NotNull PlayerDao playerDao) {
    this.playerDao = playerDao;
  }

  @Override
  public @NotNull Duration getRemainingHealCooldown(@NotNull UUID playerUuid) {
    Preconditions.checkNotNull(playerUuid);

    Optional<Duration> durationSinceLastHeal = playerDao.getDurationSinceLastHeal(playerUuid);

    if (durationSinceLastHeal.isEmpty()) {
      return Duration.ZERO;
    }

    return HEAL_COOLDOWN.minus(durationSinceLastHeal.get());
  }

  @Override
  public @NotNull Duration getRemainingFeedCooldown(@NotNull UUID playerUuid) {
    Preconditions.checkNotNull(playerUuid);

    Optional<Duration> durationSinceLastFeed = playerDao.getDurationSinceLastFeed(playerUuid);

    if (durationSinceLastFeed.isEmpty()) {
      return Duration.ZERO;
    }

    return FEED_COOLDOWN.minus(durationSinceLastFeed.get());
  }

  @Override
  public void startHealCooldown(@NotNull UUID playerUuid) {
    Preconditions.checkNotNull(playerUuid);

    playerDao.startHealCooldown(playerUuid);
  }

  @Override
  public void startFeedCooldown(@NotNull UUID playerUuid) {
    Preconditions.checkNotNull(playerUuid);

    playerDao.startFeedCooldown(playerUuid);
  }
}
