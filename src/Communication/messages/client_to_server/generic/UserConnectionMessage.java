package Communication.messages.client_to_server.generic;

import Communication.messages.abstracts.NetworkMessage;
import Communication.messages.server_to_client.AcceptationMessage;
import Communication.messages.server_to_client.NewUserConnectedMessage;
import common.sharedData.Channel;
import common.sharedData.UserLite;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.server.CommunicationServerController;

import java.util.List;
import java.util.UUID;

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
