package Communication.messages.client_to_server;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.UserLite;

public class CreateChannelMessage extends ClientToServerMessage {

    private UserLite sender;
    private Channel channel;
    private boolean proprietaryChannel;
    private boolean publicChannel;

    // TODO: verify with DATA to agree on the organization of data structure
    public CreateChannelMessage(UserLite sender,
                                Channel channel,
                                boolean proprietary,
                                boolean publicChannel) {
        this.sender = sender;
        this.channel = channel;
        this.proprietaryChannel = proprietary;
        this.publicChannel = publicChannel;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        commController.requestCreateChannel(channel, proprietaryChannel, publicChannel, sender);
    }
}
