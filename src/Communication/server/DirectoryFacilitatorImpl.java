package Communication.server;

import common.sharedData.UserLite;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

public class DirectoryFacilitatorImpl implements DirectoryFacilitator {

    private final CommunicationServerController commController;
    private final Map<UUID, NetworkUser> connections;

    public DirectoryFacilitatorImpl(CommunicationServerController commController) {
        this.commController = commController;
        this.connections = new HashMap<>();
    }

    @Override
    public void registerClient(Socket clientSocket) {
        if (clientSocket != null) {
            NetworkUser client = new NetworkUser(commController, clientSocket);

            connections.put(client.uuid(), client);
        }
        else {
            System.out.println("DirectoryFacilitator.registerClients : Socket est NULL");
        }
    }

    @Override
    public void deregisterClient(UUID clientID) {
        NetworkUser user = connections.remove(clientID);

        if (user != null) {
            try {
                user.stop();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public NetworkUser getConnection(UUID clientID) {
        return connections.get(clientID);
    }

    @Override
    public List<UserLite> onlineUsers() {
        List<UserLite> userList = new ArrayList<>();

        for (NetworkUser user: connections.values()) {
            userList.add(user.userInfo());
        }

        return userList;
    }

    @Override
    public List<NetworkUser> getConnections(List<UUID> uuids) {
        List<NetworkUser> connections = new ArrayList<>();

        for (UUID id: uuids) {
            NetworkUser user = getConnection(id);

            if (user != null) {
                connections.add(user);
            }
        }

        return connections;
    }

    @Override
    public List<NetworkUser> getAllConnections() {
        return (List<NetworkUser>) connections.values();
    }
}
