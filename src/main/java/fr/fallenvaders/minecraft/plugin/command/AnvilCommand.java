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
@CommandAlias("anvil")
@CommandPermission("fallenvaders.essentials.anvil")
public class AnvilCommand extends BaseCommand {

  private final PlayerController playerController;

  @Inject
  public AnvilCommand(@NotNull PlayerController playerController) {
    this.playerController = playerController;
  }

  @Default
  @Description("Accès à une enclume portable.")
  public void onOpenAnvil(@NotNull Player playerSender) {
    playerController.openAnvil(playerSender);
  }
}
