package me.infinity.ibl;

import net.dv8tion.jda.api.JDA;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
public interface IBL {
    // Base url of IBL api
    String baseUrl = "https://api.infinitybotlist.com/";

    /**
     * Post Stats to Infinity Bol List
     *
     * @param serverCount number of servers your bot is in
     */
    void postStats(long serverCount);

    /**
     * Post Stats to Infinity Bol List
     *
     * @param serverCount number of servers your bot is in
     * @param shardCount  number of shards of your bot
     */
    void postStats(long serverCount, int shardCount);

    class Builder implements IBL {
        private final String botId;
        private final String iblToken;
        private final OkHttpClient client;
        private final Logger LOGGER = LoggerFactory.getLogger(IBL.class);

        /**
         * IBL Builder
         *
         * @param botId    Id of your Bot
         * @param iblToken Infinity Bot List Token of your bot
         */
        public Builder(String botId, String iblToken) {
            this.botId = botId;
            this.iblToken = iblToken;
            this.client = new OkHttpClient();
        }

        /**
         * IBL Builder
         *
         * @param botId    Id of your Bot
         * @param iblToken Infinity Bot List Token of your bot
         */
        public Builder(long botId, String iblToken) {
            this(String.valueOf(botId), iblToken);
        }

        @Override
        public void postStats(long serverCount) {
            postStats(serverCount, 1);
        }

        @Override
        public void postStats(long serverCount, int shardCount) {
            try {
                RequestBody body = new FormBody.Builder()
                        .add("servers", String.valueOf(serverCount))
                        .add("shards", String.valueOf(shardCount))
                        .build();

                final Request request = new Request.Builder()
                        .post(body)
                        .addHeader("authorization", iblToken)
                        .url(baseUrl + "bot/" + botId)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    if (response.isSuccessful()) {
                        LOGGER.info("Posted Server Count");
                    } else if (response.code() == 401) {
                        LOGGER.error("Unauthorized : Please make sure you have put the correct token");
                    } else if (response.code() == 429) {
                        LOGGER.error("Rate Limit : {}", response.message());
                    } else {
                        LOGGER.error("Could not post Server Stats : {}", response.message());
                    }
                }
            } catch (IOException ex) {
                LOGGER.error("Could not post Server Stats", ex);
            }
        }
    }

    class Client extends Builder {
        private final JDA bot;
        private final ScheduledExecutorService executor;

        /**
         * IBL Builder
         *
         * @param bot      JDA Instance of the bot
         * @param iblToken Infinity Bot List Token of your bot
         */
        public Client(JDA bot, String iblToken) {
            super(bot.getSelfUser().getId(), iblToken);
            this.bot = bot;
            this.executor = Executors.newSingleThreadScheduledExecutor();
        }

        /**
         * IBL Builder
         *
         * @param bot      JDA Instance of the bot
         * @param iblToken Infinity Bot List Token of your bot
         * @param executor ScheduledExecutorService for auto posting stats
         */
        public Client(JDA bot, String iblToken, ScheduledExecutorService executor) {
            super(bot.getSelfUser().getId(), iblToken);
            this.bot = bot;
            this.executor = executor;
        }

        /**
         * Auto Posts Stats to IBL every 1 Hour
         *
         * @see Client#autoPostStats(long)
         * @see Client#autoPostStats(long, TimeUnit)
         */
        public void autoPostStats() {
            autoPostStats(60 * 60 * 1000);
        }

        /**
         * Auto Posts Stats to IBL very `delay` milliseconds
         *
         * @param delay delay in milliseconds
         * @see Client#autoPostStats()
         * @see Client#autoPostStats(long, TimeUnit)
         */
        public void autoPostStats(long delay) {
            autoPostStats(delay, TimeUnit.MILLISECONDS);
        }

        /**
         * Auto Posts Stats to IBL
         *
         * @param delay    delay for posting stats
         * @param timeUnit TimeUnit for delay
         * @see Client#autoPostStats()
         * @see Client#autoPostStats(long)
         */
        public void autoPostStats(long delay, TimeUnit timeUnit) {
            executor.scheduleWithFixedDelay(() -> {
                final long servers = bot.getGuildCache().size();
                final int shards = bot.getShardInfo().getShardTotal();
                postStats(servers, shards);
            }, 10000, delay, timeUnit);
        }
    }
}
