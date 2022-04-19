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
import fr.fallenvaders.minecraft.plugin.controller.MessageController;
import fr.fallenvaders.minecraft.plugin.controller.PlayerController;
import fr.fallenvaders.minecraft.plugin.view.EssentialsMessage;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Singleton
@CommandAlias("heal")
@CommandPermission("fallenvaders.essentials.heal")
public class HealCommand extends BaseCommand {

  private final EssentialsMessage essentialsMessage;
  private final MessageController messageController;
  private final PlayerController playerController;

  @Inject
  public HealCommand(
      @NotNull EssentialsMessage essentialsMessage,
      @NotNull MessageController messageController,
      @NotNull PlayerController playerController) {
    this.essentialsMessage = essentialsMessage;
    this.messageController = messageController;
    this.playerController = playerController;
  }

  @Default
  @Description("Régénère instantanément votre santé au maximum.")
  public void onHeal(@NotNull Player player) {
    playerController.healPlayer(player, player);
  }

  @Subcommand("player")
  @CommandCompletion("@players")
  @Description("Régénère instantanément la santé du joueur ciblé au maximum.")
  public void onHealOther(
      @NotNull CommandSender commandSender, @NotNull @Name("joueur") Player targetedPlayer) {
    playerController.healPlayer(commandSender, targetedPlayer);
  }

  @Subcommand("help")
  @Description("Affichage l'aide pour la commande.")
  public void onHelp(CommandSender sender, CommandHelp help) {
    help.showHelp();
  }
}
