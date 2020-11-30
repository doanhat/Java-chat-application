package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.UserLite;

/**
 * Cette classe permet d'indiquer au client l'arrivée d'un autre client sur le serveur
 *
 */
public class NewUserConnectedMessage extends ServerToClientMessage {

    private final UserLite newUser;

    public NewUserConnectedMessage(UserLite newUser)
    {
        this.newUser = newUser;
    }

    @Override
    protected void handle(CommunicationClientController commController)
    {
        commController.notifyUserConnected(newUser);
    }
}
