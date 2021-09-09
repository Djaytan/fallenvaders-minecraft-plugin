package fr.fallenvaders.minecraft.commons.cte.exceptions;

public class CommandNotFoundException extends Exception {

  public CommandNotFoundException(String cmdLabel) {
    super("Command \"" + cmdLabel + "\" not found: activation impossible.");
  }
}
