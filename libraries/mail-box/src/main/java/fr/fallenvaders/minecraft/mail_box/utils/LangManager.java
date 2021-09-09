package fr.fallenvaders.minecraft.mail_box.utils;

import fr.fallenvaders.minecraft.mail_box.MailBox;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.IllegalFormatException;
import java.util.logging.Level;

public class LangManager {

  private static FileConfiguration configuration;

  public static void load() {
    saveDefaultLangFile();

    String fileName = MailBox.main.getConfig().getString("lang_file");

    if (fileName == null || fileName.isEmpty()) {
      fileName = "FR_fr";
    }

    FileConfiguration cfg =
        YamlConfiguration.loadConfiguration(
            new File(
                MailBox.main.getDataFolder()
                    + File.separator
                    + "lang"
                    + File.separator
                    + fileName
                    + ".yml"));

    if (cfg != null) {
      setConfiguration(cfg);
      MailBox.main.getLogger().log(Level.INFO, "Lang file \"" + fileName + "\" loaded.");

    } else {
      MailBox.main
          .getLogger()
          .log(
              Level.SEVERE,
              "Can't load the lang file \"" + fileName + "\". The default lang file will be used.");
    }
  }

  public static String getValue(String str, Object... args) {
    String res = null;

    try {
      res = String.format(getValue(str), args);

    } catch (IllegalFormatException e) {
      res = String.format("%s%s%s%s", ChatColor.RED, str, " : wrong format", ChatColor.RESET);
    }

    return res;
  }

  public static String format(String str, Object... args) {
    String res = null;

    try {
      res = String.format(str, args);

    } catch (IllegalFormatException e) {
      res = String.format("%s%s%s%s", ChatColor.RED, str, " : wrong format", ChatColor.RESET);
    }

    return res;
  }

  public static String getValue(String id) {
    String res = getConfiguration().getString(id);

    if (res == null || res.isEmpty()) {
      res = String.format("%s%s%s%s", ChatColor.RED, id, " : missing translation", ChatColor.RESET);
    }

    res = res.replace(".#*", "%s");

    return res;
  }

  private static FileConfiguration getConfiguration() {
    return configuration;
  }

  private static void setConfiguration(FileConfiguration configuration) {
    LangManager.configuration = configuration;
  }

  private static void saveDefaultLangFile() {
    File folder = new File(MailBox.main.getDataFolder() + File.separator + "lang");
    File file = new File(folder.getPath() + File.separator + "FR_fr.yml");

    YamlConfiguration newConfig = null;

    if (!folder.exists()) {
      folder.mkdirs();
    }

    if (!file.exists()) {
      try {
        file.createNewFile();

      } catch (IOException e) {
        MailBox.main
            .getLogger()
            .log(
                Level.SEVERE,
                "Could not create config to " + file.getName() + " to " + file.getPath());
        e.printStackTrace();
      }

      InputStream customClassStream = MailBox.main.getResource("FR_fr.yml");
      InputStreamReader strR = new InputStreamReader(customClassStream, StandardCharsets.UTF_8);
      newConfig = YamlConfiguration.loadConfiguration(strR);

      try {
        newConfig.save(file);
      } catch (IOException e) {
        MailBox.main.getLogger().log(Level.SEVERE, "Could not save config: " + newConfig.getName());
        e.printStackTrace();
      }

    } else {
      newConfig = YamlConfiguration.loadConfiguration(file);
    }

    setConfiguration(newConfig);
  }
}
