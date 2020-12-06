package Communication.messages.client_to_server.shared_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.AcceptJoinChannelMessage;
import Communication.messages.server_to_client.NewUserJoinChannelMessage;
import Communication.messages.server_to_client.RefuseJoinChannelMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.UserLite;

import java.util.List;
import java.util.UUID;

/**
 * Message envoyé par le client lorsque le client demande à rejoindre un channel particulier.
 */
public class AskToJoinSharedMessage extends ClientToServerMessage {

    private static final long serialVersionUID = -1923313673314704993L;
    private final UserLite sender;
    private final UUID channelID;

    public AskToJoinSharedMessage(UUID channelID, UserLite requester) {
        this.sender = requester;
        this.channelID = channelID;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        Channel channel = commController.getChannel(channelID);
        commController.requestJoinChannel(channel, sender);
        List<UserLite> users = commController.getChannelConnectedUserList(channelID); //Obtient la liste des utilisateurs connectes

        if (channel != null)
        {
            // send Acceptation back to sender
            commController.sendMessage(sender.getId(), new AcceptJoinChannelMessage(sender, channel));

            // Notifie les utilisateurs connectes au channel qu'un nouveau utilisateur les rejoins
            commController.sendMulticast(users,
                    new NewUserJoinChannelMessage(sender, channelID));
        }
        else {
            // send Refusal back to sender
            commController.sendMessage(sender.getId(), new RefuseJoinChannelMessage(sender, channelID));
        }
    }
}
