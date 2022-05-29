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
@CommandAlias("enderchest|ec")
@CommandPermission("fallenvaders.essentials.enderchest")
public class EnderChestCommand extends BaseCommand {

  private final PlayerController playerController;

  @Inject
  public EnderChestCommand(@NotNull PlayerController playerController) {
    this.playerController = playerController;
  }

  @Default
  @Description("Accès à un coffre de l'End portable.")
  public void onOpenEnderChest(@NotNull Player playerSender) {
    playerController.openEnderChest(playerSender);
  }
}
