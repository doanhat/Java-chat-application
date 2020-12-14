package Communication.messages.client_to_server.channel_access.proprietary_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.server.CommunicationServerController;
import common.shared_data.UserLite;

import java.util.UUID;

public class UserLeftConfirmationMessage extends ClientToServerMessage {
    private static final long serialVersionUID = -19233136521370473L;
    private final UUID channelID;
    private final UserLite user;

    public UserLeftConfirmationMessage(UserLite user, UUID channelID) {
        this.channelID  = channelID;
        this.user = user;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        commController.leaveChannel(channelID, user);
    }
}