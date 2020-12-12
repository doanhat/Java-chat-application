package Communication.messages.client_to_server.channel_access.proprietary_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.channel_access.propietary_channels.TellOwnerToRemoveAdminMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.Channel;
import common.sharedData.UserLite;

import java.util.UUID;

public class RemoveAdminPropMessage extends ClientToServerMessage {

    private static final long serialVersionUID = -4704993L;
    private final UUID channelID;
    private final UUID channelCreatorID;
    private final UserLite user;

    public RemoveAdminPropMessage(UserLite user, Channel channel) {
        this.channelID  = channel.getId();
        this.channelCreatorID = channel.getCreator().getId();
        this.user = user;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        //Handle proprietary channel. Tell Owners to remove admins
        commController.sendMessage(channelCreatorID, new TellOwnerToRemoveAdminMessage(user, channelID));
    }
}