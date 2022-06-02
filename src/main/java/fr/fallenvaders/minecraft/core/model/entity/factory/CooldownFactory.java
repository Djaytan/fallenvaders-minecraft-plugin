package fr.fallenvaders.minecraft.core.model.entity.factory;

import fr.fallenvaders.minecraft.core.model.entity.Cooldown;
import fr.fallenvaders.minecraft.core.model.service.api.parameter.CooldownType;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents the factory of {@link Cooldown} entity.
 *
 * @author Djaytan
 * @since 0.3.0
 */
@Singleton
public class CooldownFactory {

  private final Clock clock;

  /**
   * Constructor.
   *
   * @param clock The clock.
   */
  @Inject
  public CooldownFactory(@NotNull Clock clock) {
    this.clock = clock;
  }

  /**
   * Creates an instance of {@link Cooldown} according to the specified arguments.
   *
   * @param cooldownType The cooldown type.
   * @param playerUuid The player's UUID associated to the cooldown to create.
   * @param cooldownDuration The duration of the cooldown to create.
   * @return An instance of Cooldown according to the specified arguments.
   */
  public @NotNull Cooldown create(
      @NotNull CooldownType cooldownType,
      @NotNull UUID playerUuid,
      @NotNull Duration cooldownDuration) {
    LocalDateTime startCooldownDateTime = LocalDateTime.now(clock);
    return new Cooldown(clock, cooldownType, playerUuid, startCooldownDateTime, cooldownDuration);
  }
}
