package autoposting;

import me.infinity.ibl.IBLClient;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class JavacordAutoPosting {
    public static void main(String[] args) {
        DiscordApi api = new DiscordApiBuilder().setToken("TOKEN").login().join();

        // Example for Discord4J
        IBLClient ibl = new IBLClient.Builder(api, "IBL_TOKEN");
        ibl.autoPostStats();
    }
}
