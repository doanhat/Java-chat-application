package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.Channel;

import java.io.Serializable;

public class NewVisibleChannelMessage extends ServerToClientMessage {
    private Channel channel;

    public NewVisibleChannelMessage(Channel channel){
        this.channel = channel;
    }

    public Channel getChannel() {
        return this.channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.notifyVisibleChannel(this.channel);
    }
}
