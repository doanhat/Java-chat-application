package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.shared_data.Channel;

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
