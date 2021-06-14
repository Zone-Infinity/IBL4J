package autoposting;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import me.infinity.ibl.IBLClient;

public class Discord4JAutoPosting {
    public static void main(String[] args) {
        final DiscordClient client = DiscordClient.create("TOKEN");
        final GatewayDiscordClient gateway = client.login().block();

        // Example for Discord4J
        IBLClient ibl = new IBLClient.Builder(gateway, "IBL_TOKEN");
        ibl.autoPostStats();

        gateway.onDisconnect().block();
    }
}
