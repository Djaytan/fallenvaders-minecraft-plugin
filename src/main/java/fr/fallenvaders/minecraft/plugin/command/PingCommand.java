package fr.fallenvaders.minecraft.plugin.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Name;
import co.aikar.commands.annotation.Values;
import fr.fallenvaders.minecraft.plugin.controller.PlayerController;
import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Singleton
@CommandAlias("ping")
@CommandPermission("fallenvaders.essentials.ping")
public class PingCommand extends BaseCommand {

  private final PlayerController playerController;
  private final Server server;

  @Inject
  public PingCommand(@NotNull PlayerController playerController, @NotNull Server server) {
    this.playerController = playerController;
    this.server = server;
  }

  @Default
  @Description("Test du ping.")
  public void onPing(@NotNull Player playerSender) {
    playerController.ping(playerSender, playerSender);
  }

  @Default
  @CommandCompletion("@playernames")
  @CommandPermission("fallenvaders.essentials.ping.other")
  @Description("Test du ping d'un joueur.")
  public void onPingOther(
      @NotNull CommandSender commandSender,
      @NotNull @Name("joueur") @Values("@playernames") String targetedPlayerName) {
    Player targetedPlayer = Objects.requireNonNull(server.getPlayer(targetedPlayerName));
    playerController.ping(commandSender, targetedPlayer);
  }
}
