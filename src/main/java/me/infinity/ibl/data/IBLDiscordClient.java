package me.infinity.ibl.data;

import discord4j.core.GatewayDiscordClient;
import net.dv8tion.jda.api.JDA;
import org.javacord.api.DiscordApi;

public class IBLDiscordClient {
    private JDA jda = null;
    private GatewayDiscordClient discord4j = null;
    private DiscordApi javacord = null;

    public IBLDiscordClient(JDA jda) {
        if (jda == null) throw new IllegalArgumentException("JDA instance cannot be null");
        this.jda = jda;
    }

    public IBLDiscordClient(GatewayDiscordClient discord4j) {
        if (discord4j == null) throw new IllegalArgumentException("GatewayDiscordClient instance cannot be null");
        this.discord4j = discord4j;
    }

    public IBLDiscordClient(DiscordApi javacord) {
        if (javacord == null) throw new IllegalArgumentException("DiscordApi instance cannot be null");
        this.javacord = javacord;
    }

    public String getId() {
        if (jda != null) return jda.getSelfUser().getId();
        else if (discord4j != null) return discord4j.getSelfId().asString();
        return javacord.getYourself().getIdAsString();
    }

    public long getGuildCount() {
        if (jda != null) return jda.getGuildCache().size();
        else if (discord4j != null) return discord4j.getGuilds().count().blockOptional().orElse(0L);
        return javacord.getServers().size();
    }

    public int getShardCount() {
        if (jda != null) return jda.getShardInfo().getShardTotal();
        else if (discord4j != null) return discord4j.getGatewayClientGroup().getShardCount();
        return javacord.getTotalShards();
    }
}
