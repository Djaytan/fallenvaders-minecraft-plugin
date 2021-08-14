package fr.fallenvaders.minecraft.mini_events.commands.args;

import org.bukkit.entity.Player;

public class ArgHELP {
    public static void ArgHelp(Player player) {
        player.sendMessage("§7§l+§r§6------------------§7[§ePage d'aide§7]§6-----------------§7§l+");
        player.sendMessage("  §b/event join §7Permet de rejoindre un événement.");
        player.sendMessage("  §b/event leave §7Permet de quitter un événément.");
        player.sendMessage("  §b/event start <event> §7Permet de faire démarrer un événement.");
        player.sendMessage("  §b/event startlist §7Affiche la liste des événéments disponibles.");
        player.sendMessage("  §b/event teleport §7Ouvre un menu de téléportation aux événements.");
        player.sendMessage("  §b/event run §7Permet de lancer un événement §b(§7nombre de joueurs réquis§b)§7.");
        player.sendMessage("  §b/event forcerun §7Permet de forcer la mise en marche d'un événement.");
        player.sendMessage("  §b/event stop §7Permet de stoper un événement, peut importe son état.");
        player.sendMessage("  §b/event state §7Permet de vérifier l'état de l'événement en cours.");
        player.sendMessage("  §b/event name §7Permet de savoir quel événement est en cours.");
        player.sendMessage("  §b/event help §7Affiche cette page d'aide.");
        player.sendMessage("§7§l+§r§6----------------------------------------------§7§l+");
    }
}
