package fr.fallenvaders.minecraft.mini_events.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TabComplete implements TabCompleter {

  @Override
  public List<String> onTabComplete(
      CommandSender sender, Command cmd, String label, String[] args) {
    ArrayList<String> Event = new ArrayList<String>();
    if (args.length >= 1) {
      if (args.length == 1) {
        if (args[0].startsWith("f")) {
          Event.add("forcerun");
        } else if (args[0].startsWith("h")) {
          Event.add("help");
        } else if (args[0].startsWith("j")) {
          Event.add("join");
        } else if (args[0].startsWith("l")) {
          Event.add("leave");
        } else if (args[0].startsWith("n")) {
          Event.add("name");
        } else if (args[0].startsWith("p")) {
          Event.add("participants");
        } else if (args[0].startsWith("r")) {
          Event.add("run");
        } else if (args[0].startsWith("s")) {
          Event.add("start");
          Event.add("startlist");
          Event.add("state");
          Event.add("stop");
        } else if (args[0].startsWith("t")) {
          Event.add("teleport");
        } else {
          Event.add("forcerun");
          Event.add("help");
          Event.add("join");
          Event.add("leave");
          Event.add("name");
          Event.add("participants");
          Event.add("run");
        }
        return Event;
      }
    }
    return null;
  }
}
