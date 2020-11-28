package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.UserLite;

import java.util.UUID;

public class NewUserJoinChannelMessage extends ServerToClientMessage {

    private final UserLite user;
    private final UUID channelID;

    /**
     * Message avertissant un utilisateur qu'un autre utilisateur a rejoint un channel ou il est présent.
     * @param receiver
     * @param channelID
     */
    public NewUserJoinChannelMessage(UserLite receiver, UUID channelID) {
        this.user = receiver;
        this.channelID = channelID;
    }

    /**
     * Notifie le controller de l'arrive de l'utilisateur
     * @param commClientController
     */
    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.notifyNewUserAddedToJoinChannel(user, channelID);
    }
}
