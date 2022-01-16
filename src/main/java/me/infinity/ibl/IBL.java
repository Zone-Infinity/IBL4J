package me.infinity.ibl;

import me.infinity.ibl.data.IBLResponse;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Client for posting stats (servers and shards)
 */
public interface IBL {

    /**
     * Only Posts Server Count to Infinity Bot List
     *
     * @return Response by IBL
     */
    IBLResponse postServerCount();

    /**
     * Posts Stats (Server Count and Shard Count both) to Infinity Bot List
     *
     * @return Response by IBL
     */
    IBLResponse postStats();

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
}
