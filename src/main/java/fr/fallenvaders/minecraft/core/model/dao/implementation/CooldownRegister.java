package fr.fallenvaders.minecraft.core.model.dao.implementation;

import com.google.common.base.Preconditions;
import fr.fallenvaders.minecraft.core.model.entity.Cooldown;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Singleton;
import org.jetbrains.annotations.NotNull;

@Singleton
public class CooldownRegister {

  private final Map<UUID, Cooldown> cooldownMap = new HashMap<>();

  public @NotNull Optional<Cooldown> getCooldown(@NotNull UUID playerUuid) {
    Preconditions.checkNotNull(playerUuid);

    if (!cooldownMap.containsKey(playerUuid)) {
      return Optional.empty();
    }
    return Optional.of(cooldownMap.get(playerUuid));
  }

  public void putCooldown(@NotNull Cooldown cooldown) {
    Preconditions.checkNotNull(cooldown);
    cooldownMap.put(cooldown.getPlayerUuid(), cooldown);
  }
}
