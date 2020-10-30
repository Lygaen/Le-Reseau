package fr.lygaen.lereseaubot.commands;

import fr.lygaen.lereseaubot.core.commands.CommandBase;
import fr.lygaen.lereseaubot.core.commands.CommandContext;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class Ping implements CommandBase {
    @Override
    public void run(CommandContext ctx) {
        long time = System.currentTimeMillis();

        ctx.send(new EmbedBuilder()
                .setTitle("📶 Calculating...")
                .setColor(Color.GRAY)
                .build(), (msg) -> {
            msg.editMessage(new EmbedBuilder()
                    .setTitle("📶 Ping")
                    .addField("Gateway", ctx.getJDA().getGatewayPing() + "ms", false)
                    .addField("Bot",  (System.currentTimeMillis() - time) + "ms", false)
                    .setColor(Color.GREEN)
                    .build()).queue();
        });
    }

    @Override
    public String getInvoker() {
        return "ping";
    }

    @Override
    public int getArgSize() {
        return 0;
    }
}
