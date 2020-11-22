package Communication.server;

import common.sharedData.UserLite;

import java.net.Socket;
import java.util.List;
import java.util.UUID;

public interface DirectoryFacilitator {
    /**
     * Register nouveau client au Annuaire
     * @param clientSocket socket du client
     */
    void registerClient(Socket clientSocket);

    /**
     * Deregister nouveau client au Annuaire
     * @param clientID ID du client
     */
    void deregisterClient(UUID clientID);

    /**
     * Cherche NetworkUser selon clientID
     * @param clientID ID du client
     * @return
     */
    NetworkUser getConnection(UUID clientID);

    /**
     * Retourne la liste des clients en-lignes
     * @return
     */
    List<UserLite> onlineUsers();

    /**
     * Retourne la liste des NetworkUser de tous les clients en-lignes
     * @return
     */
    List<NetworkUser> getAllConnections();

    /**
     * Cherche la liste des NetworkUser selon User Id
     * @param uuids IDs des Clients
     * @return
     */
    List<NetworkUser> getConnections(List<UUID> uuids);
}
