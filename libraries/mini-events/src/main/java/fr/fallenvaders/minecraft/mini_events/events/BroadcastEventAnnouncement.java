package fr.fallenvaders.minecraft.mini_events.events;

import org.bukkit.Bukkit;

public class BroadcastEventAnnouncement {

    // Message envoyé à tous les joueurs présent sur le serveur lors du lancement
    // d'un événement par un membre du personnel.
    public static void BroadcastMessage(String eventName) {
        Bukkit.broadcastMessage(" ");
        Bukkit.broadcastMessage("  §7§l** §6Un événement §b§l" + eventName + " §r§6va bientôt démarrer ! §7§l**");
        Bukkit.broadcastMessage(" §eTu veux y participer ? Fait la commande §b/event join §e!");
        Bukkit.broadcastMessage(" ");
        Bukkit.broadcastMessage(" §c(Tu seras téléporté dans un autre monde, sans ton stuff et dans une zone protégée !)");
        Bukkit.broadcastMessage(" ");
    }

}
