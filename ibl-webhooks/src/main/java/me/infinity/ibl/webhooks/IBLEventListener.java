package me.infinity.ibl.webhooks;

/**
 * The Event Listener.
 * <p>
 * Register Listeners using the {@link IBLWebhook#addEventListener} or the {@link IBLWebhook} Constructor.
 */
public interface IBLEventListener {

    /**
     * Handles the Vote Event.
     * A Vote Event is triggered when a user votes you bot.
     *
     * @param event the Vote Event stored in {@link IBLVoteEvent}
     */
    void onIBLVote(IBLVoteEvent event);
}
