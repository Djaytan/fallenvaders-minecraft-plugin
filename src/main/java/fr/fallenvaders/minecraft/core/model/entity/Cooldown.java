package fr.fallenvaders.minecraft.core.model.entity;

import com.google.common.base.Preconditions;
import fr.fallenvaders.minecraft.core.model.service.api.parameter.CooldownType;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@ToString
@EqualsAndHashCode
public class Cooldown {

  // ---------- DEPENDENCIES ----------
  Clock clock;

  // ---------- DATA ----------
  CooldownType cooldownType;
  UUID playerUuid;
  LocalDateTime startCooldownDateTime;
  Duration cooldownDuration;

  public Cooldown(
      @NotNull Clock clock,
      @NotNull CooldownType cooldownType,
      @NotNull UUID playerUuid,
      @NotNull LocalDateTime startCooldownDateTime,
      @NotNull Duration cooldownDuration) {
    setClock(clock);
    setCooldownType(cooldownType);
    setPlayerUuid(playerUuid);
    setStartCooldownDateTime(startCooldownDateTime);
    setCooldownDuration(cooldownDuration);
  }

  public boolean isCooldownElapsed() {
    Duration rc = getRemainingCooldown();
    return rc.isZero() || rc.isNegative();
  }

  public @NotNull Duration getRemainingCooldown() {
    LocalDateTime finalDateTime = LocalDateTime.now(clock);
    Duration timeElapsed = Duration.between(startCooldownDateTime, finalDateTime);
    return cooldownDuration.minus(timeElapsed);
  }

  // ---------- GETTERS ----------

  public @NotNull CooldownType getCooldownType() {
    return cooldownType;
  }

  public @NotNull UUID getPlayerUuid() {
    return playerUuid;
  }

  // ---------- SETTERS ----------

  private void setClock(@NotNull Clock clock) {
    Preconditions.checkNotNull(clock);
    this.clock = clock;
  }

  private void setCooldownType(@NotNull CooldownType cooldownType) {
    Preconditions.checkNotNull(cooldownType);
    this.cooldownType = cooldownType;
  }

  private void setPlayerUuid(@NotNull UUID playerUuid) {
    Preconditions.checkNotNull(playerUuid);
    this.playerUuid = playerUuid;
  }

  private void setStartCooldownDateTime(@NotNull LocalDateTime startCooldownDateTime) {
    Preconditions.checkNotNull(startCooldownDateTime);
    this.startCooldownDateTime = startCooldownDateTime;
  }

  private void setCooldownDuration(@NotNull Duration cooldownDuration) {
    Preconditions.checkNotNull(cooldownDuration);
    this.cooldownDuration = cooldownDuration;
  }
}
