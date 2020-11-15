package Communication.server;

import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DirectoryFacilitatorImpl implements DirectoryFacilitator
{
    private final CommunicationServerController commController;
    private final Map<UUID, NetworkUser> connections;

    public DirectoryFacilitatorImpl(CommunicationServerController commController)
    {
        this.commController = commController;
        this.connections    = new HashMap<>();
    }

    @Override
    public void registerClient(Socket clientSocket)
    {
        if (clientSocket != null)
        {
            NetworkUser client = new NetworkUser(commController, clientSocket);

            connections.put(client.uuid(), client);
        }
        else
        {
            System.out.println("DirectoryFacilitator.registerClients : Socket est NULL");
        }
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
