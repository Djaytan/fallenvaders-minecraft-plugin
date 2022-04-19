package fr.fallenvaders.minecraft.plugin;

import fr.fallenvaders.minecraft.plugin.controller.MessageController;
import fr.fallenvaders.minecraft.plugin.guice.GuiceInjector;
import fr.fallenvaders.minecraft.plugin.view.Message;
import javax.inject.Inject;
import net.kyori.adventure.text.Component;
import org.bukkit.plugin.java.JavaPlugin;

public class FallenVadersPlugin extends JavaPlugin {

  @Inject private CommandRegister commandRegister;
  @Inject private ListenerRegister listenerRegister;
  @Inject private Message message;
  @Inject private MessageController messageController;

  @Override
  public void onEnable() {
    GuiceInjector.inject(this);

    try {
      messageController.sendRawMessage(getServer().getConsoleSender(), message.startupBanner());
      messageController.sendRawMessage(
          getServer().getConsoleSender(), message.startupBannerVersionLine(getDescription()));
      messageController.sendRawMessage(
          getServer().getConsoleSender(),
          message.startupBannerProgressionLine("Guice injection done."));
      // TODO: replace "done"s by a green mark
      // TODO: take ConsoleSender from Guice directly

      // Events listeners registration
      listenerRegister.registerListeners();

      messageController.sendRawMessage(
          getServer().getConsoleSender(),
          message.startupBannerProgressionLine("Events listeners registration done."));

      // Commands registration
      commandRegister.registerCommands();
      commandRegister.registerCommandCompletions();

      messageController.sendRawMessage(
          getServer().getConsoleSender(),
          message.startupBannerProgressionLine("Commands registration done."));

      // Plugin enabled successfully
      messageController.sendRawMessage(
          getServer().getConsoleSender(), message.startupBannerEnablingSuccessLine());
      messageController.sendRawMessage(getServer().getConsoleSender(), Component.empty());
    } catch (RuntimeException e) {
      messageController.sendRawMessage(
          getServer().getConsoleSender(), message.startupBannerEnablingFailureLine());
      messageController.sendRawMessage(getServer().getConsoleSender(), Component.empty());
      getSLF4JLogger().error("Something went wrong and prevent plugin activation.", e);
    }
  }
}
