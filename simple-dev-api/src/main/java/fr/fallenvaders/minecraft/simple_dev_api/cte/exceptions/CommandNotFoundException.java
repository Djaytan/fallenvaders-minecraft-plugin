package fr.fallenvaders.minecraft.simple_dev_api.cte.exceptions;

public class CommandNotFoundException extends Exception {

    public CommandNotFoundException(String cmdLabel) {
        super("Command \"" + cmdLabel + "\" not found: activation impossible.");
    }
}
