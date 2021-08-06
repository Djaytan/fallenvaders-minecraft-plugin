package fr.fallenvaders.minecraft.mini_events.commands.args;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;

import fr.fallenvaders.minecraft.mini_events.GameName;

public class ArgSTARTLIST {

	public static void ArgStartList(String pluginPrefix, Player player) {
		String startlist = null;
		List<GameName> names = Arrays.asList(GameName.values());
		for (int i = 0; i < names.size(); i++) {
			if (i == 0) {
				startlist = ("§b" + names.get(i).name());
			} else {
				startlist = (startlist + "§7, §b" + names.get(i).name());
			}
		}
		startlist = startlist.replace("§bNONE§7, ", "");
		player.sendMessage(pluginPrefix + "§7Liste des événements disponible : " + startlist);
	}
}
