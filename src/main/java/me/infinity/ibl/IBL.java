package me.infinity.ibl;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Client for posting stats (servers and shards) once
 * <p>
 * Build IBL Instance using Builder
 *
 * @see Builder
 */
public interface IBL {
    /**
     * Base url of IBL api
     */
    String baseUrl = "https://api.infinitybotlist.com/";

    /**
     * Post Stats to Infinity Bol List
     *
     * @param serverCount number of servers your bot is in
     * @return Response by IBL
     * @see IBL#postStats(long, int)
     * @see IBLResponse
     */
    IBLResponse postStats(long serverCount);

    /**
     * Post Stats to Infinity Bol List
     *
     * @param serverCount number of servers your bot is in
     * @param shardCount  number of shards of your bot
     * @return Response by IBL
     * @see IBL#postStats(long)
     * @see IBLResponse
     */
    IBLResponse postStats(long serverCount, int shardCount);

    /**
     * IBL Builder
     *
     * @see IBL
     */
    class Builder implements IBL {
        private final String botId;
        private final String iblToken;
        private final OkHttpClient client;
        private final ObjectMapper mapper;
        private final Logger LOGGER = LoggerFactory.getLogger(IBL.class);

        /**
         * @param botId    Id of your Bot
         * @param iblToken Infinity Bot List Token of your bot
         */
        public Builder(String botId, String iblToken) {
            this.botId = botId;
            this.iblToken = iblToken;
            this.client = new OkHttpClient();
            mapper = new ObjectMapper();
        }

        /**
         * @param botId    Id of your Bot
         * @param iblToken Infinity Bot List Token of your bot
         */
        public Builder(long botId, String iblToken) {
            this(String.valueOf(botId), iblToken);
        }

        @Override
        public IBLResponse postStats(long serverCount) {
            return postStats(serverCount, 0);
        }

        @SuppressWarnings("ConstantConditions")
        @Override
        public IBLResponse postStats(long serverCount, int shardCount) {
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
                    final IBLResponse res = mapper.readValue(response.body().string(), IBLResponse.class);
                    final int code = res.getCode();

                    if (response.isSuccessful())
                        LOGGER.info("Posted Server Count to IBL");
                    else if (code == 401 || code == 404 || code == 429 || code == 500)
                        LOGGER.error(res.getMessage());
                    else
                        LOGGER.error("Something went wrong : Could not post Server Stats : {}", res.getMessage());
                    return res;
                }
            } catch (IOException ex) {
                LOGGER.error("Could not post Server Stats", ex);
                return new IBLResponse(ex.getMessage(), 500, true);
            }
        }
    }
}
