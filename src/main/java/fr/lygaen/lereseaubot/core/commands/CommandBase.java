package fr.lygaen.lereseaubot.core.commands;

import fr.lygaen.lereseaubot.core.errors.ArgError;
import fr.lygaen.lereseaubot.core.errors.PermissionError;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildChannel;

import javax.annotation.Nullable;
import java.util.HashMap;

/**
 * The interface to make a Command
 */
public interface CommandBase {
    /**
     * Run the command
     * @param ctx the Command's Context
     * @throws ArgError If the arg is not what you asked
     * @throws PermissionError If the permission is of the member is not enough
     */
    void run(CommandContext ctx) throws ArgError, PermissionError;

    /**
     * Get the Invoker for the command (ex : "ping") but
     * not the args
     * @return The invoker
     */
    String getInvoker();

    /**
     * Get the Arg size required for the command to run properly,
     * if it's -1, there isn't arg size
     * @return The size
     */
    default int getArgSize() {
        return -1;
    }

    /**
     * Get the permissions required by the member to run the command
     * @return The permissions required to run the command
     */
    @Nullable
    default Permission getRequiredPermission() {return null;}

    /**
     * Get the permissions required by the member to run the command
     * for a specific channel
     * @return The permissions required to run the command
     */
    @Nullable
    default HashMap<GuildChannel, Permission> getRequiredPermissionForChannel() {return null;}

}
