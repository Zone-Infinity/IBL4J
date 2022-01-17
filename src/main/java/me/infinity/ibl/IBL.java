package me.infinity.ibl;

import me.infinity.ibl.data.IBLResponse;
import me.infinity.ibl.data.entities.IBLBot;
import me.infinity.ibl.data.entities.IBLUser;
import okhttp3.Request;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Client for posting stats (servers and shards)
 */
public interface IBL {
    String BASE_URL = "https://api.infinitybotlist.com/";

    void getStats(Consumer<IBLBot> botConsumer);

    /**
     * Only Posts Server Count to Infinity Bot List
     *
     */
    void postServerCount(Consumer<IBLResponse> responseConsumer);

    /**
     * Posts Stats (Server Count and Shard Count both) to Infinity Bot List
     *
     */
    void postStats(Consumer<IBLResponse> responseConsumer);

    /**
     * Auto Posts Stats to IBL every 5 minutes
     */
    void autoPostStats(ScheduledExecutorService executor, Consumer<IBLResponse> afterResponse);

    /**
     * Auto Posts Stats to IBL very `delay` milliseconds
     *
     * @param delay delay in milliseconds
     */
    void autoPostStats(ScheduledExecutorService executor, long delay, Consumer<IBLResponse> afterResponse);

    /**
     * Auto Posts Stats to IBL
     *
     * @param delay    delay for posting stats
     * @param timeUnit TimeUnit for delay
     */
    void autoPostStats(ScheduledExecutorService executor, long delay, TimeUnit timeUnit, Consumer<IBLResponse> afterResponse);

    static void getBotStats(String botID, Consumer<IBLBot> botConsumer) {
        Request request = new Request.Builder()
                .get()
                .url(BASE_URL + "bots/" + botID)
                .build();

        IBLCall.fetch(request, IBLBot.class, response -> {
            IBLBot bot = response.getResponse();
            if (response.getCode() != 200) bot.doesNotExist();

            botConsumer.accept(bot);
        });
    }

    static void getUserInfo(String userID, Consumer<IBLUser> userConsumer) {
        Request request = new Request.Builder()
                .get()
                .url(BASE_URL + "user/" + userID)
                .build();

        IBLCall.fetch(request, IBLUser.class, response -> {
            IBLUser user = response.getResponse();
            if (response.getCode() != 200) user.doesNotExist();

            userConsumer.accept(user);
        });
    }
}
