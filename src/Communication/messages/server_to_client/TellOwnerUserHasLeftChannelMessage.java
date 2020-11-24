package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.Channel;
import common.sharedData.UserLite;

public class TellOwnerUserHasLeftChannelMessage extends ServerToClientMessage {
    UserLite userLite;
    Channel channel;

    public TellOwnerUserHasLeftChannelMessage(Channel channel, UserLite userLite) {
        this.userLite = userLite;
        this.channel = channel;
    }

    public UserLite getUserLite() {
        return userLite;
    }

    public void setUserLite(UserLite userLite) {
        this.userLite = userLite;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.notifyUserHasLeftChannel(channel, userLite);
    }
}
