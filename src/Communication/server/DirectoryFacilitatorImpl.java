package Communication.server;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DirectoryFacilitatorImpl implements DirectoryFacilitator
{
    private final Map<UUID, NetworkUser> connections;

    public DirectoryFacilitatorImpl()
    {
        connections = new HashMap<>();
    }

    @Override
    public void registerClient(UUID clientID, Socket clientSocket)
    {
        // TODO get UUID from connectionMessage and assign it to corresponding NetworkUser
    }

    public void deregisterClient(UUID clientID)
    {
        connections.remove(clientID);
    }

    public NetworkUser getAgent(UUID clientID)
    {
        return connections.get(clientID);
    }
}
