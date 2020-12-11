package Communication.messages.server_to_client.channel_access.propietary_channels;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.UserLite;

import java.util.UUID;

public class TellOwnerUserInvitedMessage extends ServerToClientMessage {
    private static final long serialVersionUID = -8527104319089062L;
    private final UserLite user;
    private final UUID channelID;

    public TellOwnerUserInvitedMessage(UserLite newUser, UUID channelID) {
        this.user = newUser;
        this.channelID = channelID;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.inviteUserToProprietaryChannel(user, channelID);
    }
}