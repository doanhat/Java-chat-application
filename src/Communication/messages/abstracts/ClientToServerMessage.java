package Communication.messages.abstracts;

import Communication.common.CommunicationController;
import Communication.server.CommunicationServerController;

public abstract class ClientToServerMessage extends NetworkMessage
{
    @Override
    public void handle(CommunicationController communicationController)
    {
        handle((CommunicationServerController) communicationController);
    }

    protected abstract void handle(CommunicationServerController commClientController);
}
