package fr.fallenvaders.minecraft.core.model.dao.api;

import fr.fallenvaders.minecraft.core.model.entity.Cooldown;
import fr.fallenvaders.minecraft.core.model.service.api.parameter.CooldownType;
import java.util.Optional;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

/**
 * An interface that represents the {@link Cooldown} DAO.
 *
 * @author Djaytan
 * @since 0.3.0
 */
public interface CooldownDao {

  @NotNull
  Optional<Cooldown> getCooldown(@NotNull CooldownType cooldownType, @NotNull UUID playerUuid);

  void registerCooldown(@NotNull Cooldown cooldown);
}
