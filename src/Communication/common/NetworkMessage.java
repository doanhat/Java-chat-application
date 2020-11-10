package Communication.common;

import Communication.client.CommunicationController;

public abstract class NetworkMessage
{
    public abstract void handle(CommunicationController controller);
}
