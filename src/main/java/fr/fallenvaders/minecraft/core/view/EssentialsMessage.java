package fr.fallenvaders.minecraft.core.view;

import java.util.ResourceBundle;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

@Singleton
public class EssentialsMessage {

  private final MiniMessage miniMessage;
  private final ResourceBundle resourceBundle;

  @Inject
  public EssentialsMessage(
      @NotNull MiniMessage miniMessage, @NotNull ResourceBundle resourceBundle) {
    this.miniMessage = miniMessage;
    this.resourceBundle = resourceBundle;
  }

  public @NotNull Component openAnvilInventory() {
    return miniMessage.deserialize(
        resourceBundle.getString("fallenvaders.essentials.command.anvil"));
  }

  public @NotNull Component openCartographyTableInventory() {
    return miniMessage.deserialize(
        resourceBundle.getString("fallenvaders.essentials.command.cartography_table"));
  }

  public @NotNull Component openCraftingTableInventory() {
    return miniMessage.deserialize(
        resourceBundle.getString("fallenvaders.essentials.command.crafting_table"));
  }

  public @NotNull Component openDisposalInventory() {
    return miniMessage.deserialize(
        resourceBundle.getString("fallenvaders.essentials.command.disposal"));
  }

  public @NotNull Component openEnderChestInventory() {
    return miniMessage.deserialize(
        resourceBundle.getString("fallenvaders.essentials.command.ender_chest"));
  }

  public @NotNull Component youHaveBeenFed() {
    return miniMessage.deserialize(
        resourceBundle.getString("fallenvaders.essentials.command.feed.you_have_been_fed"));
  }

  public @NotNull Component flyToggled(boolean isFlyActivated) {
    String stateKey = "fallenvaders.common.state.activated";

    if (!isFlyActivated) {
      stateKey = "fallenvaders.common.state.deactivated";
    }

    return miniMessage.deserialize(
        resourceBundle.getString("fallenvaders.essentials.command.fly.toggled"),
        TagResolver.resolver(
            Placeholder.component(
                "fv_state", miniMessage.deserialize(resourceBundle.getString(stateKey)))));
  }

  public @NotNull Component flyToggledOther(
      boolean isFlyActivated, @NotNull String targetedPlayerName) {
    String stateKey = "fallenvaders.common.state.activated";

    if (!isFlyActivated) {
      stateKey = "fallenvaders.common.state.deactivated";
    }

    return miniMessage.deserialize(
        resourceBundle.getString("fallenvaders.essentials.command.fly.toggled.other"),
        TagResolver.resolver(
            Placeholder.component(
                "fv_state", miniMessage.deserialize(resourceBundle.getString(stateKey))),
            Placeholder.unparsed("fv_player", targetedPlayerName)));
  }

  public @NotNull Component godToggled(boolean isGodActivated) {
    String stateKey = "fallenvaders.common.state.activated";

    if (!isGodActivated) {
      stateKey = "fallenvaders.common.state.deactivated";
    }

    return miniMessage.deserialize(
        resourceBundle.getString("fallenvaders.essentials.command.god.toggled"),
        TagResolver.resolver(
            Placeholder.component(
                "fv_state", miniMessage.deserialize(resourceBundle.getString(stateKey)))));
  }

  public @NotNull Component godToggledOther(
      boolean isGodActivated, @NotNull String targetedPlayerName) {
    String stateKey = "fallenvaders.common.state.activated";

    if (!isGodActivated) {
      stateKey = "fallenvaders.common.state.deactivated";
    }

    return miniMessage.deserialize(
        resourceBundle.getString("fallenvaders.essentials.command.god.toggled.other"),
        TagResolver.resolver(
            Placeholder.component(
                "fv_state", miniMessage.deserialize(resourceBundle.getString(stateKey))),
            Placeholder.unparsed("fv_player", targetedPlayerName)));
  }

  public @NotNull Component openGrindstoneInventory() {
    return miniMessage.deserialize(
        resourceBundle.getString("fallenvaders.essentials.command.grindstone"));
  }

  public @NotNull Component youHaveBeenHealed() {
    return miniMessage.deserialize(
        resourceBundle.getString("fallenvaders.essentials.command.heal.you_have_been_healed"));
  }

  public @NotNull Component openLoomInventory() {
    return miniMessage.deserialize(
        resourceBundle.getString("fallenvaders.essentials.command.loom"));
  }

  public @NotNull Component changeHat() {
    return miniMessage.deserialize(
        resourceBundle.getString("fallenvaders.essentials.command.hat.success"));
  }

  public @NotNull Component noItemInHandHat() {
    return miniMessage.deserialize(
        resourceBundle.getString("fallenvaders.essentials.command.hat.no_item_in_hand"));
  }

  public @NotNull Component pingPong(int pingValue) {
    return miniMessage.deserialize(
        resourceBundle.getString("fallenvaders.essentials.command.ping.pong"),
        TagResolver.resolver(Placeholder.unparsed("fv_ping_value", Integer.toString(pingValue))));
  }

  public @NotNull Component pingPongOther(@NotNull String targetedPlayerName, int pingValue) {
    return miniMessage.deserialize(
        resourceBundle.getString("fallenvaders.essentials.command.ping.pong.other"),
        TagResolver.resolver(
            Placeholder.unparsed("fv_player", targetedPlayerName),
            Placeholder.unparsed("fv_ping_value", Integer.toString(pingValue))));
  }

  public @NotNull Component openSmithingTableInventory() {
    return miniMessage.deserialize(
        resourceBundle.getString("fallenvaders.essentials.command.smithing_table"));
  }

  public @NotNull Component youHaveBeenTeleportedToSpawn() {
    return miniMessage.deserialize(
        resourceBundle.getString(
            "fallenvaders.essentials.command.spawn.you_have_been_teleported_to_spawn"));
  }

  public @NotNull Component playerHasBeenTeleportedToSpawn(@NotNull String teleportedPlayerName) {
    return miniMessage.deserialize(
        resourceBundle.getString(
            "fallenvaders.essentials.command.spawn.player_teleported_to_spawn"),
        TagResolver.resolver(Placeholder.unparsed("fv_player", teleportedPlayerName)));
  }

  public @NotNull Component openStonecutterInventory() {
    return miniMessage.deserialize(
        resourceBundle.getString("fallenvaders.essentials.command.stonecutter"));
  }

  public @NotNull Component suicide() {
    return miniMessage.deserialize(
        resourceBundle.getString("fallenvaders.essentials.command.suicide"));
  }

  public @NotNull Component more(@NotNull Material material, int amount) {
    return miniMessage.deserialize(
        resourceBundle.getString("fallenvaders.essentials.command.more"),
        TagResolver.resolver(
            Placeholder.unparsed("fv_amount", Integer.toString(amount)),
            Placeholder.component("fv_item_name", Component.translatable(material))));
  }

  public @NotNull Component mainHandSlotAlreadyFull() {
    return miniMessage.deserialize(
        resourceBundle.getString(
            "fallenvaders.essentials.command.more.main_hand_slot_already_full"));
  }

  public @NotNull Component mainHandSlotEmpty() {
    return miniMessage.deserialize(
        resourceBundle.getString("fallenvaders.essentials.command.more.main_hand_slot_empty"));
  }
}
