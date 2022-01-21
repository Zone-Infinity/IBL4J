package me.infinity.ibl;

import me.infinity.ibl.data.IBLDiscordClient;
import me.infinity.ibl.data.IBLResponse;
import me.infinity.ibl.data.entities.IBLBot;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.UncheckedIOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * The Builder of {@link IBL}
 */
public class IBLBuilder implements IBL {
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final IBLDiscordClient<?> discordClient;
    private final String iblToken;

    /**
     * @param discordClient {@link IBLDiscordClient} of your bot
     * @param iblToken      Infinity Bot List Token of your bot
     */
    public IBLBuilder(IBLDiscordClient<?> discordClient, String iblToken) {
        this.discordClient = discordClient;
        this.iblToken = iblToken;
    }

    @Override
    public void getStats(Consumer<IBLBot> botConsumer) {
        IBL.getBotStats(discordClient.getId(), botConsumer);
    }

    @Override
    public void postServerCount(Consumer<IBLResponse> afterTask) {
        postStats(false, afterTask);
    }

    @Override
    public void postStats(Consumer<IBLResponse> afterTask) {
        postStats(true, afterTask);
    }

    private void postStats(boolean postShards, Consumer<IBLResponse> afterTask) {
        String body = String.format("{\"servers\":%s,\"shards\":%s}",
                discordClient.getGuildCount(),
                postShards ? discordClient.getShardCount() : 0
        );
        RequestBody requestBody = RequestBody.create(body, JSON);

        Request request = new Request.Builder()
                .url(BASE_URL + "bots/stats")
                .addHeader("Content-Type", "application/json")
                .addHeader("authorization", iblToken)
                .post(requestBody)
                .build();

        IBLCall.fetch(request, IBLResponse.class, response -> afterTask.accept(response.getResponse()),
                error -> {
                    // Something wrong in my codes ;-;
                    throw new UncheckedIOException("Report to Zone_Infinity#0062 on discord.", error);
                });
    }

    @Override
    public void autoPostStats(ScheduledExecutorService executor, Consumer<IBLResponse> afterTask) {
        autoPostStats(executor, 5, TimeUnit.MINUTES, afterTask);
    }

    @Override
    public void autoPostStats(ScheduledExecutorService executor, long delay, Consumer<IBLResponse> afterTask) {
        autoPostStats(executor, delay, TimeUnit.MILLISECONDS, afterTask);
    }

    @Override
    public void autoPostStats(ScheduledExecutorService executor, long delay, TimeUnit timeUnit, Consumer<IBLResponse> afterTask) {
        long delayInMillis = timeUnit.toMillis(delay);
        if (delayInMillis < 100000) { // 100K milliseconds = 5 minutes / 3 requests
            throw new IllegalArgumentException("Delay should be more than 100000 milliseconds for handling rate limits,\n" +
                    "Your delay in millisecond : " + delayInMillis);
        }

        executor.scheduleWithFixedDelay(() -> postStats(afterTask), 0, delay, timeUnit);
    }
}