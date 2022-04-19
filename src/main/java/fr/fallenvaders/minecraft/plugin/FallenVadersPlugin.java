package fr.fallenvaders.minecraft.plugin;

import fr.fallenvaders.minecraft.plugin.controller.MessageController;
import fr.fallenvaders.minecraft.plugin.guice.GuiceInjector;
import fr.fallenvaders.minecraft.plugin.view.Message;
import javax.inject.Inject;
import net.kyori.adventure.text.Component;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class FallenVadersPlugin extends JavaPlugin {

  @Inject private CommandRegister commandRegister;
  @Inject private ConsoleCommandSender consoleCommandSender;
  @Inject private ListenerRegister listenerRegister;
  @Inject private Message message;
  @Inject private MessageController messageController;

  @Override
  public void onEnable() {
    GuiceInjector.inject(this);

    try {
      messageController.sendRawMessage(consoleCommandSender, message.startupBanner());
      messageController.sendRawMessage(
          consoleCommandSender, message.startupBannerVersionLine(getDescription()));
      messageController.sendRawMessage(
          consoleCommandSender, message.startupBannerProgressionLine("Guice injection done."));
      // TODO: replace "done"s by a green mark

      // Events listeners registration
      listenerRegister.registerListeners();

      messageController.sendRawMessage(
          consoleCommandSender,
          message.startupBannerProgressionLine("Events listeners registration done."));

      // Commands registration
      commandRegister.registerCommands();
      commandRegister.registerCommandCompletions();

      messageController.sendRawMessage(
          consoleCommandSender,
          message.startupBannerProgressionLine("Commands registration done."));

      // Plugin enabled successfully
      messageController.sendRawMessage(
          consoleCommandSender, message.startupBannerEnablingSuccessLine());
      messageController.sendRawMessage(consoleCommandSender, Component.empty());
    } catch (RuntimeException e) {
      messageController.sendRawMessage(
          consoleCommandSender, message.startupBannerEnablingFailureLine());
      messageController.sendRawMessage(consoleCommandSender, Component.empty());
      getSLF4JLogger().error("Something went wrong and prevent plugin activation.", e);
    }
  }
}
