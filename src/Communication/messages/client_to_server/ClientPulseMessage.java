package Communication.messages.client_to_server;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.UserLite;

public class ClientPulseMessage extends ClientToServerMessage {

    private final UserLite user;
    private int nbSequence;

    public ClientPulseMessage(UserLite user, int nbSequence) {
        this.user = user;
        this.nbSequence = nbSequence;
    }

    public UserLite getUser() {
        return user;
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        // TODO 
    }
}