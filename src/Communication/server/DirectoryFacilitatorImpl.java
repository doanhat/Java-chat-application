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

    /**
     * Register nouveau client au Annuaire
     * @param clientSocket socket du client
     */
    @Override
    public boolean registerClient(Socket clientSocket) {
        if (clientSocket != null) {
            NetworkUser client = new NetworkUser(commController, clientSocket);

            connections.put(client.uuid(), client);

            System.err.println("DirectoryFacilitator register nouveau client avec ID: " + client.uuid());

            return true;
        }
        else {
            System.err.println("DirectoryFacilitator.registerClients : Socket est NULL");
        }

        return false;
    }

    /**
     * Deregister nouveau client au Annuaire
     * @param clientID ID du client
     */
    @Override
    public boolean deregisterClient(UUID clientID) {
        NetworkUser user = connections.remove(clientID);

        if (user != null) {
            try {
                user.stop();

                return true;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * Cherche NetworkUser selon clientID
     * @param clientID ID du client
     * @return
     */
    @Override
    public NetworkUser getConnection(UUID clientID) {
        return connections.get(clientID);
    }

    /**
     * Retourne la liste des clients en-lignes
     * @return
     */
    @Override
    public List<UserLite> onlineUsers() {
        List<UserLite> userList = new ArrayList<>();

        for (NetworkUser user: connections.values()) {
            userList.add(user.userInfo());
        }

        return userList;
    }

    /**
     * Cherche la liste des NetworkUser selon User Id
     * @param users liste de clients
     * @return
     */
    @Override
    public List<NetworkUser> getConnections(List<UserLite> users) {
        List<NetworkUser> connections = new ArrayList<>();

        for (UserLite usr: users) {
            NetworkUser user = getConnection(usr.getId());

            if (user != null) {
                connections.add(user);
            }
        }

        return connections;
    }

    /**
     * Purge la liste de NetworkUser et ferme tous les sockets clients
     */
    @Override
    public void clear() {
        for (NetworkUser user: connections.values()) {
            if (user != null) {
                try {
                    user.stop();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        connections.clear();
    }
}
