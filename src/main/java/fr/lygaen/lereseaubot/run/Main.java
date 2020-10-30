package fr.lygaen.lereseaubot.run;

import fr.lygaen.lereseaubot.commands.AddGlobalChannel;
import fr.lygaen.lereseaubot.commands.Ping;
import fr.lygaen.lereseaubot.core.client.DClient;
import fr.lygaen.lereseaubot.events.Listener;
import fr.lygaen.lereseaubot.events.MultiGuildHandler;
import fr.lygaen.lereseaubot.util.Secret;
import net.dv8tion.jda.api.JDA;

import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Main {
    public static LinkedList<Long> channels;
    public static JDA jda;
    public static void main(String[] args) throws LoginException, IOException {
        if(!(new File("channels.txt").exists())) new File("channels.txt").createNewFile();
        DClient client = new DClient(Secret.TOKEN)
                .setPrefix('§')
                .addCommand(new Ping())
                .addCommand(new AddGlobalChannel())
                .addListener(new MultiGuildHandler())
                .addWrapperListener(new Listener());

        jda = client.build();
        getChannels();
    }

    private static void getChannels() throws IOException {
        channels = new LinkedList<>();
        File f = new File("channels.txt");
        LinkedList<String> content = getContentFromFile(f);
        content.forEach((line) -> channels.add(Long.valueOf(line)));
    }

    private static LinkedList<String> getContentFromFile(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        LinkedList<String> builder = new LinkedList<>();
        while ((st = br.readLine()) != null) {
            if(!st.strip().matches("")) {
                builder.add(st);
            }
        }
        return builder;
    }
}
