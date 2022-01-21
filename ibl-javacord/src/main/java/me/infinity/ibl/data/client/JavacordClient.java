package me.infinity.ibl.data.client;

import me.infinity.ibl.data.IBLDiscordClient;
import org.javacord.api.DiscordApi;

/**
 * Implementation of abstract class {@link IBLDiscordClient} for the Javacord library
 */
public class JavacordClient extends IBLDiscordClient<DiscordApi> {

    /**
     * @param discordApi The DiscordApi object of your bot
     */
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
