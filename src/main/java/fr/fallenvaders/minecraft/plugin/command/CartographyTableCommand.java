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
@CommandAlias("cartographytable")
@CommandPermission("fallenvaders.essentials.cartographytable")
public class CartographyTableCommand extends BaseCommand {

  private final PlayerController playerController;

  @Inject
  public CartographyTableCommand(@NotNull PlayerController playerController) {
    this.playerController = playerController;
  }

  @Default
  @Description("Accès à une table cartographique portable.")
  public void onOpenCartographyTable(@NotNull Player playerSender) {
    playerController.openCartographyTable(playerSender);
  }
}
