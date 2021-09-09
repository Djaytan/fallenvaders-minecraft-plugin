package fr.fallenvaders.minecraft.mini_events.events.bowspleef;

import fr.fallenvaders.minecraft.mini_events.MiniEvents;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BowSpleefActions {

  public static ItemStack getFireBow() {
    ItemStack it = new ItemStack(Material.BOW, 1, (short) 0);
    ItemMeta itM = it.getItemMeta();
    itM.setDisplayName("§cFire Bow");
    itM.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
    itM.setUnbreakable(true);
    it.setItemMeta(itM);
    return it;
  }

  public static void EventElimationMessageFall(Player pls, Player player) {
    pls.sendMessage(
        "§f["
            + MiniEvents.getGame().getGameName().getEventColoredPrefix().toUpperCase()
            + "§f] §c"
            + player.getName()
            + " §7est tombé de très haut !");
  }
}
