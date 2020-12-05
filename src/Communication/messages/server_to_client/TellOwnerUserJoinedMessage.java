package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import Communication.messages.client_to_server.proprietary_channels.UserJoinedConfirmationMessage;
import common.sharedData.UserLite;

import java.util.UUID;

public class TellOwnerUserJoinedMessage extends ServerToClientMessage {
    private static final long serialVersionUID = -8527233704319089062L;
    private final UserLite user;
    private final UUID channelID;

    public TellOwnerUserJoinedMessage(UserLite newUser, UUID channelID) {
        this.user    = newUser;
        this.channelID  = channelID;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        //TODO recuperer l'object channel contenant tous les messages grace a l'ID
        //commClientController.sendMessage(new UserJoinedConfirmationMessage(newUser, channel));
    }
}
