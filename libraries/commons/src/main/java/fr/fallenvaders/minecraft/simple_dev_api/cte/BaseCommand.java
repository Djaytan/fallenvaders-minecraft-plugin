package fr.fallenvaders.minecraft.simple_dev_api.cte;

import fr.fallenvaders.minecraft.simple_dev_api.MessageLevel;
import fr.fallenvaders.minecraft.simple_dev_api.UtilsAPI;
import fr.fallenvaders.minecraft.simple_dev_api.cte.exceptions.CommandNotFoundException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * Classe de gestion d'une commande de Dornacraft<br>
 * <br>
 * <b>Commande à ne pas créer : </b><i>/&lt;label&gt; help</i> (créée par
 * défaut)
 *
 * @author Voltariuss
 * @version 1.5.0
 */
public abstract class BaseCommand implements CommandExecutor {

    private CommandTreeExecutor cmdTreeExecutor = null;

    /**
     * Constructeur
     *
     * @param cmdLabel Le label de la commande, non null
     */
    public BaseCommand(String cmdLabel) {
        CommandNodeExecutor dce = (sender, cmd, label, args) -> {
            int page = args.length == 2 ? Integer.parseInt(args[1]) : 1;
            getCmdTreeExecutor().getRoot().sendHelpMessage(sender, label, page);
        };
        try {
            setCmdTreeExecutor(new CommandTreeExecutor(cmdLabel));
        } catch (CommandNotFoundException e) {
            e.printStackTrace();
        }
        getCmdTreeExecutor().getRoot().setExecutor(dce);
    }

    /**
     * Vérifie si le joueur possède la permission globale d'exécution de la
     * commande puis tente de l'exécuter en parcourant son arbre d'exécution.
     *
     * @param sender L'émetteur de la commande, non null
     * @param cmd    La commande Bukkit correspondante, non null
     * @param label  Le label de la commande, non null
     * @param args   La liste des arguments de la commande à exécuter, non null
     */
    @Override
    public final boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        String permission = cmd.getPermission();
        if (permission != null && sender.hasPermission(cmd.getPermission())) {
            getCmdTreeExecutor().checkCommand(sender, cmd, label, args);
        } else {
            UtilsAPI.sendSystemMessage(MessageLevel.ERROR, sender, UtilsAPI.PERMISSION_MISSING);
        }
        return true; // on bloque les actions de Bukkit sur la commande
    }

    /**
     * @return L'arbre d'exécution de la commande, non null
     */
    public CommandTreeExecutor getCmdTreeExecutor() {
        return cmdTreeExecutor;
    }

    /**
     * Définit l'arbre d'exécution de la commande.
     *
     * @param cmdTreeExecutor L'arbre d'exécution à associer à la commande, non null
     */
    public void setCmdTreeExecutor(CommandTreeExecutor cmdTreeExecutor) {
        this.cmdTreeExecutor = cmdTreeExecutor;
    }
}