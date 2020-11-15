package Communication.server;

import java.net.Socket;
import java.util.UUID;

public interface DirectoryFacilitator
{
    public void registerClient(Socket clientSocket);
    public void deregisterClient(UUID clientID);
    public NetworkUser getAgent(UUID clientID);
}
