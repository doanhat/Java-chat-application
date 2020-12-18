package Communication.messages.server_to_client.connection;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.shared_data.UserLite;
/**
 * Classe signalant au client qu'un utilisateur s'est déconnecté du serveur
 *
 */
public class UserDisconnectedMessage extends ServerToClientMessage {

	private static final long serialVersionUID = -1766844866729235472L;
	private final UserLite user;

    public UserDisconnectedMessage(UserLite user)
    {
        this.user = user;
    }

    @Override
    protected void handle(CommunicationClientController commController) {
        commController.mainHandler().notifyUserDisconnected(user);
    }
}
