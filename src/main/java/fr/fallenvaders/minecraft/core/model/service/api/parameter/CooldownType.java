package fr.fallenvaders.minecraft.core.model.service.api.parameter;

import java.time.Duration;
import org.jetbrains.annotations.NotNull;

/**
 * An enum that represents a type of cooldown.
 *
 * @author Djaytan
 * @since 0.3.0
 */
// TODO: respect the Open-Close Principle (SOLID)
public enum CooldownType {
  HEAL(Duration.ofSeconds(300)),
  FEED(Duration.ofSeconds(300));

  private final Duration cooldownDuration;

  CooldownType(@NotNull Duration cooldownDuration) {
    this.cooldownDuration = cooldownDuration;
  }

  public @NotNull Duration getCooldownDuration() {
    return cooldownDuration;
  }
}
