package autoposting;

import me.infinity.ibl.IBLClient;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class JDAAutoPosting {
    public static void main(String[] args) throws LoginException {
        JDA jda = JDABuilder
                .createDefault("TOKEN")
                .build();

        IBLClient ibl = new IBLClient.Builder(jda, "IBL_TOKEN");

        // Post stats per hour
        ibl.autoPostStats();
    }
}
