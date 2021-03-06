package communication.messages.client_to_server.channel_modification;

import communication.messages.abstracts.ClientToServerMessage;
import communication.messages.server_to_client.channel_modification.NewInvisibleChannelsMessage;
import communication.server.CommunicationServerController;
import common.shared_data.Channel;
import common.shared_data.UserLite;
import common.shared_data.Visibility;

import java.util.UUID;

public class DeleteChannelMessage extends ClientToServerMessage {

    private static final long     serialVersionUID = -22469207475665L;
    private final        UUID     channelID;
    private final        UserLite requester;

    /**
     * Message vers le serveur annoncant la suppression des channels
     * @param channelID Channel supprimé
     * @param requester Utilisateur l'ayant supprime
     */
    public DeleteChannelMessage(UUID channelID, UserLite requester) {
        this.channelID = channelID;
        this.requester = requester;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        Channel channel = commController.getChannel(channelID);

        if (channel == null) {
            return;
        }

        if (commController.requestDeleteChannel(channel, requester)) {
            if (channel.getVisibility() == Visibility.PUBLIC) {
                commController.sendBroadcast(new NewInvisibleChannelsMessage(channel.getId(), "Channel a été supprimé"), null);
            }
            else {
                commController.sendMulticast(channel.getAuthorizedPersons(),
                                             new NewInvisibleChannelsMessage(channel.getId(), "Channel a été supprimé"));
            }
        }
        else {
            System.err.println("Channel is not deleted");
        }

    }
}
