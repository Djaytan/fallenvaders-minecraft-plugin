package fr.fallenvaders.minecraft.plugin.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import fr.fallenvaders.minecraft.plugin.controller.api.PlayerController;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Singleton
@CommandAlias("feed")
@CommandPermission("fallenvaders.essentials.feed")
public class FeedCommand extends BaseCommand {

  private final PlayerController playerController;

  @Inject
  public FeedCommand(@NotNull PlayerController playerController) {
    this.playerController = playerController;
  }

  @Default
  @Description("Vous rassasie intégralement (saturation incluse).")
  public void onFeed(@NotNull Player playerSender) {
    playerController.feedPlayer(playerSender);
  }
}
