package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.UserLite;

public class NewUserConnectedMessage extends ServerToClientMessage {

    public NewUserConnectedMessage(UserLite newUser)
    {
        // TODO: implementer constructeur
    }

    @Override
    protected void handle(CommunicationClientController commController)
    {
        // TODO: implementer handler méthode
    }
}
