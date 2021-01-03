package communication.messages.client_to_server.connection;

import communication.messages.abstracts.ClientToServerMessage;
import communication.server.CommunicationServerController;
import common.shared_data.UserLite;

public class UserConnectionMessage extends ClientToServerMessage {

    private static final long     serialVersionUID = -4369939063238047930L;
    private final        UserLite user;

    /**
     * Message de connection d'un utilisateur
     * @param user Utilisateur
     */
    public UserConnectionMessage(UserLite user) {
        this.user = user;
    }

    public UserLite getUser() {
        return user;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        // Handle Connection in NetworkUser constructor to avoid synchronization with DF
    }
}