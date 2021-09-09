package fr.fallenvaders.minecraft.mail_box.utils;

import org.bukkit.ChatColor;

public enum MessageLevel {
  INFO(ChatColor.YELLOW.toString()),
  NOTIFICATION(ChatColor.GREEN.toString()),
  ERROR(ChatColor.RED.toString());

  private String color;

  private MessageLevel(String str) {
    this.setColor(str);
  }

  public String getColor() {
    return color;
  }

  private void setColor(String color) {
    this.color = color;
  }
}
