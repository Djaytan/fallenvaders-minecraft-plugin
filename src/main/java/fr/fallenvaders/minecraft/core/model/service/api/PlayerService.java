package fr.fallenvaders.minecraft.core.model.service.api;

import java.time.Duration;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

/**
 * An interface that represents a stateless player service.
 *
 * @author Djaytan
 * @since 0.3.0
 */
public interface PlayerService {

  /**
   * Gets and returns the remaining "heal" cooldown for the specified player.
   *
   * <p>A "heal" cooldown is considered as elapsed when the duration between the start cooldown time
   * and now is higher than the implementation specific heal cooldown value.
   *
   * @param playerUuid The player's UUID.
   * @return The remaining duration of the "heal" cooldown for the specified player.
   */
  @NotNull
  Duration getRemainingHealCooldown(@NotNull UUID playerUuid);

  /**
   * Gets and returns the remaining "feed" cooldown for the specified player.
   *
   * <p>A "feed" cooldown is considered as elapsed when the duration between the start cooldown time
   * and now is higher than the implementation specific feed cooldown value.
   *
   * @param playerUuid The player's UUID.
   * @return The remaining duration of the "feed" cooldown for the specified player.
   */
  @NotNull
  Duration getRemainingFeedCooldown(@NotNull UUID playerUuid);

  /**
   * Starts the "heal" cooldown for the given player.
   *
   * @param playerUuid The player's UUID.
   */
  void startHealCooldown(@NotNull UUID playerUuid);

  /**
   * Starts the "feed" cooldown for the given player.
   *
   * @param playerUuid The player's UUID.
   */
  void startFeedCooldown(@NotNull UUID playerUuid);
}
