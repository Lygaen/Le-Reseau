package fr.lygaen.lereseaubot.commands;

import fr.lygaen.lereseaubot.core.commands.CommandBase;
import fr.lygaen.lereseaubot.core.commands.CommandContext;
import fr.lygaen.lereseaubot.core.errors.ArgError;
import fr.lygaen.lereseaubot.core.errors.PermissionError;
import fr.lygaen.lereseaubot.run.Main;
import net.dv8tion.jda.api.Permission;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddGlobalChannel implements CommandBase {

    protected static Logger logger = Logger.getLogger(AddGlobalChannel.class.getName());

    @Override
    public void run(CommandContext ctx) throws PermissionError, ArgError {
        if(!Objects.equals(ctx.getGuild().getOwner(), ctx.getMember())) throw new PermissionError();
        long l;
        try {
            l = Long.parseLong(ctx.getArgs().get(0));
            if(ctx.getJDA().getGuildChannelById(l) == null) throw new ArgError();
        } catch (NumberFormatException e) {throw new ArgError();}
        Main.channels.add(l);
        try {
            Files.write(Paths.get("channels.txt"), String.valueOf(l).getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "COULDN'T WRITE TO THE FILE 'CHANNELS.TXT' !");
        }
    }

    @Override
    public String getInvoker() {
        return "addglobalchannel";
    }

    @Nullable
    @Override
    public Permission getRequiredPermission() {
        return Permission.ADMINISTRATOR;
    }

    @Override
    public int getArgSize() {
        return 1;
    }
}
