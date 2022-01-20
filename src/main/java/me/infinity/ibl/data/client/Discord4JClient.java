package me.infinity.ibl.data.client;

import discord4j.core.GatewayDiscordClient;
import me.infinity.ibl.data.IBLDiscordClient;

/**
 * Implementation of abstract class {@link IBLDiscordClient} for the Discord4J library
 */
public class Discord4JClient extends IBLDiscordClient<GatewayDiscordClient> {

    /**
     * @param discordApi The DiscordClient object of your bot
     */
    public Discord4JClient(GatewayDiscordClient discordApi) {
        super(discordApi);
    }

    @Override
    public String getId() {
        return discordApi.getSelfId().asString();
    }

    @Override
    public long getGuildCount() {
        return discordApi.getGuilds().count().blockOptional().orElse(0L);
    }

    @Override
    public int getShardCount() {
        return discordApi.getGatewayClientGroup().getShardCount();
    }
}
