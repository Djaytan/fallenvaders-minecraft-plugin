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
@CommandAlias("disposal|trash")
@CommandPermission("fallenvaders.essentials.disposal")
public class DisposalCommand extends BaseCommand {

  private final PlayerController playerController;

  @Inject
  public DisposalCommand(@NotNull PlayerController playerController) {
    this.playerController = playerController;
  }

  @Default
  @Description("Ouvre un inventaire pour y jeter vos items.")
  public void onOpenDisposal(@NotNull Player playerSender) {
    playerController.openDisposal(playerSender);
  }
}
