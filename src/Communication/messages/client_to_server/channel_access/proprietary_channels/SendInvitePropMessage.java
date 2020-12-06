package Communication.messages.client_to_server.channel_access.proprietary_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.TellOwnerInviteMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.UserLite;

import java.util.UUID;

public class SendInvitePropMessage extends ClientToServerMessage {
    private UserLite guest;
    private UserLite channelOwnerUserLite;
    private String message;
    private UUID channelID;

    /**
     * Message d'inviation a rejoindre un channel
     * @param guest
     * @param channel
     * @param message
     */
    public SendInvitePropMessage(UserLite guest, Channel channel, String message){
        this.channelID = channel.getId();
        this.channelOwnerUserLite = channel.getCreator();
        this.message = message;
        this.guest = guest;
    }

    /**
     * Envoi le message d'invitation
     * @param commController
     */
    @Override
    protected void handle(CommunicationServerController commController) {

        if(guest != null && channelID != null && channelOwnerUserLite != null) {
            commController.sendMessage(channelOwnerUserLite.getId(), new TellOwnerInviteMessage(guest, channelID, message));
        }

    }
}
