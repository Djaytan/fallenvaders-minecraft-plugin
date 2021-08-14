package fr.fallenvaders.minecraft.core.fight_session.utils;

import fr.fallenvaders.minecraft.core.FallenVadersCorePlugin;

/**
 * Le niveau des messages (info, notification, warning, faillure & error) <br>
 * Leurs valeurs sont tiré du fichier de configuration principal
 */
public enum MessageLevel {

    INFO(FallenVadersCorePlugin.main
        .getConfig().getString("message_levels.info")), NOTIFICATION(FallenVadersCorePlugin.main
        .getConfig().getString("message_levels.notification")),
    WARNING(FallenVadersCorePlugin.main
        .getConfig().getString("message_levels.warning")), FAILLURE(FallenVadersCorePlugin.main
        .getConfig().getString("message_levels.faillure")),
    ERROR(FallenVadersCorePlugin.main
        .getConfig().getString("message_levels.error"));

    private String color = "§f";

    private MessageLevel(String str) {
        if (str != null) {
            this.setColor(str.replace("&", "§"));
        }
    }

    public String getColor() {
        return color;
    }

    private void setColor(String color) {
        this.color = color;
    }

    public static void refresh() {
        for (MessageLevel e : values()) {
            e.color = FallenVadersCorePlugin.main
                .getConfig().getString("message_levels." + e.name().toLowerCase()).replace("&", "§");
        }
    }

}
