package fr.fallenvaders.minecraft.mc_enhancer.controller.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PingCmd implements CommandExecutor {

    @Override
    public boolean onCommand(
        @NotNull CommandSender commandSender,
        @NotNull Command command,
        @NotNull String s,
        @NotNull String[] strings) {
        if (commandSender instanceof Player player) {
            String ping = Integer.toString(player.getPing());
            player.sendMessage("Votre ping : " + ping);
        } else {
            commandSender.sendMessage("Vous devez être un joueur pour pouvoir exécuter cette commande.");
        }
        return true;
    }
}
