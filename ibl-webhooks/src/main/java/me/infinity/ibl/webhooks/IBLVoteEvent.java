package me.infinity.ibl.webhooks;

/**
 * {
 * type: 'VOTE',
 * userID: '510065483693817867',
 * userName: 'Toxic Dev',
 * botID: '868984038151569438',
 * count: '10000',
 * timeStamp: "1645781773038"
 * }
 */

public class IBLVoteEvent {
    private final Type type;
    private final String botID;
    private final String userID;
    private final String username;
    private final int voteCount;
    private final long timeStamp;

    public IBLVoteEvent(Type type, String botID, String userID, String username, int voteCount, long timestamp) {
        this.type = type;
        this.botID = botID;
        this.userID = userID;
        this.username = username;
        this.voteCount = voteCount;
        this.timeStamp = timestamp;
    }

    public Type getType() {
        return type;
    }

    public String getBotID() {
        return botID;
    }

    public String getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public enum Type {
        VOTE, TEST, UNKNOWN
    }
}
