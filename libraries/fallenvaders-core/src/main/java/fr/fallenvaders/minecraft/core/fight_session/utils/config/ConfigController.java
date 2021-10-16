package fr.fallenvaders.minecraft.core.fight_session.utils.config;

import fr.fallenvaders.minecraft.core.FallenVadersCore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

/** Donne des methodes pour la gestion des fichiers config; */
public abstract class ConfigController {
  protected File folder;
  protected File file;
  protected FileConfiguration configuration;
  protected String defaultRessource;

  protected ConfigController(String folderPath, String fileName, String defaultRessource) {
    this.folder = new File(folderPath);
    this.file = new File(folderPath + File.separator + fileName);
    this.defaultRessource = defaultRessource;
  }

  protected void setFolderPath(String newFolderPath) {
    this.folder = new File(newFolderPath);
  }

  protected void setFile(String newFileName) {
    this.file = new File(this.folder + File.separator + newFileName);
  }

  protected void setDefaultRessource(String newDefaultRessource) {
    this.defaultRessource = newDefaultRessource;
  }

  protected abstract void onLoad();

  protected abstract void onSave();

  /**
   * Charge ou recharger le fichier<br>
   * créer ou re créer le fichier config depuis les champs si inexistant
   */
  public void load() {
    if (!folder.exists()) {
      folder.mkdirs();
    }

    if (!file.exists()) {

      if (this.defaultRessource != null) {
        this.saveDefault();

      } else {
        try {
          file.createNewFile();
          this.loadConfiguration();

        } catch (IOException e) {
          e.printStackTrace();
        }
      }

    } else {
      this.loadConfiguration();
    }

    this.onLoad();
  }

  public void save() {
    this.onSave();

    try {
      configuration.save(file);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** Ecrit le fichier par default sur l'emplacement cible dossier fichier <br> */
  public void saveDefault() {
    if (!folder.exists()) {
      folder.mkdirs();
    }

    if (!file.exists()) {
      try {
        file.createNewFile();

      } catch (IOException e) {
        FallenVadersCore.main
            .getLogger()
            .log(
                Level.SEVERE,
                "Could not create config to " + file.getName() + " to " + file.getPath());
        e.printStackTrace();
      }

      InputStream customClassStream = FallenVadersCore.main.getResource(this.defaultRessource);
      InputStreamReader strR = new InputStreamReader(customClassStream, StandardCharsets.UTF_8);
      YamlConfiguration newConfig = YamlConfiguration.loadConfiguration(strR);

      try {
        newConfig.save(file);

      } catch (IOException e) {
        FallenVadersCore.main
            .getLogger()
            .log(Level.SEVERE, "Could not save config: " + newConfig.getName());
        e.printStackTrace();
      }

      this.configuration = newConfig;
    }
  }

  protected void loadConfiguration() {
    this.configuration = YamlConfiguration.loadConfiguration(this.file);
  }

  public FileConfiguration getConfig() {
    return this.configuration;
  }
}