package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.User;

public class UserDisconnectedMessage extends ServerToClientMessage {

    private User user;

    public UserDisconnectedMessage(User user)
    {
        this.user = user;
    }

    @Override
    protected void handle(CommunicationClientController commController)
    {
        commController.notifyUserDisconnected(user);
    }
}
