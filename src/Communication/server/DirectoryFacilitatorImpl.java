package Communication.server;

import common.sharedData.UserLite;

import java.net.Socket;
import java.util.*;

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
        connections.remove(clientID);
    }

    @Override
    public NetworkUser getAgent(UUID clientID) {
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
}
