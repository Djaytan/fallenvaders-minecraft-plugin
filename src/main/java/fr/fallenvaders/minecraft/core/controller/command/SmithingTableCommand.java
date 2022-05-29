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
@CommandAlias("smithingtable")
@CommandPermission("fallenvaders.essentials.smithingtable")
public class SmithingTableCommand extends BaseCommand {

  private final PlayerController playerController;

  @Inject
  public SmithingTableCommand(@NotNull PlayerController playerController) {
    this.playerController = playerController;
  }

  @Default
  @Description("Accès à la table de forgeron portable.")
  public void onOpenSmithingTable(@NotNull Player playerSender) {
    playerController.openSmithingTable(playerSender);
  }
}
