package fr.fallenvaders.minecraft.plugin.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.annotation.Values;
import fr.fallenvaders.minecraft.plugin.controller.MessageController;
import fr.fallenvaders.minecraft.plugin.controller.PlayerController;
import fr.fallenvaders.minecraft.plugin.view.Message;
import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Singleton
@CommandAlias("ping")
@CommandPermission("fallenvaders.essentials.ping")
public class PingCommand extends BaseCommand {

  private final Message message;
  private final MessageController messageController;
  private final PlayerController playerController;
  private final Server server;

  @Inject
  public PingCommand(
      @NotNull Message message,
      @NotNull MessageController messageController,
      @NotNull PlayerController playerController,
      @NotNull Server server) {
    this.message = message;
    this.messageController = messageController;
    this.playerController = playerController;
    this.server = server;
  }

  @Default
  @CommandCompletion("@playernames")
  @Description("Test du ping.")
  public void onPing(
      @NotNull CommandSender commandSender,
      @Nullable @Optional @Values("@playernames") String playerName) {
    if (playerName == null) {
      if (commandSender instanceof Player playerSender) {
        playerController.ping(playerSender, playerSender);
      } else {
        messageController.sendErrorMessage(commandSender, message.impossibleToTargetConsole());
      }
    } else {
      Player targetedPlayer = Objects.requireNonNull(server.getPlayer(playerName));
      playerController.ping(commandSender, targetedPlayer);
    }
  }
}
