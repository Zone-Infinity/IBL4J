package me.infinity.ibl.webhooks;

import java.time.Instant;

/**
 * A Dataclass for storing the Vote Event from IBL.
 */
public class IBLVoteEvent {
    private final Type type;
    private final String botID;
    private final String userID;
    private final String userName;
    private final int count;
    private final long timeStamp;

    public IBLVoteEvent(Type type, String botID, String userID, String userName, int count, long timestamp) {
        this.type = type;
        this.botID = botID;
        this.userID = userID;
        this.userName = userName;
        this.count = count;
        this.timeStamp = timestamp;
    }

    /**
     * The {@link Type} of Request.
     * - {@link Type#VOTE} : When a user votes for you bot.
     * - {@link Type#TEST} : When you test the webhook using the test button.
     *
     * @return a {@link Type}
     */
    public Type getType() {
        return type;
    }

    /**
     * The Discord ID (Snowflake) for the Bot who Received a Vote.
     *
     * @return bot's id in string
     */
    public String getBotID() {
        return botID;
    }

    /**
     * The Discord ID (Snowflake) of the User who Voted.
     *
     * @return user's id in string
     */
    public String getUserID() {
        return userID;
    }

    /**
     * The Username of the User who Voted.
     *
     * @return username in string
     */
    public String getUserName() {
        return userName;
    }

    /**
     * The Bots new Vote Count.
     *
     * @return the bots new vote count in integer
     */
    public int getCount() {
        return count;
    }

    /**
     * The Date and Time of the Vote.
     * Use {@link Instant#ofEpochMilli} to convert the time.
     *
     * @return the vote time in milliseconds
     */
    public long getTimeStamp() {
        return timeStamp;
    }

    /**
     * The Type of the Request IBL has sent.
     */
    public enum Type {
        VOTE, TEST
    }
}
