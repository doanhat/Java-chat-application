package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.UserLite;

import java.util.UUID;

public class AcceptJoinChannelMessage extends ServerToClientMessage {

    private final UserLite user;
    private final UUID channelID;

    public AcceptJoinChannelMessage(UserLite receiver, UUID channelID) {
        this.user = receiver;
        this.channelID = channelID;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.notifyAcceptedToJoinChannel(user, channelID);
    }
}
