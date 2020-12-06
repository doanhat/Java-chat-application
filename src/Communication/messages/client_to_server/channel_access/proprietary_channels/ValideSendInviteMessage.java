package Communication.messages.client_to_server.channel_access.proprietary_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.NewUserJoinChannelMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.UserLite;

import java.util.UUID;

/**
 * Envoie l'information au serveur que le message à été supprimé afin de la propager
 */

public class ValideSendInviteMessage extends ClientToServerMessage {
    UUID channelID;
    UserLite guest;

    public ValideSendInviteMessage(UserLite guest, UUID channelID) {
        this.channelID = channelID;
        this.guest = guest;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        commController.sendMessage(guest.getId(), new NewUserJoinChannelMessage(guest, channelID));
    }
}
