package Communication.messages.client_to_server.channel_access.shared_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.NewUserJoinChannelMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.UserLite;

import java.util.UUID;

public class SendInviteSharedMessage extends ClientToServerMessage {
    private UserLite guest;
    private String message;
    private UUID channelID;

    /**
     * Message d'inviation a rejoindre un channel
     * @param guest
     * @param channel
     * @param message
     */
    public SendInviteSharedMessage(UserLite guest, Channel channel, String message){
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
            Channel ch = commController.getChannel(channelID);
            if(ch==null){
                System.err.println("Erreur SendInviteMessage: Le channel n'existe pas");
                return;
            }
            //commController.SendInvite(sender.getId(), receiver.getId(), message);
            commController.notifyInviteChannel(guest, ch); // Celon le diagrame de sequence
            commController.sendMessage(guest.getId(), new NewUserJoinChannelMessage(guest, channelID));
        }

    }
}
