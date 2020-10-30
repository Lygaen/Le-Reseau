package fr.lygaen.lereseaubot.core.events;

import fr.lygaen.lereseaubot.core.client.DClient;
import fr.lygaen.lereseaubot.core.commands.CommandBase;
import fr.lygaen.lereseaubot.core.commands.CommandContext;
import fr.lygaen.lereseaubot.core.errors.ArgError;
import fr.lygaen.lereseaubot.core.errors.ArgSizeError;
import fr.lygaen.lereseaubot.core.errors.PermissionError;
import fr.lygaen.lereseaubot.core.errors.UnknownCommandError;
import fr.lygaen.lereseaubot.core.events.wrapper.WrapperListener;
import fr.lygaen.lereseaubot.run.Main;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The handler for the message that is processed for a command
 */
public class MessageHandler extends ListenerAdapter {
    /**
     * The client Instance
     */
    private final DClient client;

    /**
     * The constructor
     * @param dClient the client given a the creation
     */
    public MessageHandler(DClient dClient) {
        this.client = dClient;
    }

    /**
     * When a message is recieved by the Handler
     * @param event The event
     */
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String msg = event.getMessage().getContentRaw();
        User author = event.getAuthor();
        if(author.isBot() ||
                !(msg.charAt(0) == this.client.getPrefix()) ||
                !event.getChannel().getType().equals(ChannelType.TEXT) ||
                !msg.matches(this.client.getPrefix() + "[A-Z-a-z-0-9-\\s]+") ||
                Main.channels.contains(event.getChannel().getIdLong())) {
            return;
        }
        LinkedList<String> args = new LinkedList<>(Arrays.asList(msg.replaceFirst(String.valueOf(this.client.getPrefix()), "").split("\\s")));
        String subcommand = args.get(0);
        args.remove(0);
        CommandBase commandBase = getCommand(subcommand);
        CommandContext ctx = new CommandContext(event, subcommand, args);
        try {
            if(commandBase == null) throw new UnknownCommandError();
            check(commandBase, args, ctx);
        } catch (ArgSizeError | UnknownCommandError | PermissionError e) {
            callEvent(e.getLocalizedMessage(), ctx);
            return;
        }
        try {
            commandBase.run(ctx);
        } catch (ArgError | PermissionError e) {
            callEvent(e.getLocalizedMessage(), ctx);
        }
    }

    /**
     * Call an DClient event from a string
     * @param name the name of the event
     * @param ctx the context to invoke the event
     */
    private void callEvent(String name, CommandContext ctx) {
        for (WrapperListener wrapperListener : client.getWrapperListeners()) {
            try {
                Method method = wrapperListener.getClass().getMethod(name, CommandContext.class);
                method.invoke(wrapperListener, ctx);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {/**/}
        }
    }

    /**
     * All checks
     * @param commandBase Required
     * @param args Required
     * @param ctx The context
     * @throws ArgSizeError If any check is false, the method will throw the corresponding error
     * @throws PermissionError If any check is false, the method will throw the corresponding error
     */
    private void check(CommandBase commandBase, LinkedList<String> args, CommandContext ctx) throws ArgSizeError, PermissionError {
        if(commandBase.getArgSize() != args.size() && commandBase.getArgSize() > -1) {
            throw new ArgSizeError();
        } else if(!ctx.getMember().hasPermission(commandBase.getRequiredPermission())) {
            throw new PermissionError();
        }
        AtomicBoolean hasPerms = new AtomicBoolean(true);
        HashMap<GuildChannel, Permission> h = commandBase.getRequiredPermissionForChannel();
        if (h != null) { h.forEach((c, p) -> { if (!ctx.getMember().hasPermission(c, p)) { hasPerms.set(false); } }); }
        if(!hasPerms.get()) {throw new PermissionError();}
    }

    /**
     * Get a command from the name
     * @param command The actual command
     * @return The command
     */
    private CommandBase getCommand(String command) {
        AtomicReference<CommandBase> object = new AtomicReference<>();
        client.getCommands().forEach((cmd) -> {
            if(cmd.getInvoker().equals(command)) object.set(cmd);
        });
        return object.get();
    }
}
