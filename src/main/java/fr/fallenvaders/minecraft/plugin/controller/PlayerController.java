package fr.fallenvaders.minecraft.plugin.controller;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface PlayerController {

  void healPlayer(@NotNull CommandSender sender, @NotNull Player targetedPlayer);

  void feedPlayer(@NotNull CommandSender sender, @NotNull Player targetedPlayer);
}
