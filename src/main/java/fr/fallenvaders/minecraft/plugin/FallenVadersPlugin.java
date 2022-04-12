package fr.fallenvaders.minecraft.plugin;

import fr.fallenvaders.minecraft.plugin.controller.MessageController;
import fr.fallenvaders.minecraft.plugin.guice.GuiceInjector;
import fr.fallenvaders.minecraft.plugin.view.Message;
import javax.inject.Inject;
import net.kyori.adventure.text.Component;
import org.bukkit.plugin.java.JavaPlugin;

public class FallenVadersPlugin extends JavaPlugin {

  @Inject private CommandRegister commandRegister;
  @Inject private Message message;
  @Inject private MessageController messageController;

  @Override
  public void onEnable() {
    // TODO: exception handling
    GuiceInjector.inject(this);

    messageController.sendRawMessage(getServer().getConsoleSender(), message.startupBanner());
    messageController.sendRawMessage(
        getServer().getConsoleSender(), message.startupBannerVersionLine(getDescription()));
    messageController.sendRawMessage(
        getServer().getConsoleSender(),
        message.startupBannerProgressionLine("Guice injection done."));

    // Commands registration
    commandRegister.registerCommands();
    commandRegister.registerCommandCompletions();

    messageController.sendRawMessage(
        getServer().getConsoleSender(),
        message.startupBannerProgressionLine("Commands registration done."));

    // Plugin enabled successfully
    messageController.sendRawMessage(
        getServer().getConsoleSender(), message.startupBannerEnablingSuccessLine());
    getServer().getConsoleSender().sendMessage(Component.empty());
  }
}
