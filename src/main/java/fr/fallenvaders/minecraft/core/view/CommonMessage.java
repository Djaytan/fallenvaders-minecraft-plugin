package fr.fallenvaders.minecraft.core.view;

import java.util.ResourceBundle;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;

@Singleton
public final class CommonMessage {

  private static final Component STARTUP_BANNER_INDENT = Component.text("      ");

  private final MiniMessage miniMessage;
  private final ResourceBundle resourceBundle;

  @Inject
  private CommonMessage(@NotNull MiniMessage miniMessage, @NotNull ResourceBundle resourceBundle) {
    this.miniMessage = miniMessage;
    this.resourceBundle = resourceBundle;
  }

  /*
   *        _____       _  _            __     __          _
   *       |  ___|__ _ | || |  ___  _ __\ \   / /__ _   __| |  ___  _ __  ___
   *       | |_  / _` || || | / _ \| '_ \\ \ / // _` | / _` | / _ \| '__|/ __|
   *       |  _|| (_| || || ||  __/| | | |\ V /| (_| || (_| ||  __/| |   \__ \
   *       |_|   \__,_||_||_| \___||_| |_| \_/  \__,_| \__,_| \___||_|   |___/
   */
  public @NotNull Component startupBanner() {
    return Component.join(
        JoinConfiguration.newlines(),
        Component.newline(),
        STARTUP_BANNER_INDENT.append(
            Component.text(" _____       _  _            __     __          _")
                .color(NamedTextColor.GOLD)),
        STARTUP_BANNER_INDENT.append(
            Component.text("|  ___|__ _ | || |  ___  _ __\\ \\   / /__ _   __| |  ___  _ __  ___")
                .color(NamedTextColor.GOLD)),
        STARTUP_BANNER_INDENT.append(
            Component.text(
                    "| |_  / _` || || | / _ \\| '_ \\\\ \\ / // _` | / _` | / _ \\| '__|/ __|")
                .color(NamedTextColor.GOLD)),
        STARTUP_BANNER_INDENT.append(
            Component.text("|  _|| (_| || || ||  __/| | | |\\ V /| (_| || (_| ||  __/| |   \\__ \\")
                .color(NamedTextColor.GOLD)),
        STARTUP_BANNER_INDENT.append(
            Component.text(
                    "|_|   \\__,_||_||_| \\___||_| |_| \\_/  \\__,_| \\__,_| \\___||_|  |___/")
                .color(NamedTextColor.GOLD)),
        Component.newline());
  }

  public @NotNull Component startupBannerVersionLine(
      @NotNull PluginDescriptionFile pluginDescriptionFile) {
    return STARTUP_BANNER_INDENT.append(
        miniMessage.deserialize(
            resourceBundle.getString("fallenvaders.common.message.startup.current_version"),
            TagResolver.resolver(
                Placeholder.unparsed("fv_plugin_version", pluginDescriptionFile.getVersion()))));
  }

  public @NotNull Component startupBannerProgressionLine(@NotNull String text) {
    return STARTUP_BANNER_INDENT.append(
        miniMessage.deserialize(
            resourceBundle.getString("fallenvaders.common.message.startup.progression_line.format"),
            TagResolver.resolver(Placeholder.unparsed("fv_progression_message", text))));
  }

  public @NotNull Component startupBannerEnablingSuccessLine() {
    return STARTUP_BANNER_INDENT.append(
        miniMessage.deserialize(
            resourceBundle.getString("fallenvaders.common.message.startup.enabling_successfully")));
  }

  public @NotNull Component startupBannerEnablingFailureLine() {
    return STARTUP_BANNER_INDENT.append(
        miniMessage.deserialize(
            resourceBundle.getString("fallenvaders.common.message.startup.enabling_failed")));
  }

  public @NotNull Component impossibleToTargetConsole() {
    return miniMessage.deserialize(
        resourceBundle.getString("fallenvaders.common.command.impossible_to_target_console"));
  }
}
