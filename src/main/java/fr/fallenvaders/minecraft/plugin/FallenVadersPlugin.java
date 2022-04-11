package fr.fallenvaders.minecraft.plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.plugin.java.JavaPlugin;

public class FallenVadersPlugin extends JavaPlugin {

  @Override
  public void onEnable() {
    /*
     *        _____       _  _            __     __          _
     *       |  ___|__ _ | || |  ___  _ __\ \   / /__ _   __| |  ___  _ __  ___
     *       | |_  / _` || || | / _ \| '_ \\ \ / // _` | / _` | / _ \| '__|/ __|
     *       |  _|| (_| || || ||  __/| | | |\ V /| (_| || (_| ||  __/| |   \__ \
     *       |_|   \__,_||_||_| \___||_| |_| \_/  \__,_| \__,_| \___||_|   |___/
     */
    Component startupBanner =
        Component.join(
            JoinConfiguration.newlines(),
            Component.newline(),
            Component.text("       _____       _  _            __     __          _")
                .color(NamedTextColor.GOLD),
            Component.text(
                    "      |  ___|__ _ | || |  ___  _ __\\ \\   / /__ _   __| |  ___  _ __  ___")
                .color(NamedTextColor.GOLD),
            Component.text(
                    "      | |_  / _` || || | / _ \\| '_ \\\\ \\ / // _` | / _` | / _ \\| '__|/"
                        + " __|")
                .color(NamedTextColor.GOLD),
            Component.text(
                    "      |  _|| (_| || || ||  __/| | | |\\ V /| (_| || (_| ||  __/| |   \\__ \\")
                .color(NamedTextColor.GOLD),
            Component.text(
                    "      |_|   \\__,_||_||_| \\___||_| |_| \\_/  \\__,_| \\__,_| \\___||_|  "
                        + " |___/")
                .color(NamedTextColor.GOLD),
            Component.newline(),
            Component.text("      Current version:")
                .color(NamedTextColor.AQUA)
                .append(Component.space())
                .append(Component.text(getDescription().getVersion()).color(NamedTextColor.WHITE)),
            Component.text("      Plugin successfully activated!").color(NamedTextColor.GREEN),
            Component.text(""));

    getServer().getConsoleSender().sendMessage(startupBanner);
  }
}
