package fr.fallenvaders.minecraft.plugin;

import fr.fallenvaders.minecraft.plugin.controller.api.MessageController;
import fr.fallenvaders.minecraft.plugin.guice.GuiceInjector;
import fr.fallenvaders.minecraft.plugin.view.CommonMessage;
import javax.inject.Inject;
import net.kyori.adventure.text.Component;
import org.bukkit.plugin.java.JavaPlugin;

public class FallenVadersPlugin extends JavaPlugin {

  @Inject private CommandRegister commandRegister;
  @Inject private ListenerRegister listenerRegister;
  @Inject private CommonMessage commonMessage;
  @Inject private MessageController messageController;

  @Override
  public void onEnable() {
    GuiceInjector.inject(this);

    try {
      messageController.sendConsoleMessage(commonMessage.startupBanner());
      messageController.sendConsoleMessage(
          commonMessage.startupBannerVersionLine(getDescription()));
      messageController.sendConsoleMessage(
          commonMessage.startupBannerProgressionLine("Guice injection"));

      // Events listeners registration
      listenerRegister.registerListeners();

      messageController.sendConsoleMessage(
          commonMessage.startupBannerProgressionLine("Events listeners registration"));

      // Commands registration
      commandRegister.registerCommands();
      commandRegister.registerCommandCompletions();

      messageController.sendConsoleMessage(
          commonMessage.startupBannerProgressionLine("Commands registration"));

      // Plugin enabled successfully
      messageController.sendConsoleMessage(commonMessage.startupBannerEnablingSuccessLine());
      messageController.sendConsoleMessage(Component.empty());
    } catch (RuntimeException e) {
      messageController.sendConsoleMessage(commonMessage.startupBannerEnablingFailureLine());
      messageController.sendConsoleMessage(Component.empty());
      getSLF4JLogger().error("Something went wrong and prevent plugin activation.", e);
    }
  }
}
