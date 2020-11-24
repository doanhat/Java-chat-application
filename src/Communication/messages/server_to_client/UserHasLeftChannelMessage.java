package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.Channel;
import common.sharedData.UserLite;

public class UserHasLeftChannelMessage extends ServerToClientMessage {
    Channel channel;
    UserLite userLite;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public UserLite getUserLite() {
        return userLite;
    }

    public void setUserLite(UserLite userLite) {
        this.userLite = userLite;
    }

    public UserHasLeftChannelMessage(Channel channel, UserLite userLite) {
        this.channel = channel;
        this.userLite = userLite;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.notifyUserHasLeftChannel(channel, userLite);
    }
}
