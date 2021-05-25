package me.infinity.ibl;

import net.dv8tion.jda.api.JDA;

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
        private final JDA bot;
        private final ScheduledExecutorService executor;

        /**
         * IBL Builder
         *
         * @param bot      JDA Instance of the bot
         * @param iblToken Infinity Bot List Token of your bot
         */
        public Builder(JDA bot, String iblToken) {
            super(bot.getSelfUser().getId(), iblToken);
            this.bot = bot;
            this.executor = Executors.newSingleThreadScheduledExecutor();
        }

        /**
         * @param bot      JDA Instance of the bot
         * @param iblToken Infinity Bot List Token of your bot
         * @param executor ScheduledExecutorService for auto posting stats
         */
        public Builder(JDA bot, String iblToken, ScheduledExecutorService executor) {
            super(bot.getSelfUser().getId(), iblToken);
            this.bot = bot;
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
                final long servers = bot.getGuildCache().size();
                final int shards = bot.getShardInfo().getShardTotal();
                postStats(servers, shards);
            }, 10000, delay, timeUnit);
        }
    }
}
