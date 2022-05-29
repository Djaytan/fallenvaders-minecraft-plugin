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
@CommandAlias("heal")
@CommandPermission("fallenvaders.essentials.heal")
public class HealCommand extends BaseCommand {

  private final PlayerController playerController;

  @Inject
  public HealCommand(@NotNull PlayerController playerController) {
    this.playerController = playerController;
  }

  @Default
  @Description("Régénère instantanément votre santé au maximum.")
  public void onHeal(@NotNull Player playerSender) {
    playerController.healPlayer(playerSender);
  }
}
