package fr.fallenvaders.minecraft.core.fight_session.utils.config;

import java.io.File;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.logging.Level;

import org.apache.commons.lang.StringUtils;

import fr.fallenvaders.minecraft.core.FallenVadersCore;


/**
 * Gère les fichier de message externalisées. (Lang)
 */
public class LangManager extends ConfigController {
    private static String FOLDER_PATH = FallenVadersCore.main
        .getDataFolder() + File.separator + "lang";
    private static String CFG_NAME = FallenVadersCore.main
        .getConfig().getString("lang_file") + ".yml";
    private static String DEFAULT_RESSOURCE = FallenVadersCore.main
        .getConfig().getString("lang_file") + ".yml";

    public LangManager() {
        super(FOLDER_PATH, CFG_NAME, DEFAULT_RESSOURCE);
    }

    public LangManager(String folder_path, String cfg_name, String default_ressource) {
        super(folder_path, cfg_name, default_ressource);
    }

    /**
     * Charge les fichier de lang par défaut
     *
     * @return l'instance de LangManager utilisé
     */
    public static LangManager loadDefaults() {
        LangManager FR_fr = new LangManager(FallenVadersCore.main
            .getDataFolder() + File.separator + "lang", "FR_fr.yml",
            "FR_fr.yml");
        FR_fr.saveDefault();

        LangManager EN_en = new LangManager(FallenVadersCore.main
            .getDataFolder() + File.separator + "lang", "EN_en.yml",
            "EN_en.yml");
        EN_en.saveDefault();

        LangManager defaultLangManager = new LangManager(FallenVadersCore.main
            .getDataFolder() + File.separator + "lang",
            FallenVadersCore.main
                .getConfig().getString("lang_file") + ".yml",
            FallenVadersCore.main
                .getConfig().getString("lang_file") + ".yml");
        defaultLangManager.load();
        return defaultLangManager;

    }

    public String getValue(String str, Object... args) {
        String res = null;

        try {
            res = String.format(this.getValue(str), args);

        } catch (IllegalFormatException e) {
            res = str + " : §4wrong format§r";
        }

        return res;
    }

    public String getValue(String id) {
        String res = configuration.getString(id);

        if (res == null || res.isEmpty()) {
            res = id + " : §4missing translation§r";
        }

        res = res.replace(".#*", "%s").replace(".#1*", "%1$s").replace(".#2*", "%2$s").replace(".#3*", "%3$s")
            .replace(".#4*", "%4$s").replace(".#5*", "%5$s");

        return res;
    }

    public String getValueOfList(String id) {
        List<String> temp = configuration.getStringList(id);
        String res = StringUtils.join(temp, "\n");

        if (res == null || res.isEmpty()) {
            res = id + " : §4missing translation§r";
        }
        //
        res = res.replace(".#*", "%s").replace(".#1*", "%1$s").replace(".#2*", "%2$s").replace(".#3*", "%3$s")
            .replace(".#4*", "%4$s").replace(".#5*", "%5$s");

        return res;
    }

    @Override
    protected void onLoad() {
        if (configuration != null) {
            FallenVadersCore.main
                .getLogger().log(Level.INFO, "Lang file \"" + CFG_NAME + "\" loaded.");

        } else {
            FallenVadersCore.main
                .getLogger().log(Level.SEVERE,
                    "Can't load the lang file \"" + CFG_NAME + "\". The default lang file will be used.");
        }
    }

    @Override
    protected void onSave() {

    }

    @Override
    public void load() {
        FOLDER_PATH = FallenVadersCore.main
            .getDataFolder() + File.separator + "lang";
        CFG_NAME = FallenVadersCore.main
            .getConfig().getString("lang_file") + ".yml";
        DEFAULT_RESSOURCE = FallenVadersCore.main
            .getConfig().getString("lang_file") + ".yml";

        super.setFolderPath(FOLDER_PATH);
        super.setFile(CFG_NAME);
        super.setDefaultRessource(DEFAULT_RESSOURCE);

        super.load();

    }

}
