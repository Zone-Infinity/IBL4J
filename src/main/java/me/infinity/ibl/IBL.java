package me.infinity.ibl;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public interface IBL {
    // Base url of IBL api
    String baseUrl = "https://api.infinitybotlist.com/";

    /**
     * Post Stats to Infinity Bol List
     *
     * @param serverCount number of servers your bot is in
     * @see IBL#postStats(long, int)
     */
    void postStats(long serverCount);

    /**
     * Post Stats to Infinity Bol List
     *
     * @param serverCount number of servers your bot is in
     * @param shardCount  number of shards of your bot
     * @see IBL#postStats(long)
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
            postStats(serverCount, 0);
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
                        .addHeader("Authorization", iblToken)
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
}
