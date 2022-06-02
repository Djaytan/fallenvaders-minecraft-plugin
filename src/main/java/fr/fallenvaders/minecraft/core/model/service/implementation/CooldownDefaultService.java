package fr.fallenvaders.minecraft.core.model.service.implementation;

import com.google.common.base.Preconditions;
import fr.fallenvaders.minecraft.core.model.dao.api.CooldownDao;
import fr.fallenvaders.minecraft.core.model.entity.Cooldown;
import fr.fallenvaders.minecraft.core.model.entity.factory.CooldownFactory;
import fr.fallenvaders.minecraft.core.model.service.api.CooldownService;
import fr.fallenvaders.minecraft.core.model.service.api.parameter.CooldownType;
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

  private final CooldownDao cooldownDao;
  private final CooldownFactory cooldownFactory;

  /**
   * Constructor.
   *
   * @param cooldownDao The Cooldown DAO.
   * @param cooldownFactory The Cooldown factory.
   */
  @Inject
  public CooldownDefaultService(
      @NotNull CooldownDao cooldownDao, @NotNull CooldownFactory cooldownFactory) {
    this.cooldownDao = cooldownDao;
    this.cooldownFactory = cooldownFactory;
  }

  @Override
  public @NotNull Optional<Cooldown> getCooldown(
      @NotNull CooldownType cooldownType, @NotNull UUID playerUuid) {
    Preconditions.checkNotNull(playerUuid);
    return cooldownDao.getCooldown(cooldownType, playerUuid);
  }

  @Override
  public void startCooldown(@NotNull CooldownType cooldownType, @NotNull UUID playerUuid) {
    Preconditions.checkNotNull(playerUuid);
    Cooldown cooldown =
        cooldownFactory.create(cooldownType, playerUuid, cooldownType.getCooldownDuration());
    cooldownDao.registerCooldown(cooldown);
  }
}
