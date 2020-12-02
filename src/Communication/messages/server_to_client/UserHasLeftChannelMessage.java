package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.Channel;
import common.sharedData.UserLite;

import java.util.UUID;

/**
 * Cette classe permet de prevenir les membres d'un channel qu'un autre membre est parti
 */

public class UserHasLeftChannelMessage extends ServerToClientMessage {
    UUID channelID;
    UserLite userLite;

    public UUID getChannelID() {
        return channelID;
    }

    public void setChannel(UUID channelID) {
        this.channelID = channelID;
    }

    public UserLite getUserLite() {
        return userLite;
    }

    public void setUserLite(UserLite userLite) {
        this.userLite = userLite;
    }

    public UserHasLeftChannelMessage(UUID channelID, UserLite userLite) {
        this.channelID = channelID;
        this.userLite = userLite;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        //commClientController.notifyUserHasLeftChannel(channelID, userLite);
    }
}
