package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.UserLite;

import java.util.UUID;

/**
 * Cette classe permet d'indiquer l'arrivée d'un nouvel utilisateur sur le servuer
 *
 */
public class NewUserJoinChannelMessage extends ServerToClientMessage {

    private final UserLite user;
    private final UUID channelID;

    /**
     * Crée un message indiquant l'arrivée d'un nouvel utilisateur
     * @param receiver utilisateur ayant rejoint le canal
     * @param channelID Canal en question
     */
    public NewUserJoinChannelMessage(UserLite receiver, UUID channelID) {
        this.user = receiver;
        this.channelID = channelID;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.notifyNewUserAddedToJoinChannel(user, channelID);
    }
}
