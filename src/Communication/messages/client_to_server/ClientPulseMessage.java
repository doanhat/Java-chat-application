package Communication.messages.client_to_server;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.UserLite;

public class ClientPulseMessage extends ClientToServerMessage {

    private final UserLite user;

    public ClientPulseMessage(UserLite user) {
        this.user = user;
    }

    public UserLite getUser() {
        return user;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        // TODO 
    }
}