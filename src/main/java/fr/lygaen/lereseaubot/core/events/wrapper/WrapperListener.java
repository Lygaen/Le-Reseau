package fr.lygaen.lereseaubot.core.events.wrapper;

import fr.lygaen.lereseaubot.core.commands.CommandContext;

/**
 * A listener for events inside of the DClient
 */
public class WrapperListener {
    /**
     * Triggered when The size given to a command
     * id not the one specified
     * @param ctx The command's context
     */
    public void onArgSizeError(CommandContext ctx) {/**/}

    /**
     * Triggered when the command send in a channel
     * is not recognized
     * @param ctx The command's context
     */
    public void onUnknownCommandError(CommandContext ctx) {/**/}

    /**
     * Triggered when there is a specified problem
     * with an arg, to use it throw ArgError in a
     * command
     * @param ctx The command's context
     */
    public void onArgError(CommandContext ctx) {/**/}

    /**
     * Triggered when the member trying to run the
     * command doesn't have the required perms,
     * to use it throw PermissionError in command
     * @param ctx The command's context
     */
    public void onPermissionError(CommandContext ctx) {/**/}
}
