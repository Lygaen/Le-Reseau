package fr.lygaen.lereseaubot.events;

import fr.lygaen.lereseaubot.core.commands.CommandContext;
import fr.lygaen.lereseaubot.core.events.wrapper.WrapperListener;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class Listener extends WrapperListener {

    @Override
    public void onArgSizeError(CommandContext ctx) {
        ctx.send(new EmbedBuilder()
                .setTitle("Ø Erreur")
                .setDescription("Vous n'avez pas donné le bon nombre d'arguments...")
                .setColor(Color.RED)
                .build(), null);
    }

    @Override
    public void onPermissionError(CommandContext ctx) {
        ctx.send(new EmbedBuilder()
                .setTitle("🛡 Erreur")
                .setDescription("Vous n'avez pas assez de permissions...")
                .setColor(Color.RED)
                .build(), null);
    }

    @Override
    public void onArgError(CommandContext ctx) {
        ctx.send(new EmbedBuilder()
                .setTitle("🌌 Erreur")
                .setDescription("Vous avez donner un argument, mais ce n'est pas le bon...")
                .setColor(Color.RED)
                .build(), null);
    }
}
