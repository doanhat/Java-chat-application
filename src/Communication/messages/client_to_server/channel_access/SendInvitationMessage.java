package Communication.messages.client_to_server.channel_access;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.channel_modification.NewVisibleChannelMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.UserLite;

import java.util.UUID;

public class SendInvitationMessage extends ClientToServerMessage {
    private UserLite guest;
    private String message;
    private UUID channelID;

    /**
     * Message d'inviation a rejoindre un channel
     * @param guest
     * @param channel
     * @param message
     */
    public SendInvitationMessage(UserLite guest, Channel channel, String message){
        this.channelID = channel.getId();
        this.message = message;
        this.guest = guest;
    }

    /**
     * Envoi le message d'invitation
     * @param commController
     */
    @Override
    protected void handle(CommunicationServerController commController) {
        if(guest != null && channelID != null) {
            Channel channel = commController.getChannel(channelID);

            if(channel == null) {
                return;
            }

            commController.requestAddUserToChannel(guest, channel);

            // send Invitation to guest
            commController.sendMessage(guest.getId(), new NewVisibleChannelMessage(channel));
        }
    }
}
