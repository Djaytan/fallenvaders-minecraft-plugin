package fr.fallenvaders.minecraft.plugin.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.HelpCommand;
import co.aikar.commands.annotation.Name;
import co.aikar.commands.annotation.Subcommand;
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
@CommandAlias("feed")
@CommandPermission("fallenvaders.essentials.feed")
public class FeedCommand extends BaseCommand {

  private final PlayerController playerController;
  private final Server server;

  @Inject
  public FeedCommand(@NotNull PlayerController playerController, @NotNull Server server) {
    this.playerController = playerController;
    this.server = server;
  }

  @Default
  @Description("Vous rassasie intégralement (saturation incluse).")
  public void onFeed(@NotNull Player player) {
    playerController.feedPlayer(player, player);
  }

  @Subcommand("player")
  @CommandCompletion("@playernames")
  @Description("Rassasie intégralement le joueur ciblé (saturation incluse).")
  @CommandPermission("fallenvaders.essentials.heal.other")
  public void onFeedOther(
      @NotNull CommandSender commandSender,
      @NotNull @Name("joueur") @Values("@playernames") String targetedPlayerName) {
    Player targetedPlayer = Objects.requireNonNull(server.getPlayer(targetedPlayerName));
    playerController.feedPlayer(commandSender, targetedPlayer);
  }

  @HelpCommand
  @Description("Affichage de l'aide pour la commande.")
  public void onHelp(CommandSender commandSender, CommandHelp commandHelp) {
    commandHelp.showHelp();
  }
}
