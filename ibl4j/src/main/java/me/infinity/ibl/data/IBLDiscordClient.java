package me.infinity.ibl.data;

/**
 * Abstract class used for posting stats
 *
 * @param <T> the core class of your library with which you control the bot, Example: JDA, DiscordApi
 */
public abstract class IBLDiscordClient<T> {
    protected T discordApi;

    public IBLDiscordClient(T discordApi) {
        if (discordApi == null) throw new IllegalArgumentException("Discord Api Instance cannot be null!");
        this.discordApi = discordApi;
    }

    /**
     * @return the id of the bot
     */
    public abstract String getId();

    /**
     * @return count of the servers
     */
    public abstract long getGuildCount();

    /**
     * @return count of the shards
     */
    public abstract int getShardCount();
}
