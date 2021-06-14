package me.infinity.ibl;

import discord4j.core.GatewayDiscordClient;
import me.infinity.ibl.data.IBLDiscordClient;
import net.dv8tion.jda.api.JDA;
import org.javacord.api.DiscordApi;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Client for both posting and auto posting stats (servers and shards)
 * <p>
 * Build IBL Instance using Builder
 *
 * @see Builder
 */
public interface IBLClient extends IBL {
    /**
     * Auto Posts Stats to IBL every 1 Hour
     *
     * @see IBLClient#autoPostStats(long)
     * @see IBLClient#autoPostStats(long, TimeUnit)
     */
    void autoPostStats();

    /**
     * Auto Posts Stats to IBL very `delay` milliseconds
     *
     * @param delay delay in milliseconds
     * @see IBLClient#autoPostStats()
     * @see IBLClient#autoPostStats(long, TimeUnit)
     */
    void autoPostStats(long delay);

    /**
     * Auto Posts Stats to IBL
     *
     * @param delay    delay for posting stats
     * @param timeUnit TimeUnit for delay
     * @see IBLClient#autoPostStats()
     * @see IBLClient#autoPostStats(long)
     */
    void autoPostStats(long delay, TimeUnit timeUnit);

    /**
     * IBLClient Builder
     *
     * @see IBLClient
     */
    class Builder extends IBL.Builder implements IBLClient {
        private final IBLDiscordClient bot;
        private final ScheduledExecutorService executor;

        /**
         * @param client   IBLDiscordClient Instance of the bot
         * @param iblToken Infinity Bot List Token of your bot
         * @see IBLDiscordClient
         */
        public Builder(IBLDiscordClient client, String iblToken) {
            super(client.getId(), iblToken);
            this.bot = client;
            this.executor = Executors.newSingleThreadScheduledExecutor();
        }

        /**
         * @param client   IBLDiscordClient Instance of the bot
         * @param iblToken Infinity Bot List Token of your bot
         * @see IBLDiscordClient
         */
        public Builder(IBLDiscordClient client, String iblToken, ScheduledExecutorService executor) {
            super(client.getId(), iblToken);
            this.bot = client;
            this.executor = executor;
        }

        /**
         * JDA Client Builder
         *
         * @param bot      JDA Instance of the bot
         * @param iblToken Infinity Bot List Token of your bot
         */
        public Builder(JDA bot, String iblToken) {
            super(bot.getSelfUser().getId(), iblToken);
            this.bot = new IBLDiscordClient(bot);
            this.executor = Executors.newSingleThreadScheduledExecutor();
        }

        /**
         * JDA Client Builder
         *
         * @param bot      JDA Instance of the bot
         * @param iblToken Infinity Bot List Token of your bot
         * @param executor ScheduledExecutorService for auto posting stats
         */
        public Builder(JDA bot, String iblToken, ScheduledExecutorService executor) {
            super(bot.getSelfUser().getId(), iblToken);
            this.bot = new IBLDiscordClient(bot);
            this.executor = executor;
        }

        /**
         * Discord4J Client Builder
         *
         * @param bot      GatewayDiscordClient Instance of the bot
         * @param iblToken Infinity Bot List Token of your bot
         */
        public Builder(GatewayDiscordClient bot, String iblToken) {
            super(bot.getSelfId().asString(), iblToken);
            this.bot = new IBLDiscordClient(bot);
            this.executor = Executors.newSingleThreadScheduledExecutor();
        }

        /**
         * Discord4J Client Builder
         *
         * @param bot      GatewayDiscordClient Instance of the bot
         * @param iblToken Infinity Bot List Token of your bot
         * @param executor ScheduledExecutorService for auto posting stats
         */
        public Builder(GatewayDiscordClient bot, String iblToken, ScheduledExecutorService executor) {
            super(bot.getSelfId().asString(), iblToken);
            this.bot = new IBLDiscordClient(bot);
            this.executor = executor;
        }

        /**
         * Javacord Client Builder
         *
         * @param bot      DiscordApi Instance of the bot
         * @param iblToken Infinity Bot List Token of your bot
         */
        public Builder(DiscordApi bot, String iblToken) {
            super(bot.getClientId(), iblToken);
            this.bot = new IBLDiscordClient(bot);
            this.executor = Executors.newSingleThreadScheduledExecutor();
        }

        /**
         * Javacord Client Builder
         *
         * @param bot      DiscordApi Instance of the bot
         * @param iblToken Infinity Bot List Token of your bot
         * @param executor ScheduledExecutorService for auto posting stats
         */
        public Builder(DiscordApi bot, String iblToken, ScheduledExecutorService executor) {
            super(bot.getYourself().getIdAsString(), iblToken);
            this.bot = new IBLDiscordClient(bot);
            this.executor = executor;
        }

        public void autoPostStats() {
            autoPostStats(60 * 60 * 1000);
        }

        public void autoPostStats(long delay) {
            autoPostStats(delay, TimeUnit.MILLISECONDS);
        }

        public void autoPostStats(long delay, TimeUnit timeUnit) {
            executor.scheduleWithFixedDelay(() -> {
                final long servers = bot.getGuildCount();
                final int shards = bot.getShardCount();
                postStats(servers, shards);
            }, 0, delay, timeUnit);
        }
    }
}
