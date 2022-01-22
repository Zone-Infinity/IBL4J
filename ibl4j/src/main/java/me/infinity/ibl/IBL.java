package me.infinity.ibl;

import me.infinity.ibl.data.IBLResponse;
import me.infinity.ibl.data.entities.IBLBot;
import me.infinity.ibl.data.entities.IBLUser;
import okhttp3.Request;

import java.io.UncheckedIOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * The core of IBL. This will help you to post the stats (servers and shards) to IBL.
 */
public interface IBL {
    String BASE_URL = "https://api.infinitybotlist.com/";

    /**
     * This method fetches your bots information
     *
     * @param botConsumer the callback with your bot information
     */
    void getStats(Consumer<IBLBot> botConsumer);

    /**
     * Only Posts Server Count to Infinity Bot List
     *
     * @param afterTask the task with {@link IBLResponse}, you want to do after the posting is done
     */
    void postServerCount(Consumer<IBLResponse> afterTask);

    /**
     * Posts Stats (Server Count and Shard Count both) to Infinity Bot List
     *
     * @param afterTask the task with {@link IBLResponse}, you want to do after the posting is done
     */
    void postStats(Consumer<IBLResponse> afterTask);

    /**
     * Auto Posts Stats to IBL every 5 minutes
     *
     * @param executor  The executor which will run these tasks
     * @param afterTask the task with {@link IBLResponse}, you want to do after the posting is done
     */
    void autoPostStats(ScheduledExecutorService executor, Consumer<IBLResponse> afterTask);

    /**
     * Auto Posts Stats to IBL every `delay` milliseconds
     *
     * @param executor  The executor which will run these tasks
     * @param delay     the delay in milliseconds
     * @param afterTask the task with {@link IBLResponse}, you want to do after the posting is done
     */
    void autoPostStats(ScheduledExecutorService executor, long delay, Consumer<IBLResponse> afterTask);

    /**
     * Auto Posts Stats to IBL
     *
     * @param executor  The executor which will run these tasks
     * @param delay     the delay in timeunit you will specify
     * @param timeUnit  the TimeUnit of the delay
     * @param afterTask the task with {@link IBLResponse}, you want to do after the posting is done
     */
    void autoPostStats(ScheduledExecutorService executor, long delay, TimeUnit timeUnit, Consumer<IBLResponse> afterTask);

    /**
     * This method fetches any bots information
     *
     * @param botID       the id of bot which you want to fetch
     * @param botConsumer callback with {@link IBLBot} if it exists or callback with a null object
     */
    static void getBotStats(String botID, Consumer<IBLBot> botConsumer) {
        Request request = new Request.Builder()
                .get()
                .url(BASE_URL + "bots/" + botID)
                .build();

        IBLCall.fetch(request, IBLBot.class, response -> {
            IBLBot bot = response.getResponse();
            if (response.getCode() != 200) bot.doesNotExist();

            botConsumer.accept(bot);
        }, error -> {
            // Something wrong in my codes ;-;
            throw new UncheckedIOException("Report to Zone_Infinity#0062 on discord.", error);
        });
    }

    /**
     * This method fetches a users information
     *
     * @param userID       the id of user which you want to fetch
     * @param userConsumer callback with {@link IBLUser} if it exists or callback with a null object
     */
    static void getUserInfo(String userID, Consumer<IBLUser> userConsumer) {
        Request request = new Request.Builder()
                .get()
                .url(BASE_URL + "user/" + userID)
                .build();

        IBLCall.fetch(request, IBLUser.class, response -> {
            IBLUser user = response.getResponse();
            if (response.getCode() != 200) user.doesNotExist();

            userConsumer.accept(user);
        }, error -> {
            // Something wrong in my codes ;-;
            throw new UncheckedIOException("Report to Zone_Infinity#0062 on discord.", error);
        });
    }
}
