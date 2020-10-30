package fr.lygaen.lereseaubot.core.commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.LinkedList;
import java.util.function.Consumer;

/**
 * The context of a Command which includes channel, author,...
 */
public class CommandContext {

    /**
     * The event that invoked CTX
     */
    private final MessageReceivedEvent event;
    /**
     * The Event's invoker
     */
    private final String command;
    /**
     * The list of all args (might be empty)
     */
    private final LinkedList<String> args;
    /**
     * The author as an User
     */
    private final User user;
    /**
     * The author as a Member
     */
    private final Member member;

    /**
     * Create a CTX
     * @param event The message Recieved Event
     * @param command The Invoker Command
     * @param args The Args
     */
    public CommandContext(MessageReceivedEvent event, String command, LinkedList<String> args) {
        this.event = event;
        this.args = args;
        this.command = command;
        this.user = this.event.getAuthor();
        this.member = this.getGuild().retrieveMember(this.user).complete();
    }

    /**
     * Get the CTX's event
     * @return The event
     */
    private MessageReceivedEvent getEvent() {
        return event;
    }

    /**
     * Get the invoker that invoked CTX
     * @return The invoker as a String
     */
    public String getCommand() {
        return command;
    }

    /**
     * Get the args of the command
     * @return A LinkedList of all args
     */
    public LinkedList<String> getArgs() {
        return args;
    }

    /**
     * Get the channel the message has been sent in
     * @return The Channel as a TextChannel
     */
    public TextChannel getChannel() {
        return getEvent().getTextChannel();
    }

    /**
     * Get the Author of the message, as an User
     * @return The User
     */
    public User getUser() {
        return this.user;
    }

    /**
     * Get the Author of the message, as a Member
     * @return The member
     */
    public Member getMember() {
        return this.member;
    }

    /**
     * Get the Guild where the message has been sent in
     * @return The guild
     */
    public Guild getGuild() {
        return this.getEvent().getGuild();
    }

    public void send(MessageEmbed msg, Consumer<Message> consumer) {
        this.getChannel().sendMessage(msg).queue(consumer);
    }

    public void send(String msg, Consumer<Message> consumer) {
        this.getChannel().sendMessage(msg).queue(consumer);
    }

    /**
     * Send back an Embed to the channel, NOT RECOMMENDED
     * @param msg The embed
     * @return The message got from JDA
     */
    public Message csend(MessageEmbed msg) {
        return this.getChannel().sendMessage(msg).complete();
    }

    /**
     * Send back a Message as a String to the channel, NOT RECOMMENDED
     * @param msg The message as a String
     * @return The message got from JDA
     */
    public Message csend(String msg) {
        return this.getChannel().sendMessage(msg).complete();
    }

    /**
     * Get the JDA from the Event
     * @return JDA
     */
    public JDA getJDA() {
        return this.getEvent().getJDA();
    }
}
