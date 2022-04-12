package fr.fallenvaders.minecraft.plugin.view;

import java.util.ResourceBundle;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;

@Singleton
public final class Message {

  private static final Component STARTUP_BANNER_INDENT = Component.text("      ");

  private final MiniMessage miniMessage;
  private final ResourceBundle resourceBundle;

  @Inject
  private Message(@NotNull MiniMessage miniMessage, @NotNull ResourceBundle resourceBundle) {
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
        Component.text("Current version:")
            .color(NamedTextColor.AQUA)
            .append(Component.space())
            .append(
                Component.text(pluginDescriptionFile.getVersion()).color(NamedTextColor.WHITE)));
  }

  public @NotNull Component startupBannerProgressionLine(@NotNull String text) {
    return STARTUP_BANNER_INDENT.append(Component.text(text).color(NamedTextColor.YELLOW));
  }

  public @NotNull Component startupBannerEnablingSuccessLine() {
    return STARTUP_BANNER_INDENT.append(
        Component.text("Plugin successfully activated!").color(NamedTextColor.GREEN));
  }

  public @NotNull Component startupBannerEnablingFailureLine() {
    return STARTUP_BANNER_INDENT.append(
        Component.text("Plugin failed to be activated! See exception below...")
            .color(NamedTextColor.RED));
  }
}
