package me.infinity.ibl.webhooks;

import io.javalin.Javalin;
import io.javalin.http.UnauthorizedResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A Post Request Listener for your bot to listen for Votes from IBL.
 */
public class IBLWebhook {
    private static final Logger LOGGER = LoggerFactory.getLogger(IBLWebhook.class);

    private final Javalin app;
    private final String token;
    private final String iblPath;
    private final List<IBLEventListener> listeners = new ArrayList<>();

    /**
     * This makes you a webhook listener on /ibl/vote
     *
     * @param token     The Secret or Secret Auth you have put in the bot page.
     * @param listeners Vote Listeners.
     */
    public IBLWebhook(String token, IBLEventListener... listeners) {
        this(token, "/ibl/vote");
    }

    /**
     * This makes you a webhook.
     *
     * @param token     The Secret or Secret Auth you have put in the bot page.
     * @param iblPath   The path you want your listener to be.
     * @param listeners Vote Listeners.
     */
    public IBLWebhook(String token, String iblPath, IBLEventListener... listeners) {
        this.app = Javalin.create();
        this.token = token;
        this.iblPath = iblPath;
        this.listeners.addAll(Arrays.asList(listeners));
    }

    /**
     * Adds an event listener to the webhook
     *
     * @param listener Vote Listener.
     */
    public void addEventListener(IBLEventListener listener) {
        if (listener == null) throw new IllegalArgumentException("Listener cannot be null!");
        listeners.add(listener);
    }

    /**
     * Starts the application on port 8080
     */
    public void start() {
        start(8080);
    }

    /**
     * Starts the application
     *
     * @param port the port you want to have the listener on.
     */
    public void start(int port) {
        LOGGER.info("Starting Webhook ...");

        app.post(iblPath, ctx -> {
            String auth = ctx.header("Authorization");
            if (auth == null) throw new UnauthorizedResponse("Missing Authorization header!");
            if (!auth.equals(token)) throw new UnauthorizedResponse("Wrong Authorization header!");

            IBLVoteEvent voteEvent = ctx.bodyAsClass(IBLVoteEvent.class);
            listeners.forEach(it -> it.onIBLVote(voteEvent));
            ctx.status(200);
        });

        app.start(port);
        LOGGER.info("Listening to port 8080");
    }

    /**
     * The Javalin Application used for requests.
     * This can be used to set your custom config.
     *
     * @return The Javalin Instance app.
     */
    public Javalin getApp() {
        return app;
    }
}
