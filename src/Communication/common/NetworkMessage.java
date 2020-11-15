package Communication.common;

import java.io.Serializable;

public abstract class NetworkMessage implements Serializable
{
    public abstract void handle(CommunicationController controller);
}
