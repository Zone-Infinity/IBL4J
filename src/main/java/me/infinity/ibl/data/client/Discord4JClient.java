package me.infinity.ibl.data.client;

import discord4j.core.GatewayDiscordClient;
import me.infinity.ibl.data.IBLDiscordClient;

public class Discord4JClient extends IBLDiscordClient<GatewayDiscordClient> {

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
