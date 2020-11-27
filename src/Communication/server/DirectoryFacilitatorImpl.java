package Communication.server;

import Communication.common.Parameters;
import Communication.messages.client_to_server.ClientPulseMessage;
import common.sharedData.UserLite;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class DirectoryFacilitatorImpl implements DirectoryFacilitator {

    private final CommunicationServerController commController;
    private final Map<UUID, NetworkUser> connections;
    private final Timer timer;

    public DirectoryFacilitatorImpl(CommunicationServerController commController) {
        this.commController = commController;
        this.connections = new HashMap<>();

        // Setup periodic users health check
        this.timer = new Timer();

        this.timer.schedule(new TimerTask(){
            @Override
            public void run() {
                Collection<UUID> userIDs = connections.keySet();
                // Check pulse of all NetworkUsers
                for (UUID userID: userIDs) {
                    NetworkUser user = connections.get(userID);

                    if (user != null) {
                        if (!user.isActive()) {
                            // reset active flag to false wait for client to reset to true
                            user.active(false);
                            // TODO reply to client
                        }
                        else {
                            System.err.println("Client " + userID + " déconnecté");
                            commController.disconnect(userID);
                        }
                    }
                }

            }
        }, 0, Parameters.PULSE_INTERVAL);
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
     * @return handler du client socket
     */
    @Override
    public NetworkUser getConnection(UUID clientID) {
        return connections.get(clientID);
    }

    /**
     * Retourne la liste des clients en-lignes
     * @return liste des clients enlignes
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
     * @return listes des handlers du client socket actives
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
     * Recevoir l'impulsion d'un client
     * @param clientID ID du client
     */
    @Override
    public void receivePulse(UUID clientID) {
        NetworkUser user = getConnection(clientID);

        if (user != null) {
            System.err.println("Server reçoit impulse du client " + clientID);

            user.active(true);
        }
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
