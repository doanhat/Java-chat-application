package Communication.messages.server_to_client.channel_access.propietary_channels;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import Communication.messages.client_to_server.channel_access.proprietary_channels.UserLeftConfirmationMessage;
import common.sharedData.UserLite;

import java.util.UUID;

public class TellOwnerUserLeftMessage extends ServerToClientMessage {
    private static final long serialVersionUID = -8520233704319089062L;
    private final UserLite user;
    private final UUID channelID;

    public TellOwnerUserLeftMessage(UserLite newUser, UUID channelID) {
        this.user = newUser;
        this.channelID = channelID;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.dataClientHandler().requestLeaveChannel(channelID, user);

        // Give permission to Server to remove user from channel
        commClientController.sendMessage(new UserLeftConfirmationMessage(user, channelID));
    }
}