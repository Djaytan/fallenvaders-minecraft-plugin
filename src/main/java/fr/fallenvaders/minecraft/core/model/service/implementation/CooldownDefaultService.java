package fr.fallenvaders.minecraft.core.model.service.implementation;

import com.google.common.base.Preconditions;
import fr.fallenvaders.minecraft.core.model.dao.api.CooldownDao;
import fr.fallenvaders.minecraft.core.model.service.api.CooldownService;
import fr.fallenvaders.minecraft.core.model.service.api.parameter.CooldownType;
import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.jetbrains.annotations.NotNull;

/**
 * A class that represents the default implementation of {@link CooldownService} API.
 *
 * @author Djaytan
 * @since 0.3.0
 */
@Singleton
public class CooldownDefaultService implements CooldownService {

  /** The "heal" cooldown between each use. */
  private static final Duration HEAL_COOLDOWN = Duration.ofSeconds(300);
  /** The "feed" cooldown between each use. */
  private static final Duration FEED_COOLDOWN = Duration.ofSeconds(300);

  private final CooldownDao cooldownDao;

  @Inject
  public CooldownDefaultService(@NotNull CooldownDao cooldownDao) {
    this.cooldownDao = cooldownDao;
  }

  @Override
  public @NotNull Duration getRemainingCooldown(
      @NotNull CooldownType cooldownType, @NotNull UUID playerUuid) {
    Preconditions.checkNotNull(playerUuid);

    Optional<Duration> durationSinceLastFeed = cooldownDao.getDurationSinceLastFeed(playerUuid);

    if (durationSinceLastFeed.isEmpty()) {
      return Duration.ZERO;
    }

    return FEED_COOLDOWN.minus(durationSinceLastFeed.get());
  }

  @Override
  public void startCooldown(@NotNull CooldownType cooldownType, @NotNull UUID playerUuid) {
    Preconditions.checkNotNull(playerUuid);

    cooldownDao.startFeedCooldown(playerUuid);
  }
}
