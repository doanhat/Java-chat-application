package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.Channel;
import common.sharedData.UserLite;
import common.sharedData.Visibility;

import java.io.Serializable;
import java.util.List;

public class NewVisibleChannelMessage extends ServerToClientMessage {

    private final Channel channel;

    public NewVisibleChannelMessage(Channel channel){
        this.channel = channel;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.notifyVisibleChannel(channel);
    }
}
