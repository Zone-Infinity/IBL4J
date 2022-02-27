package me.infinity.ibl.webhooks;

import io.javalin.Javalin;
import io.javalin.http.UnauthorizedResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IBLWebhook {
    private static final Logger LOGGER = LoggerFactory.getLogger(IBLWebhook.class);

    private final Javalin app;
    private final String token;
    private final String iblPath;
    private final List<IBLEventListener> listeners = new ArrayList<>();

    public IBLWebhook(String token, IBLEventListener... listeners) {
        this(token, "/ibl/vote");
    }

    public IBLWebhook(String token, String iblPath, IBLEventListener... listeners) {
        this.app = Javalin.create();
        this.token = token;
        this.iblPath = iblPath;
        this.listeners.addAll(Arrays.asList(listeners));
    }

    public void addEventListener(IBLEventListener listener) {
        if (listener == null) throw new IllegalArgumentException("Listener cannot be null!");
        listeners.add(listener);
    }

    public void start() {
        LOGGER.info("Starting Webhook ...");

        app.post(iblPath, ctx -> {
            String auth = ctx.header("Authorization");
            if (auth == null) throw new UnauthorizedResponse("Missing Authorization header!");
            if (!auth.equals(token)) throw new UnauthorizedResponse("Wrong Authorization header!");

            IBLVoteEvent voteEvent = ctx.bodyAsClass(IBLVoteEvent.class);
            listeners.forEach(it -> it.onIBLVote(voteEvent));
            ctx.status(200);
        });

        app.start(8080);
        LOGGER.info("Listening to port 8080");
    }
}
