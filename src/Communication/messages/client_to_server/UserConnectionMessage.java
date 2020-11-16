package Communication.messages.client_to_server;

import Communication.messages.abstracts.ClientToServerMessage;
import Communication.server.CommunicationServerController;
import common.sharedData.UserLite;

import java.util.UUID;

public class UserConnectionMessage extends ClientToServerMessage {

    private UserLite user;

    public UserConnectionMessage(UserLite user) {
        this.user = user;
    }

    public UUID getUuid() {
        return user.getId();
    }

    @Override
    protected void handle(CommunicationServerController commController) {
        // TODO get list of publicChannels and Online users and send back to user
        // TODO: broadcast new user info to all online users
    }
}
