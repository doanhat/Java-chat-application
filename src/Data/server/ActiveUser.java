package Data.server;

import java.util.UUID;

public class ActiveUser {
    private UUID channelID;
    private UUID userID;

    public ActiveUser(UUID channelID, UUID userID) {
        this.channelID = channelID;
        this.userID = userID;
    }

    public UUID getChannelID() {
        return channelID;
    }

    public void setChannelID(UUID channelID) {
        this.channelID = channelID;
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }
}
