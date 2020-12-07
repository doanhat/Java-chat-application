package Communication.messages.client_to_server.channel_access.proprietary_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.channel_access.propietary_channels.TellOwnerUserJoinedMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.UserLite;

import java.util.UUID;

public class SendInvitePropMessage extends ClientToServerMessage {
    private final UserLite guest;
    private final UserLite channelOwnerUserLite;
    private final UUID channelID;

    public SendInvitePropMessage(UserLite guest, Channel channel, String message){
        this.channelID = channel.getId();
        this.channelOwnerUserLite = channel.getCreator();
        this.guest = guest;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        commController.sendMessage(channelOwnerUserLite.getId(), new TellOwnerUserJoinedMessage(guest, channelID));
    }
}
