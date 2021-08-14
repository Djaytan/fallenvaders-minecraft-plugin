package fr.fallenvaders.minecraft.core.fight_session;

import fr.fallenvaders.minecraft.core.FallenVadersCore;
import fr.fallenvaders.minecraft.core.fight_session.listeners.FightsEvents;
import fr.fallenvaders.minecraft.core.fight_session.utils.MessageLevel;
import fr.fallenvaders.minecraft.core.fight_session.utils.MessageUtils;
import fr.fallenvaders.minecraft.core.fight_session.utils.config.LangManager;

/**
 * Controller du plugin
 */
public class PluginController {

    private static LangManager langManager = new LangManager();
    private static SessionManager sessionManager = new SessionManager();

    public static LangManager getLangManager() {
        return langManager;
    }

    public static SessionManager getSessionManager() {
        return sessionManager;
    }

    public static void init() {
        langManager = LangManager.loadDefaults();
        FightsEvents.initFightVars();
        Bar.init();
    }

    public static void reloadPlugin() {
        FallenVadersCore.main.reloadConfig();
        FightsEvents.initFightVars();
        Bar.init();
        MessageUtils.reload();
        langManager.load();
        MessageLevel.refresh();
        sessionManager.stopAll();

    }

}
