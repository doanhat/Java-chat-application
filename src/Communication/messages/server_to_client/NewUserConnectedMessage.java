package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.UserLite;

public class NewUserConnectedMessage extends ServerToClientMessage {

    private final UserLite newUser;

    /**
     * Message avertissant un utilisateur qu'un autre utilisateur s'est connecté a l'application
     * @param newUser
     */
    public NewUserConnectedMessage(UserLite newUser)
    {
        this.newUser = newUser;
    }

    /**
     * Notifie le controller du nouvel utilisateur
     * @param commController
     */
    @Override
    protected void handle(CommunicationClientController commController)
    {
        commController.notifyUserConnected(newUser);
    }
}
