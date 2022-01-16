package me.infinity.ibl.data.client;

import me.infinity.ibl.data.IBLDiscordClient;
import org.javacord.api.DiscordApi;

public class JavacordClient extends IBLDiscordClient<DiscordApi> {

    public JavacordClient(DiscordApi discordApi) {
        super(discordApi);
    }

    @Override
    public String getId() {
        return discordApi.getYourself().getIdAsString();
    }

    @Override
    public long getGuildCount() {
        return discordApi.getServers().size();
    }

    @Override
    public int getShardCount() {
        return discordApi.getTotalShards();
    }
}
