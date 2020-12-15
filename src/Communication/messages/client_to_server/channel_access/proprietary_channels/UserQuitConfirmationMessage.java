package Communication.messages.client_to_server.channel_access.proprietary_channels;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.messages.server_to_client.channel_access.ValideUserQuitMessage;
import Communication.server.CommunicationServerController;
import common.shared_data.UserLite;

import java.util.UUID;

public class UserQuitConfirmationMessage extends ClientToServerMessage {
    private static final long serialVersionUID = -19233136521370473L;
    private final UUID channelID;
    private final UserLite user;

    public UserQuitConfirmationMessage(UserLite user, UUID channelID) {
        this.channelID  = channelID;
        this.user = user;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        commController.quitChannel(channelID, user);
        commController.sendMessage(user.getId(), new ValideUserQuitMessage(channelID));
    }
}