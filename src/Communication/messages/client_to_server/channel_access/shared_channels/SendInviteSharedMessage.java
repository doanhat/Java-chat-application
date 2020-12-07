package Communication.messages.client_to_server.channel_access.shared_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.channel_access.JoinChannelResponseMessage;
import Communication.messages.server_to_client.channel_access.NewUserJoinChannelMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.UserLite;

import java.util.UUID;

public class SendInviteSharedMessage extends ClientToServerMessage {
    private UserLite guest;
    private String message;
    private UUID channelID;

    public SendInviteSharedMessage(UserLite guest, Channel channel, String message){
        this.channelID = channel.getId();
        this.message = message;
        this.guest = guest;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        if(guest != null && channelID != null) {
            Channel channel = commController.getChannel(channelID);

            if(channel == null){
                return;
            }

            //commController.SendInvite(sender.getId(), receiver.getId(), message);
            commController.requestAddUserToChannel(guest, channel);

            // send Invitation guest
            // TODO INTEGRATION V2: verify if after invitation , guest is added to channel or guest just can see channel and can refuse or accept invitation
            commController.sendMessage(guest.getId(), new JoinChannelResponseMessage(guest, channel, true));

            // Notifie les utilisateurs connectes au channel qu'un nouveau utilisateur les rejoins
            commController.sendMulticast(channel.getAcceptedPersons(), new NewUserJoinChannelMessage(guest, channel.getId()));
        }

    }
}
