package fr.fallenvaders.minecraft.simple_dev_api.cte;

/**
 * Classe de gestion d'un checker d'argument
 *
 * @author Voltariuss
 * @version 1.5.0
 */
public interface CommandArgumentChecker {

  /**
   * Vérifie la validité des données saisies pour l'argument associé.
   *
   * @param arg L'argument associé, non null
   */
  void check(String arg) throws Exception;
}
