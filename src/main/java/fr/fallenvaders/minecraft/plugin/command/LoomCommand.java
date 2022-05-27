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
@CommandAlias("loom")
@CommandPermission("fallenvaders.essentials.loom")
public class LoomCommand extends BaseCommand {

  private final PlayerController playerController;

  @Inject
  public LoomCommand(@NotNull PlayerController playerController) {
    this.playerController = playerController;
  }

  @Default
  @Description("Accès au métier à tisser portable.")
  public void onOpenLoom(@NotNull Player playerSender) {
    playerController.openLoom(playerSender);
  }
}
