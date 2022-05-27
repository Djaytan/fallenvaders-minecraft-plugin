package fr.fallenvaders.minecraft.plugin.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Name;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.annotation.Values;
import fr.fallenvaders.minecraft.plugin.controller.api.PlayerController;
import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Singleton
@CommandAlias("fly")
@CommandPermission("fallenvaders.essentials.fly")
public class FlyCommand extends BaseCommand {

  private final PlayerController playerController;
  private final Server server;

  @Inject
  public FlyCommand(@NotNull PlayerController playerController, @NotNull Server server) {
    this.playerController = playerController;
    this.server = server;
  }

  @Default
  @Description("Change son mode fly.")
  public void onToggleFly(@NotNull Player playerSender) {
    playerController.toggleFlyMode(playerSender, playerSender);
  }

  @Default
  @CommandCompletion("@playernames")
  @CommandPermission("fallenvaders.essentials.fly.other")
  @Description("Change le mode fly d'un joueur.")
  public void onToggleFly(
      @NotNull CommandSender commandSender,
      @NotNull @Name("joueur") @Values("@playernames") String targetedPlayerName,
      @Nullable @Optional @Name("on|off") @Values("on|off") String isFlyActivatedStr) {
    Player targetedPlayer = Objects.requireNonNull(server.getPlayer(targetedPlayerName));

    if (isFlyActivatedStr == null) {
      playerController.toggleGodMode(commandSender, targetedPlayer);
    } else {
      boolean isFlyActivated = "on".equals(isFlyActivatedStr);
      playerController.toggleGodMode(commandSender, targetedPlayer, isFlyActivated);
    }
  }
}
