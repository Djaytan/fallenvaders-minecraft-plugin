package fr.fallenvaders.minecraft.core.model.service.api;

import fr.fallenvaders.minecraft.core.model.entity.Cooldown;
import fr.fallenvaders.minecraft.core.model.service.api.parameter.CooldownType;
import java.util.Optional;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

/**
 * An interface that represents a stateless cooldown service.
 *
 * @author Djaytan
 * @since 0.3.0
 */
public interface CooldownService {

  /**
   * Gets and returns the cooldown of the given type for the specified player.
   *
   * <p>A cooldown is considered as elapsed when the duration between the start cooldown time and
   * <code>now</code> is higher than the implementation specific cooldown value for the given type.
   *
   * @param cooldownType The type of cooldown.
   * @param playerUuid The player's UUID.
   * @return The remaining duration of the cooldown of the given for the specified player.
   */
  @NotNull
  Optional<Cooldown> getCooldown(@NotNull CooldownType cooldownType, @NotNull UUID playerUuid);

  /**
   * Starts the cooldown of the given type for the given player.
   *
   * @param cooldownType The cooldown type.
   * @param playerUuid The player's UUID.
   */
  void startCooldown(@NotNull CooldownType cooldownType, @NotNull UUID playerUuid);
}
