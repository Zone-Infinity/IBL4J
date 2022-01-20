package me.infinity.ibl.data;

public abstract class IBLDiscordClient<T> {
    protected T discordApi;

    public IBLDiscordClient(T discordApi) {
        if (discordApi == null) throw new IllegalArgumentException("Discord Api Instance cannot be null!");
        this.discordApi = discordApi;
    }

    public abstract String getId();

    public abstract long getGuildCount();

    public abstract int getShardCount();
}
