package fr.fallenvaders.minecraft.simple_dev_api.cte.exceptions;

import fr.fallenvaders.minecraft.simple_dev_api.cte.CommandNode;

/**
 * Classe de gestion d'une exception concernant une commande invalide
 *
 * @author Voltariuss
 * @version 1.5.0
 */
public class InvalidCommandException extends RuntimeException {

  private CommandNode node;

  /** Constructeur */
  public InvalidCommandException() {
    super();
  }

  /**
   * Constructeur avec message
   *
   * @param message Le message d'erreur associé à l'exception, non null
   */
  public InvalidCommandException(String message) {
    super(message);
  }

  /**
   * Constructeur avec un noeud associé
   *
   * @param node Le noeud associé à l'exception, non null
   */
  public InvalidCommandException(CommandNode node) {
    this();
    setNode(node);
  }

  /** @return Le noeud associé à l'exception, peut être null */
  public CommandNode getNode() {
    return node;
  }

  /**
   * Définit le noeud associé à l'exception.
   *
   * @param node Le noeud à associer à l'exception, peut être null
   */
  private void setNode(CommandNode node) {
    this.node = node;
  }
}
