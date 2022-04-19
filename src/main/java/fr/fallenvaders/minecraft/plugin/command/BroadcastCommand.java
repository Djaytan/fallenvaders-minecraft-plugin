package fr.fallenvaders.minecraft.plugin.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Name;
import fr.fallenvaders.minecraft.plugin.controller.MessageController;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@Singleton
@CommandAlias("broadcast|bc")
@CommandPermission("fallenvaders.essentials.broadcast")
public class BroadcastCommand extends BaseCommand {

  private final MessageController messageController;

  @Inject
  public BroadcastCommand(@NotNull MessageController messageController) {
    this.messageController = messageController;
  }

  @Default
  @Description("Envoie une annonce sur le serveur.")
  public void onBroadcast(@NotNull CommandSender commandSender, @NotNull @Name("message") String message) {
    messageController.broadcastMessage(Component.text(message));
  }
}
