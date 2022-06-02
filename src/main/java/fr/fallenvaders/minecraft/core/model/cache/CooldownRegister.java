package fr.fallenvaders.minecraft.core.model.cache;

import com.google.common.base.Preconditions;
import fr.fallenvaders.minecraft.core.model.entity.Cooldown;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.annotation.concurrent.NotThreadSafe;
import javax.inject.Singleton;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

/**
 * This class represents a {@link Cooldown} register.
 *
 * <p>/!\ This class isn't thread-safe yet.
 *
 * @author Djaytan
 * @since 0.3.0
 */
@Singleton
@NotThreadSafe
// TODO: make it thread-safe
public class CooldownRegister {

  private final Map<UUID, Set<Cooldown>> cooldownMap = new HashMap<>();

  /**
   * Searches and returns the list of cooldowns associated to the given player's UUID.
   *
   * @param playerUuid The player's UUID.
   * @return The list of cooldowns associated to the given player's UUID.
   */
  @Unmodifiable
  public @NotNull Set<Cooldown> getCooldowns(@NotNull UUID playerUuid) {
    Preconditions.checkNotNull(playerUuid);

    if (!cooldownMap.containsKey(playerUuid)) {
      return Collections.emptySet();
    }
    return Set.copyOf(cooldownMap.get(playerUuid));
  }

  /**
   * Registers the specified Cooldown into the register.
   *
   * <p>If a Cooldown with the same CooldownType and player's UUID exists, then it will be replaced
   * by the new one.
   *
   * @param cooldown The cooldown to put into the register.
   */
  public void registerCooldown(@NotNull Cooldown cooldown) {
    Preconditions.checkNotNull(cooldown);

    if (!cooldownMap.containsKey(cooldown.getPlayerUuid())) {
      cooldownMap.put(cooldown.getPlayerUuid(), new HashSet<>());
    }

    Set<Cooldown> cooldowns = cooldownMap.get(cooldown.getPlayerUuid());
    cooldowns.removeIf(cd -> cd.getCooldownType() == cooldown.getCooldownType());
    cooldowns.add(cooldown);
  }
}
