package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.shared_data.UserLite;

public class UserDisconnectedMessage extends ServerToClientMessage {

    private final UserLite user;

    public UserDisconnectedMessage(UserLite user)
    {
        this.user = user;
    }

    @Override
    protected void handle(CommunicationClientController commController) {
        commController.notifyUserDisconnected(user);
    }
}
