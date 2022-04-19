package fr.fallenvaders.minecraft.plugin.controller;

import com.google.common.base.Preconditions;
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

@Singleton
public class PlayerControllerImpl implements PlayerController {

  private final EssentialsMessage essentialsMessages;
  private final MessageController messageController;

  @Inject
  public PlayerControllerImpl(
      @NotNull EssentialsMessage essentialsMessage, @NotNull MessageController messageController) {
    this.essentialsMessages = essentialsMessage;
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
}
