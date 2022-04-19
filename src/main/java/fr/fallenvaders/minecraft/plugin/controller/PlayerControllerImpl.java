package fr.fallenvaders.minecraft.plugin.controller;

import com.google.common.base.Preconditions;
import fr.fallenvaders.minecraft.plugin.FallenVadersConfig;
import fr.fallenvaders.minecraft.plugin.utils.GameAttribute;
import fr.fallenvaders.minecraft.plugin.view.EssentialsMessage;
import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

// TODO: centralized error management
@Singleton
public class PlayerControllerImpl implements PlayerController {

  private final EssentialsMessage essentialsMessages;
  private final FallenVadersConfig fallenVadersConfig;
  private final MessageController messageController;

  @Inject
  public PlayerControllerImpl(
      @NotNull EssentialsMessage essentialsMessage,
      @NotNull FallenVadersConfig fallenVadersConfig,
      @NotNull MessageController messageController) {
    this.essentialsMessages = essentialsMessage;
    this.fallenVadersConfig = fallenVadersConfig;
    this.messageController = messageController;
  }

  @Override
  public void healPlayer(@NotNull CommandSender sender, @NotNull Player targetedPlayer) {
    Preconditions.checkNotNull(sender);
    Preconditions.checkNotNull(targetedPlayer);

    AttributeInstance maxHealthAttribute =
        Objects.requireNonNull(targetedPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH));
    targetedPlayer.setHealth(maxHealthAttribute.getValue());

    messageController.sendInfoMessage(targetedPlayer, essentialsMessages.youHaveBeenHealed());

    if (!sender.equals(targetedPlayer)) {
      messageController.sendInfoMessage(
          sender, essentialsMessages.playerHasBeenHealed(targetedPlayer.getName()));
    }
  }

  @Override
  public void feedPlayer(@NotNull CommandSender sender, @NotNull Player targetedPlayer) {
    Preconditions.checkNotNull(sender);
    Preconditions.checkNotNull(targetedPlayer);

    targetedPlayer.setFoodLevel(GameAttribute.MAX_FOOD_LEVEL);
    targetedPlayer.setSaturation(GameAttribute.MAX_SATURATION_LEVEL);

    messageController.sendInfoMessage(targetedPlayer, essentialsMessages.youHaveBeenFed());

    if (!sender.equals(targetedPlayer)) {
      messageController.sendInfoMessage(
          sender, essentialsMessages.playerHasBeenFed(targetedPlayer.getName()));
    }
  }

  @Override
  public void teleportToSpawn(@NotNull CommandSender sender, @NotNull Player targetedPlayer) {
    Preconditions.checkNotNull(sender);
    Preconditions.checkNotNull(targetedPlayer);

    targetedPlayer.teleport(fallenVadersConfig.getSpawnLocation());

    messageController.sendInfoMessage(
        targetedPlayer, essentialsMessages.youHaveBeenTeleportedToSpawn());

    if (!sender.equals(targetedPlayer)) {
      messageController.sendInfoMessage(
          sender, essentialsMessages.playerHasBeenTeleportedToSpawn(targetedPlayer.getName()));
    }
  }

  @Override
  public void ping(@NotNull CommandSender sender, @NotNull Player targetedPlayer) {
    Preconditions.checkNotNull(sender);
    Preconditions.checkNotNull(targetedPlayer);

    int pingValue = targetedPlayer.getPing();

    if (sender.equals(targetedPlayer)) {
      messageController.sendInfoMessage(sender, essentialsMessages.pingPong(pingValue));
    } else {
      messageController.sendInfoMessage(
          sender, essentialsMessages.pingPongOther(targetedPlayer.getName(), pingValue));
    }
  }

  @Override
  public void toggleFlyMode(@NotNull CommandSender sender, @NotNull Player targetedPlayer) {
    toggleFlyMode(sender, targetedPlayer, !targetedPlayer.getAllowFlight());
  }

  @Override
  public void toggleFlyMode(
      @NotNull CommandSender sender, @NotNull Player targetedPlayer, boolean isFlyActivated) {
    Preconditions.checkNotNull(sender);
    Preconditions.checkNotNull(targetedPlayer);

    targetedPlayer.setAllowFlight(isFlyActivated);

    messageController.sendInfoMessage(
        targetedPlayer, essentialsMessages.flyToggled(isFlyActivated));

    if (!sender.equals(targetedPlayer)) {
      messageController.sendInfoMessage(
          sender, essentialsMessages.flyToggledOther(isFlyActivated, targetedPlayer.getName()));
    }
  }

  @Override
  public void toggleGodMode(@NotNull CommandSender sender, @NotNull Player targetedPlayer) {
    toggleGodMode(sender, targetedPlayer, !targetedPlayer.isInvulnerable());
  }

  @Override
  public void toggleGodMode(
      @NotNull CommandSender sender, @NotNull Player targetedPlayer, boolean isGodActivated) {
    Preconditions.checkNotNull(sender);
    Preconditions.checkNotNull(targetedPlayer);

    targetedPlayer.setInvulnerable(isGodActivated);

    messageController.sendInfoMessage(
        targetedPlayer, essentialsMessages.godToggled(isGodActivated));

    if (!sender.equals(targetedPlayer)) {
      messageController.sendInfoMessage(
          sender, essentialsMessages.godToggledOther(isGodActivated, targetedPlayer.getName()));
    }
  }

  @Override
  public void openAnvil(@NotNull Player playerSender) {
    playerSender.openAnvil(playerSender.getLocation(), true);
    messageController.sendInfoMessage(playerSender, essentialsMessages.openAnvilInventory());
  }
}
