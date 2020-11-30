package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.User;
import common.sharedData.UserLite;
/**
 * Classe signalant au client qu'un utilisateur s'est déconnecté du serveur
 *
 */
public class UserDisconnectedMessage extends ServerToClientMessage {

    private final UserLite user;

    public UserDisconnectedMessage(UserLite user)
    {
        this.user = user;
    }

    @Override
    protected void handle(CommunicationClientController commController) {
        commController.notifyUserDisconnected(user);
    }
}
