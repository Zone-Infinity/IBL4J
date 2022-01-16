package me.infinity.ibl;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.infinity.ibl.data.IBLDiscordClient;
import me.infinity.ibl.data.IBLResponse;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@SuppressWarnings("ConstantConditions")
public class IBLBuilder implements IBL {
    private final String BASE_URL = "https://api.infinitybotlist.com/";

    private final IBLDiscordClient<?> discordClient;
    private final String iblToken;
    private final OkHttpClient client;
    private final ObjectMapper mapper;

    /**
     * @param discordClient {@link IBLDiscordClient} of your bot
     * @param iblToken      Infinity Bot List Token of your bot
     */
    public IBLBuilder(IBLDiscordClient<?> discordClient, String iblToken) {
        this.discordClient = discordClient;
        this.iblToken = iblToken;
        this.client = new OkHttpClient();
        this.mapper = new ObjectMapper();
    }

    @Override
    public IBLResponse postServerCount() {
        return postStats(false);
    }

    @Override
    public IBLResponse postStats() {
        return postStats(true);
    }

    private IBLResponse postStats(boolean postShards) {
        try {
            RequestBody body = new FormBody.Builder()
                    .add("servers", String.valueOf(discordClient.getGuildCount()))
                    .add("shards", String.valueOf(postShards ? discordClient.getShardCount() : 0))
                    .build();

            Request request = new Request.Builder()
                    .post(body)
                    .addHeader("authorization", iblToken)
                    .url(BASE_URL + "bots/stats")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                return mapper.readValue(response.body().string(), IBLResponse.class);
            }
        } catch (IOException ex) {
            return new IBLResponse(ex.getMessage(), true, 500);
        }
    }

    @Override
    public void autoPostStats(ScheduledExecutorService executor, Consumer<IBLResponse> afterResponse) {
        autoPostStats(executor, 5, TimeUnit.MINUTES, afterResponse);
    }

    @Override
    public void autoPostStats(ScheduledExecutorService executor, long delay, Consumer<IBLResponse> afterResponse) {
        autoPostStats(executor, delay, TimeUnit.MILLISECONDS, afterResponse);
    }

    @Override
    public void autoPostStats(ScheduledExecutorService executor, long delay, TimeUnit timeUnit, Consumer<IBLResponse> afterResponse) {
        long delayInMillis = timeUnit.toMillis(delay);
        if (delayInMillis < 100000) { // 100K milliseconds = 5 minutes / 3 requests
            throw new IllegalArgumentException("Delay should be more than 100000 milliseconds for handling rate limits,\n" +
                    "Your delay in millisecond : " + delayInMillis);
        }

        executor.scheduleWithFixedDelay(() -> afterResponse.accept(postStats()), 0, delay, timeUnit);
    }
}