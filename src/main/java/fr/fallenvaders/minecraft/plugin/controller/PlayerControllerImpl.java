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
  public void healPlayer(@NotNull CommandSender commandSender, @NotNull Player targetedPlayer) {
    Preconditions.checkNotNull(commandSender);
    Preconditions.checkNotNull(targetedPlayer);

    AttributeInstance maxHealthAttribute =
        Objects.requireNonNull(targetedPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH));
    targetedPlayer.setHealth(maxHealthAttribute.getValue());

    messageController.sendInfoMessage(targetedPlayer, essentialsMessages.youHaveBeenHealed());

    if (!commandSender.equals(targetedPlayer)) {
      messageController.sendInfoMessage(
          commandSender, essentialsMessages.playerHasBeenHealed(targetedPlayer.getName()));
    }
  }

  @Override
  public void feedPlayer(@NotNull CommandSender commandSender, @NotNull Player targetedPlayer) {
    Preconditions.checkNotNull(commandSender);
    Preconditions.checkNotNull(targetedPlayer);

    targetedPlayer.setFoodLevel(GameAttribute.MAX_FOOD_LEVEL);
    targetedPlayer.setSaturation(GameAttribute.MAX_SATURATION_LEVEL);

    messageController.sendInfoMessage(targetedPlayer, essentialsMessages.youHaveBeenFed());

    if (!commandSender.equals(targetedPlayer)) {
      messageController.sendInfoMessage(
          commandSender, essentialsMessages.playerHasBeenFed(targetedPlayer.getName()));
    }
  }

  @Override
  public void teleportToSpawn(
      @NotNull CommandSender commandSender, @NotNull Player targetedPlayer) {
    Preconditions.checkNotNull(commandSender);
    Preconditions.checkNotNull(targetedPlayer);

    targetedPlayer.teleport(fallenVadersConfig.getSpawnLocation());

    messageController.sendInfoMessage(
        targetedPlayer, essentialsMessages.youHaveBeenTeleportedToSpawn());

    if (!commandSender.equals(targetedPlayer)) {
      messageController.sendInfoMessage(
          commandSender,
          essentialsMessages.playerHasBeenTeleportedToSpawn(targetedPlayer.getName()));
    }
  }

  @Override
  public void ping(@NotNull CommandSender commandSender, @NotNull Player targetedPlayer) {
    Preconditions.checkNotNull(commandSender);
    Preconditions.checkNotNull(targetedPlayer);

    int pingValue = targetedPlayer.getPing();

    if (commandSender.equals(targetedPlayer)) {
      messageController.sendInfoMessage(commandSender, essentialsMessages.pingPong(pingValue));
    } else {
      messageController.sendInfoMessage(
          commandSender, essentialsMessages.pingPongOther(targetedPlayer.getName(), pingValue));
    }
  }

  @Override
  public void toggleFlyMode(@NotNull CommandSender commandSender, @NotNull Player targetedPlayer) {
    toggleFlyMode(commandSender, targetedPlayer, !targetedPlayer.getAllowFlight());
  }

  @Override
  public void toggleFlyMode(
      @NotNull CommandSender commandSender,
      @NotNull Player targetedPlayer,
      boolean isFlyActivated) {
    Preconditions.checkNotNull(commandSender);
    Preconditions.checkNotNull(targetedPlayer);

    targetedPlayer.setAllowFlight(isFlyActivated);

    messageController.sendInfoMessage(
        targetedPlayer, essentialsMessages.flyToggled(isFlyActivated));

    if (!commandSender.equals(targetedPlayer)) {
      messageController.sendInfoMessage(
          commandSender,
          essentialsMessages.flyToggledOther(isFlyActivated, targetedPlayer.getName()));
    }
  }

  @Override
  public void toggleGodMode(@NotNull CommandSender commandSender, @NotNull Player targetedPlayer) {
    toggleGodMode(commandSender, targetedPlayer, !targetedPlayer.isInvulnerable());
  }

  @Override
  public void toggleGodMode(
      @NotNull CommandSender commandSender,
      @NotNull Player targetedPlayer,
      boolean isGodActivated) {
    Preconditions.checkNotNull(commandSender);
    Preconditions.checkNotNull(targetedPlayer);

    targetedPlayer.setInvulnerable(isGodActivated);

    messageController.sendInfoMessage(
        targetedPlayer, essentialsMessages.godToggled(isGodActivated));

    if (!commandSender.equals(targetedPlayer)) {
      messageController.sendInfoMessage(
          commandSender,
          essentialsMessages.godToggledOther(isGodActivated, targetedPlayer.getName()));
    }
  }

  @Override
  public void openAnvil(@NotNull Player playerSender) {
    playerSender.openAnvil(playerSender.getLocation(), true);
    messageController.sendInfoMessage(playerSender, essentialsMessages.openAnvilInventory());
  }

  @Override
  public void openCartographyTable(@NotNull Player playerSender) {
    playerSender.openCartographyTable(playerSender.getLocation(), true);
    messageController.sendInfoMessage(
        playerSender, essentialsMessages.openCartographyTableInventory());
  }

  @Override
  public void openGrindstone(@NotNull Player playerSender) {
    playerSender.openGrindstone(playerSender.getLocation(), true);
    messageController.sendInfoMessage(playerSender, essentialsMessages.openGrindstoneInventory());
  }

  @Override
  public void openLoom(@NotNull Player playerSender) {
    playerSender.openLoom(playerSender.getLocation(), true);
    messageController.sendInfoMessage(playerSender, essentialsMessages.openLoomInventory());
  }

  @Override
  public void openStonecutter(@NotNull Player playerSender) {
    playerSender.openStonecutter(playerSender.getLocation(), true);
    messageController.sendInfoMessage(playerSender, essentialsMessages.openStonecutterInventory());
  }

  @Override
  public void openSmithingTable(@NotNull Player playerSender) {
    playerSender.openSmithingTable(playerSender.getLocation(), true);
    messageController.sendInfoMessage(
        playerSender, essentialsMessages.openSmithingTableInventory());
  }
}
