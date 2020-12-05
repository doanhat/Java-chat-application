package Communication.messages.client_to_server.proprietary_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.AcceptJoinChannelMessage;
import Communication.messages.server_to_client.NewUserJoinChannelMessage;
import Communication.messages.server_to_client.RefuseJoinChannelMessage;
import Communication.server.CommunicationServerController;
import Communication.messages.server_to_client.TellOwnerUserJoinedMessage;
import common.sharedData.Channel;
import common.sharedData.UserLite;

import java.util.List;
import java.util.UUID;

public class AskToJoinPropMessage extends ClientToServerMessage {
    private static final long serialVersionUID = -1923313673314704993L;
    private final UserLite sender;
    private final UserLite channelOwner;
    private final UUID channelID;

    public AskToJoinPropMessage(UUID channelID, UserLite requester, UserLite owner) {
        this.sender = requester;
        this.channelID = channelID;
        this.channelOwner = owner;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        commController.sendMessage(channelOwner.getId(), new TellOwnerUserJoinedMessage(sender, channelID));
    }
}
