package fr.fallenvaders.minecraft.core.model.dao.implementation;

import com.google.common.base.Preconditions;
import fr.fallenvaders.minecraft.core.model.dao.api.CooldownDao;
import fr.fallenvaders.minecraft.core.model.entity.Cooldown;
import fr.fallenvaders.minecraft.core.model.service.api.parameter.CooldownType;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.jetbrains.annotations.NotNull;

/**
 * A class that represents the cache implementation of the cooldown DAO interface.
 *
 * @author Djaytan
 * @since 0.3.0
 */
@Singleton
public class CooldownCacheDao implements CooldownDao {

  private final Map<UUID, Cooldown> cooldownMap = new HashMap<>();

  @Override
  public @NotNull Optional<Cooldown> getCooldown(@NotNull CooldownType cooldownType,
    @NotNull UUID playerUuid) {
    return Optional.empty();
  }

  @Override
  public void startCooldown(@NotNull CooldownType cooldownType, @NotNull UUID playerUuid) {
    Preconditions.checkNotNull(playerUuid);

    LocalDateTime currentDateTime = LocalDateTime.now(clock);
    feedCooldown.put(playerUuid, currentDateTime);
  }
}
