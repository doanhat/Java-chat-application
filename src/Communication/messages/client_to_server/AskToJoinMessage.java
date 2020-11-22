package Communication.messages.client_to_server;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.Message;
import common.sharedData.UserLite;

import java.util.List;
import java.util.UUID;

public class AskToJoinMessage extends ClientToServerMessage {

    private final UserLite sender;
    private final UUID channelID;

    public AskToJoinMessage(UUID channelID, UserLite requester) {
        this.sender = requester;
        this.channelID = channelID;
    }

    @Override
    protected void handle(CommunicationServerController commClientController) {
        Channel channel = commClientController.getChannel(channelID);

        if (channel != null && commClientController.requestJoinChannel(channel, sender))
        {
            // TODO send Acceptation back to sender

            // TODO Notify other user new User has joined channel
        }

    }
}
