package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.UserLite;

public class NewUserConnectedMessage extends ServerToClientMessage {

    private UserLite newUser;

    public NewUserConnectedMessage(UserLite newUser)
    {
        this.newUser = newUser;
    }

    @Override
    protected void handle(CommunicationClientController commController)
    {
        commController.notifyUserConnected(newUser);
    }
}
