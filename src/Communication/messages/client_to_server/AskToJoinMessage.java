package Communication.messages.client_to_server;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.abstracts.NetworkMessage;
import Communication.messages.server_to_client.AcceptJoinChannelMessage;
import Communication.messages.server_to_client.NewUserJoinChannelMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.UserLite;

import java.util.UUID;

public class AskToJoinMessage extends ClientToServerMessage {

    private final UserLite sender;
    private final UUID channelID;

    public AskToJoinMessage(UUID channelID, UserLite requester) {
        this.sender = requester;
        this.channelID = channelID;
    }

    @Override
    protected void handle(CommunicationServerController commServerController) {
        Channel channel = commServerController.getChannel(channelID);

        if (channel != null && commServerController.requestJoinChannel(channel, sender))
        {
            // send Acceptation back to sender
            commServerController.sendMessage(sender.getId(), new AcceptJoinChannelMessage(sender, channelID));

            // notify other user new User has joined channel
            NetworkMessage notification = new NewUserJoinChannelMessage(sender, channelID);

            for (UserLite user: channel.getAcceptedPersons()) {
                commServerController.sendMessage(user.getId(), notification);
            }
        }
    }
}
