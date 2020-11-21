package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.User;
import common.sharedData.UserLite;

public class UserDisconnectedMessage extends ServerToClientMessage {

    private UserLite user;

    public UserDisconnectedMessage(UserLite user)
    {
        this.user = user;
    }

    @Override
    protected void handle(CommunicationClientController commController) {
        // FIXME
        //commController.notifyUserDisconnected(user);
    }
}
