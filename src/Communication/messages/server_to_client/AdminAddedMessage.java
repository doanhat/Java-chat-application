package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.Message;
import common.sharedData.UserLite;

import java.util.UUID;

public class AdminAddedMessage extends ServerToClientMessage{

    private final UserLite user;
    private final UUID channelID;

    /**
     * Message avertissant un utilisateur qu'un nouvel administrateur a été ajouté
     * @param user
     * @param channelID
     */
    public AdminAddedMessage(UserLite user, UUID channelID){
        this.user = user;
        this.channelID = channelID;
    }

    /**
     * Notifie le controller de l'ajout
     * @param commController
     */
    @Override
    protected void handle(CommunicationClientController commController) {
        commController.notifyNewAdmin(channelID, user);
    }
}
