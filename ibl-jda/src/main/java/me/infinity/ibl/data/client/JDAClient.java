package me.infinity.ibl.data.client;

import me.infinity.ibl.data.IBLDiscordClient;
import net.dv8tion.jda.api.JDA;

/**
 * Implementation of abstract class {@link IBLDiscordClient} for the JDA library
 */
public class JDAClient extends IBLDiscordClient<JDA> {

    /**
     * @param discordApi The JDA object of your bot
     */
    public JDAClient(JDA discordApi) {
        super(discordApi);
    }

    @Override
    public String getId() {
        return discordApi.getSelfUser().getId();
    }

    @Override
    public long getGuildCount() {
        return discordApi.getGuildCache().size();
    }

    @Override
    public int getShardCount() {
        return discordApi.getShardInfo().getShardTotal();
    }
}
