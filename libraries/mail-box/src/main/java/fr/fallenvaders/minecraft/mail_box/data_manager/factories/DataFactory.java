package fr.fallenvaders.minecraft.mail_box.data_manager.factories;

import fr.fallenvaders.minecraft.mail_box.data_manager.Data;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Créer des Data (pour l'importation depuis la base de donnée)
 *
 * @author Bletrazer
 */
public class DataFactory extends Data {

  public DataFactory(Long id, UUID uuid, String author, String object, Timestamp creationDate) {
    super(id, uuid, author, object, creationDate);
  }

  public DataFactory(UUID uuid, String author, String object) {
    super(null, uuid, author, object, null);
  }

  public DataFactory() {}
}
