package Communication.messages.client_to_server.generic;

import common.sharedData.UserLite;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.server.CommunicationServerController;


public class UserConnectionMessage extends ClientToServerMessage {

    private final UserLite user;

    /**
     * Message avertissant de la connection d'un nouvel utilisateur
     * @param user
     */
    public UserConnectionMessage(UserLite user) {
        this.user = user;
    }

    public UserLite getUser() {
        return user;
    }

    /**
     *
     * @param commController
     */
    @Override
    protected void handle(CommunicationServerController commController) {
        // Handle Connection in NetworkUser constructor to avoid synchronization with DF
    }
}
