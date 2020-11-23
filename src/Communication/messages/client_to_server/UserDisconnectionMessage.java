package Communication.messages.client_to_server;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.UserLite;

public class UserDisconnectionMessage extends ClientToServerMessage {

    private final UserLite user;

    public UserDisconnectionMessage(UserLite user) {
        this.user = user;
    }

    public UserLite getUser() {
        return user;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        // TODO handle client disconnection
    }
}