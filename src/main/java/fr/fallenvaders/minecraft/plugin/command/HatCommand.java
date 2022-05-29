package fr.fallenvaders.minecraft.plugin.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import fr.fallenvaders.minecraft.plugin.controller.PlayerController;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Singleton
@CommandAlias("hat")
@CommandPermission("fallenvaders.essentials.hat")
public class HatCommand extends BaseCommand {

  private final PlayerController playerController;

  @Inject
  public HatCommand(@NotNull PlayerController playerController) {
    this.playerController = playerController;
  }

  @Default
  @Description("Place l'objet de sa main sur sa tête.")
  public void onHat(@NotNull Player playerSender) {
    this.playerController.setHat(playerSender);
  }
}
