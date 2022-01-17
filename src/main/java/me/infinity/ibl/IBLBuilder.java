package me.infinity.ibl;

import me.infinity.ibl.data.IBLDiscordClient;
import me.infinity.ibl.data.IBLResponse;
import me.infinity.ibl.data.entities.IBLBot;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class IBLBuilder implements IBL {

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
    public void postServerCount(Consumer<IBLResponse> responseConsumer) {
        postStats(false, responseConsumer);
    }

    @Override
    public void postStats(Consumer<IBLResponse> responseConsumer) {
        postStats(true, responseConsumer);
    }

    private void postStats(boolean postShards, Consumer<IBLResponse> responseConsumer) {
        RequestBody body = new FormBody.Builder()
                .add("servers", String.valueOf(discordClient.getGuildCount()))
                .add("shards", String.valueOf(postShards ? discordClient.getShardCount() : 0))
                .build();

        Request request = new Request.Builder()
                .post(body)
                .addHeader("authorization", iblToken)
                .url(BASE_URL + "bots/stats")
                .build();

        IBLCall.fetch(request, IBLResponse.class, response -> responseConsumer.accept(response.getResponse()));
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

        executor.scheduleWithFixedDelay(() -> postStats(afterResponse), 0, delay, timeUnit);
    }
}