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
@CommandAlias("heal")
@CommandPermission("fallenvaders.essentials.heal")
public class HealCommand extends BaseCommand {

  private final PlayerController playerController;
  private final Server server;

  @Inject
  public HealCommand(@NotNull PlayerController playerController, @NotNull Server server) {
    this.playerController = playerController;
    this.server = server;
  }

  @Default
  @Description("Régénère instantanément votre santé au maximum.")
  public void onHeal(@NotNull Player player) {
    playerController.healPlayer(player, player);
  }

  @Subcommand("player")
  @CommandCompletion("@playernames")
  @Description("Régénère instantanément la santé du joueur ciblé au maximum.")
  @CommandPermission("fallenvaders.essentials.heal.other")
  public void onHealOther(
      @NotNull CommandSender commandSender,
      @NotNull @Name("joueur") @Values("@playernames") String targetedPlayerName) {
    Player targetedPlayer = Objects.requireNonNull(server.getPlayer(targetedPlayerName));
    playerController.healPlayer(commandSender, targetedPlayer);
  }

  @HelpCommand
  @Description("Affichage de l'aide pour la commande.")
  public void onHelp(CommandSender sender, CommandHelp help) {
    help.showHelp();
  }
}
