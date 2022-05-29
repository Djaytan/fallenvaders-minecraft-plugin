package fr.fallenvaders.minecraft.core.controller.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import fr.fallenvaders.minecraft.core.controller.api.PlayerController;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Singleton
@CommandAlias("suicide")
@CommandPermission("fallenvaders.essentials.suicide")
public class SuicideCommand extends BaseCommand {

  private final PlayerController playerController;

  @Inject
  public SuicideCommand(@NotNull PlayerController playerController) {
    this.playerController = playerController;
  }

  @Default
  @Description("C'est votre choix.")
  public void onSuicide(@NotNull Player playerSender) {
    playerController.suicide(playerSender);
  }
}
