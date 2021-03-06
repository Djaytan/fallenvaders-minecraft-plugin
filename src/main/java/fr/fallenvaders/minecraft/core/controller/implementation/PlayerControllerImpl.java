package fr.fallenvaders.minecraft.core.controller.implementation;

import com.google.common.base.Preconditions;
import fr.fallenvaders.minecraft.core.controller.api.MessageController;
import fr.fallenvaders.minecraft.core.controller.api.PlayerController;
import fr.fallenvaders.minecraft.core.model.config.FallenVadersConfig;
import fr.fallenvaders.minecraft.core.utils.GameAttribute;
import fr.fallenvaders.minecraft.core.view.EssentialsMessage;
import java.util.Objects;
import java.util.ResourceBundle;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

// TODO: centralized error management
@Singleton
public class PlayerControllerImpl implements PlayerController {

  private final EssentialsMessage essentialsMessages;
  private final FallenVadersConfig fallenVadersConfig;
  private final MessageController messageController;
  private final MiniMessage miniMessage;
  private final ResourceBundle resourceBundle;
  private final Server server;

  @Inject
  public PlayerControllerImpl(
      @NotNull EssentialsMessage essentialsMessage,
      @NotNull FallenVadersConfig fallenVadersConfig,
      @NotNull MessageController messageController,
      @NotNull MiniMessage miniMessage,
      @NotNull ResourceBundle resourceBundle,
      @NotNull Server server) {
    this.essentialsMessages = essentialsMessage;
    this.fallenVadersConfig = fallenVadersConfig;
    this.messageController = messageController;
    this.miniMessage = miniMessage;
    this.resourceBundle = resourceBundle;
    this.server = server;
  }

  @Override
  public void healPlayer(@NotNull Player targetedPlayer) {
    Preconditions.checkNotNull(targetedPlayer);

    AttributeInstance maxHealthAttribute =
        Objects.requireNonNull(targetedPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH));
    targetedPlayer.setHealth(maxHealthAttribute.getValue());

    messageController.sendSuccessMessage(targetedPlayer, essentialsMessages.youHaveBeenHealed());
  }

  @Override
  public void feedPlayer(@NotNull Player targetedPlayer) {
    Preconditions.checkNotNull(targetedPlayer);

    targetedPlayer.setFoodLevel(GameAttribute.MAX_FOOD_LEVEL);
    targetedPlayer.setSaturation(GameAttribute.MAX_SATURATION_LEVEL);

    messageController.sendSuccessMessage(targetedPlayer, essentialsMessages.youHaveBeenFed());
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

  @Override
  public void openCraftingTable(@NotNull Player playerSender) {
    playerSender.openWorkbench(playerSender.getLocation(), true);
    messageController.sendInfoMessage(
        playerSender, essentialsMessages.openCraftingTableInventory());
  }

  @Override
  public void openEnderChest(@NotNull Player playerSender) {
    playerSender.openInventory(playerSender.getEnderChest());
    messageController.sendInfoMessage(playerSender, essentialsMessages.openEnderChestInventory());
  }

  @Override
  public void openDisposal(@NotNull Player playerSender) {
    playerSender.openInventory(
        server.createInventory(
            playerSender,
            27,
            miniMessage.deserialize(
                resourceBundle.getString("fallenvaders.essentials.disposal.inventory.title"))));
    messageController.sendInfoMessage(playerSender, essentialsMessages.openDisposalInventory());
  }

  @Override
  public void hat(@NotNull Player playerSender) {
    ItemStack itemInHand = playerSender.getEquipment().getItemInMainHand();

    if (itemInHand.getType().isAir()) {
      messageController.sendFailureMessage(playerSender, essentialsMessages.noItemInHandHat());
      return;
    }

    ItemStack previousHelmet = playerSender.getEquipment().getHelmet();
    playerSender.getEquipment().setHelmet(itemInHand);
    playerSender.getEquipment().setItemInMainHand(previousHelmet);
    messageController.sendInfoMessage(playerSender, essentialsMessages.changeHat());
  }

  @Override
  public void suicide(@NotNull Player playerSender) {
    playerSender.setHealth(0);
    messageController.sendInfoMessage(playerSender, essentialsMessages.suicide());
  }

  @Override
  public void more(@NotNull Player playerSender) {
    more(playerSender, GameAttribute.MAX_ITEM_STACK_SIZE);
  }

  @Override
  public void more(@NotNull Player playerSender, int amount) {
    ItemStack itemInMainHand = playerSender.getEquipment().getItemInMainHand();

    if (itemInMainHand.getType() == Material.AIR) {
      messageController.sendFailureMessage(playerSender, essentialsMessages.mainHandSlotEmpty());
      return;
    }

    int initialAmount = itemInMainHand.getAmount();
    int newAmount = Math.min(initialAmount + amount, GameAttribute.MAX_ITEM_STACK_SIZE);
    itemInMainHand.setAmount(newAmount);

    int nbMoreItems = newAmount - initialAmount;

    if (nbMoreItems > 0) {
      messageController.sendSuccessMessage(
          playerSender, essentialsMessages.more(itemInMainHand.getType(), nbMoreItems));
    } else {
      messageController.sendFailureMessage(
          playerSender, essentialsMessages.mainHandSlotAlreadyFull());
    }
  }
}
