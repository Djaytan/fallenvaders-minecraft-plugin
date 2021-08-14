package fr.fallenvaders.minecraft.justice_hands.criminalrecords.invmanager;

import java.util.List;
import java.util.UUID;

import fr.fallenvaders.minecraft.justice_hands.GeneralUtils;
import fr.fallenvaders.minecraft.justice_hands.JusticeHands;
import fr.fallenvaders.minecraft.justice_hands.criminalrecords.objects.CJSanction;
import fr.minuskube.inv.InventoryManager;
import fr.minuskube.inv.SmartInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

public class InventoryBuilderCR {

    // Création de l'inventaire affichant les sanctions d'un joueur:
    public static void openMainMenu(Player player, UUID targetUUID) {
        // Joueur cible du modérateur
        Player target = Bukkit.getPlayer(targetUUID);

        // Récupération de la liste des sanctions du joueur
        List<CJSanction> playerAllSanctionList = JusticeHands.getSqlSM().getPlayerSanctions(player);

        // Création de l'inventaire
        InventoryManager inventoryManager = new InventoryManager(JusticeHands.PLUGIN);
        inventoryManager.init();

        SmartInventory.Builder builder = SmartInventory.builder();
        builder.title(GeneralUtils.getPrefix("CR") + "§c" + target.getName());
        builder.provider(new InventoryCR(playerAllSanctionList));
        builder.id(targetUUID.toString());
        builder.size(6, 9);
        builder.type(InventoryType.CHEST);
        builder.manager(inventoryManager);
        SmartInventory inventory = builder.build();
        inventory.open(player);
    }
}
