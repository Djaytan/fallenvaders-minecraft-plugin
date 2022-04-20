package fr.fallenvaders.minecraft.plugin.controller;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface PlayerController {

  void healPlayer(@NotNull CommandSender sender, @NotNull Player targetedPlayer);

  void feedPlayer(@NotNull CommandSender sender, @NotNull Player targetedPlayer);

  void teleportToSpawn(@NotNull CommandSender sender, @NotNull Player targetedPlayer);

  void ping(@NotNull CommandSender sender, @NotNull Player targetedPlayer);

  void toggleFlyMode(@NotNull CommandSender sender, @NotNull Player targetedPlayer);

  void toggleFlyMode(
      @NotNull CommandSender sender, @NotNull Player targetedPlayer, boolean isFlyActivated);

  void toggleGodMode(@NotNull CommandSender sender, @NotNull Player targetedPlayer);

  void toggleGodMode(
      @NotNull CommandSender sender, @NotNull Player targetedPlayer, boolean isGodActivated);

  void openAnvil(@NotNull Player playerSender);

  void openCartographyTable(@NotNull Player playerSender);
}
