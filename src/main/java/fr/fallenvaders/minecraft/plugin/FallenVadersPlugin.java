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
      messageController.sendConsoleMessage(message.startupBanner());
      messageController.sendConsoleMessage(message.startupBannerVersionLine(getDescription()));
      messageController.sendConsoleMessage(message.startupBannerProgressionLine("Guice injection"));

      // Events listeners registration
      listenerRegister.registerListeners();

      messageController.sendConsoleMessage(
          message.startupBannerProgressionLine("Events listeners registration"));

      // Commands registration
      commandRegister.registerCommands();
      commandRegister.registerCommandCompletions();

      messageController.sendConsoleMessage(
          message.startupBannerProgressionLine("Commands registration"));

      // Plugin enabled successfully
      messageController.sendConsoleMessage(message.startupBannerEnablingSuccessLine());
      messageController.sendConsoleMessage(Component.empty());
    } catch (RuntimeException e) {
      messageController.sendConsoleMessage(message.startupBannerEnablingFailureLine());
      messageController.sendConsoleMessage(Component.empty());
      getSLF4JLogger().error("Something went wrong and prevent plugin activation.", e);
    }
  }
}
