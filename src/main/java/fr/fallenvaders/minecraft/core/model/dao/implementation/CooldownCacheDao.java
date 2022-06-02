package fr.fallenvaders.minecraft.core.model.dao.implementation;

import com.google.common.base.Preconditions;
import fr.fallenvaders.minecraft.core.model.cache.CooldownRegister;
import fr.fallenvaders.minecraft.core.model.dao.api.CooldownDao;
import fr.fallenvaders.minecraft.core.model.entity.Cooldown;
import fr.fallenvaders.minecraft.core.model.service.api.parameter.CooldownType;
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

  private final CooldownRegister cooldownRegister;

  /**
   * Constructor.
   *
   * @param cooldownRegister The cooldown register.
   */
  @Inject
  public CooldownCacheDao(@NotNull CooldownRegister cooldownRegister) {
    this.cooldownRegister = cooldownRegister;
  }

  @Override
  public @NotNull Optional<Cooldown> getCooldown(
      @NotNull CooldownType cooldownType, @NotNull UUID playerUuid) {
    return cooldownRegister.getCooldowns(playerUuid).stream()
        .filter(cooldown -> cooldown.getCooldownType() == cooldownType)
        .findFirst();
  }

  @Override
  public void registerCooldown(@NotNull Cooldown cooldown) {
    Preconditions.checkNotNull(cooldown);
    cooldownRegister.registerCooldown(cooldown);
  }
}
