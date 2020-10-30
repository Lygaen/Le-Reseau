package fr.lygaen.lereseaubot.events;

import fr.lygaen.lereseaubot.run.Main;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.ranktw.DiscordWebHooks.DiscordMessage;
import net.ranktw.DiscordWebHooks.DiscordWebhook;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

public class MultiGuildHandler extends ListenerAdapter {

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        Main.channels.forEach((l) -> {
            TextChannel channel = (TextChannel) Main.jda.getGuildChannelById(l);
            assert channel != null;
            AtomicBoolean bool = new AtomicBoolean(false);
            try {
                channel.retrieveWebhooks().queue((webhooks -> webhooks.forEach((webhook -> bool.set(true)))));
            } catch (NullPointerException e) {/**/}
            if(!bool.get()) {
                channel.createWebhook("LeRéseau").queue();
            }
        });
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if(!event.isFromGuild()) {return;}
        AtomicBoolean bool = new AtomicBoolean(false);
        Main.channels.forEach((l) -> {
            if(event.getChannel().getIdLong() == l) {bool.set(true);}
        });
        if(!bool.get()) {return;}
        Message msg = event.getMessage();
        if(msg.getAuthor().isBot()) {return;}
        msg.delete().queue();
        float h = (float) (Math.random()*200);
        float s = (float) (Math.random()*200);
        float b = (float) (Math.random()*200);
        Main.channels.forEach((l) -> {
            TextChannel c = (TextChannel) event.getJDA().getGuildChannelById(l);
            assert c != null;
            c.retrieveWebhooks().queue((webhooks -> {
                DiscordWebhook client = new DiscordWebhook(webhooks.get(0).getUrl());
                DiscordMessage dm = new DiscordMessage.Builder()
                        .withUsername(msg.getAuthor().getName())
                        .withAvatarURL(msg.getAuthor().getAvatarUrl())
                        .withContent(msg.getContentRaw())
                        .build();
                client.sendMessage(dm);
            }));

        });
        System.out.println(new Date(System.currentTimeMillis()) + " G:" + event.getGuild().getName() + " U:" + event.getAuthor().getName() + " M:" + event.getMessage().getContentRaw());
    }
}
