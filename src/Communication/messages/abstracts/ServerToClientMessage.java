package Communication.messages.abstracts;

import Communication.client.CommunicationClientController;
import Communication.common.CommunicationController;

public abstract class ServerToClientMessage extends NetworkMessage
{
    @Override
    public void handle(CommunicationController communicationController)
    {
        handle((CommunicationClientController) communicationController);
    }

    protected abstract void handle(CommunicationClientController commClientController);
}
