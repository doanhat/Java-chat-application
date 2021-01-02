package communication.messages.client_to_server.connection;

import communication.messages.abstracts.ClientToServerMessage;
import communication.server.CommunicationServerController;

import java.util.UUID;

public class UserDisconnectionMessage extends ClientToServerMessage {

    private static final long serialVersionUID = 2970712372437347267L;
    private final        UUID userID;

    /**
     * Message de deconnexion d'un utilisateur
     * @param userID
     */
    public UserDisconnectionMessage(UUID userID) {
        this.userID = userID;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        commController.disconnect(userID);
    }
}