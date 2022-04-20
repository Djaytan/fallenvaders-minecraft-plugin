package fr.fallenvaders.minecraft.plugin.controller;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface PlayerController {

  void healPlayer(@NotNull CommandSender commandSender, @NotNull Player targetedPlayer);

  void feedPlayer(@NotNull CommandSender commandSender, @NotNull Player targetedPlayer);

  void teleportToSpawn(@NotNull CommandSender commandSender, @NotNull Player targetedPlayer);

  void ping(@NotNull CommandSender commandSender, @NotNull Player targetedPlayer);

  void toggleFlyMode(@NotNull CommandSender commandSender, @NotNull Player targetedPlayer);

  void toggleFlyMode(
      @NotNull CommandSender commandSender, @NotNull Player targetedPlayer, boolean isFlyActivated);

  void toggleGodMode(@NotNull CommandSender commandSender, @NotNull Player targetedPlayer);

  void toggleGodMode(
      @NotNull CommandSender commandSender, @NotNull Player targetedPlayer, boolean isGodActivated);

  void openAnvil(@NotNull Player playerSender);

  void openCartographyTable(@NotNull Player playerSender);

  void openGrindstone(@NotNull Player playerSender);

  void openLoom(@NotNull Player playerSender);

  void openStonecutter(@NotNull Player playerSender);

  void openSmithingTable(@NotNull Player playerSender);

  void openCraftingTable(@NotNull Player playerSender);
}
