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
@CommandAlias("craftingtable|craft")
@CommandPermission("fallenvaders.essentials.craftingtable")
public class CraftingTableCommand extends BaseCommand {

  private final PlayerController playerController;

  @Inject
  public CraftingTableCommand(@NotNull PlayerController playerController) {
    this.playerController = playerController;
  }

  @Default
  @Description("Accès à une table de craft mobile.")
  public void onOpenCraftingTable(@NotNull Player playerSender) {
    playerController.openCraftingTable(playerSender);
  }
}
