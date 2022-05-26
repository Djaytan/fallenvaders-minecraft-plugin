package fr.fallenvaders.minecraft.plugin.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Name;
import fr.fallenvaders.minecraft.plugin.controller.PlayerController;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Singleton
@CommandAlias("more")
@CommandPermission("fallenvaders.essentials.more")
public class MoreCommand extends BaseCommand {

  private final PlayerController playerController;

  @Inject
  public MoreCommand(@NotNull PlayerController playerController) {
    this.playerController = playerController;
  }

  @Default
  @Description("Rempli le slot d'inventaire de la main jusqu'à la taille maximale (64).")
  public void onMore(@NotNull Player playerSender) {
    playerController.more(playerSender);
  }

  @Default
  @Description(
      "Rempli le slot d'inventaire de la main par le nombre spécifié ou jusqu'à la taille maximale"
          + " (64).")
  public void onMore(@NotNull Player playerSender, @Name("quantité") int amount) {
    playerController.more(playerSender, amount);
  }
}
