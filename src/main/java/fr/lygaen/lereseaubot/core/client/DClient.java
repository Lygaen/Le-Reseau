package fr.lygaen.lereseaubot.core.client;

import fr.lygaen.lereseaubot.core.commands.CommandBase;
import fr.lygaen.lereseaubot.core.events.MessageHandler;
import fr.lygaen.lereseaubot.core.events.wrapper.WrapperListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * The DClient class, which is used all along the wrapper
 */
public class DClient {
    /**
     * The Token
     */
    private final String token;
    /**
     * The current prefix (Default to "!")
     */
    private Character prefix = '!';
    /**
     * All registered events
     */
    private final LinkedList<ListenerAdapter> events = new LinkedList<>();
    /**
     * All registered Commands
     */
    private final LinkedList<CommandBase> commands = new LinkedList<>();
    /**
     * All DClient's registered events
     */
    private final LinkedList<WrapperListener> wrapperListeners = new LinkedList<>();

    /**
     * Generate the DClient instance with the Discord Bot's Token
     * @param token The token
     */
    public DClient(String token) {
        this.events.add(new MessageHandler(this));
        this.token = token;
    }

    /**
     * Sets the prefix of the Bot ( Default to "!" )
     * @param prefix The prefix
     * @return the DClient Instance
     */
    public DClient setPrefix(Character prefix) {
        this.prefix = prefix;
        return this;
    }

    /**
     * Get the current prefix of the Bot
     * @return the DClient Instance
     */
    public Character getPrefix() {
        return prefix;
    }

    /**
     * Build the DClient to be a JDA
     * @return Get the JDA from DClient
     * @throws LoginException If the Login failed
     */
    public JDA build() throws LoginException {
        JDABuilder build = JDABuilder.createDefault(this.token);
        events.forEach((build::addEventListeners));
        return build.build();
    }

    /**
     * Add multiples JDA listeners
     * @param Listener The Listener
     * @return the DClient Instance
     */
    public DClient addListener(ListenerAdapter... Listener) {
        this.events.addAll(Arrays.asList(Listener));
        return this;
    }

    /**
     * Add multiples command to handle for DClient
     * @param commandBases The Commands
     * @return the DClient Instance
     */
    public DClient addCommand(CommandBase... commandBases) {
        this.commands.addAll(Arrays.asList(commandBases));
        return this;
    }

    /**
     * Get the list of all commands
     * @return the DClient Instance
     */
    public LinkedList<CommandBase> getCommands() {
        return commands;
    }

    /**
     * Get the list of all Dclient Listeners
     * @return the DClient Instance
     */
    public LinkedList<WrapperListener> getWrapperListeners() {
        return wrapperListeners;
    }

    /**
     * Add in multiples listeners to the DClient instance, different
     * from the JDA's Listener. They handle Errors such as Args error,
     * Bot Exception, Wrong type arg, ...
     * @param wrapperListener The Wrapper Listener
     * @return the DClient Instance
     */
    public DClient addWrapperListener(WrapperListener... wrapperListener) {
        this.wrapperListeners.addAll(Arrays.asList(wrapperListener));
        return this;
    }
}
