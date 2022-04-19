package fr.fallenvaders.minecraft.plugin.view;

import java.util.ResourceBundle;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
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

  public @NotNull Component youHaveBeenHealed() {
    return miniMessage.deserialize(
        resourceBundle.getString("fallenvaders.essentials.message.you_have_been_healed"));
  }

  public @NotNull Component youHaveBeenFed() {
    return miniMessage.deserialize(
        resourceBundle.getString("fallenvaders.essentials.message.you_have_been_fed"));
  }

  public @NotNull Component playerHasBeenHealed(@NotNull String healedPlayerName) {
    return miniMessage.deserialize(
        resourceBundle.getString("fallenvaders.essentials.message.player_healed"),
        TagResolver.resolver(Placeholder.unparsed("fv_player", healedPlayerName)));
  }

  public @NotNull Component playerHasBeenFed(@NotNull String fedPlayerName) {
    return miniMessage.deserialize(
        resourceBundle.getString("fallenvaders.essentials.message.player_fed"),
        TagResolver.resolver(Placeholder.unparsed("fv_player", fedPlayerName)));
  }
}
