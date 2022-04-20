package fr.fallenvaders.minecraft.plugin.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import fr.fallenvaders.minecraft.plugin.controller.PlayerController;
import javax.inject.Singleton;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Singleton
@CommandAlias("stonecutter")
@CommandPermission("fallenvaders.essentials.stonecutter")
public class StonecutterCommand extends BaseCommand {

  private final PlayerController playerController;

  public StonecutterCommand(@NotNull PlayerController playerController) {
    this.playerController = playerController;
  }

  @Default
  @Description("Accès au tailleur de pierre portable.")
  public void onOpenStonecutter(@NotNull Player playerSender) {
    playerController.openStonecutter(playerSender);
  }
}
