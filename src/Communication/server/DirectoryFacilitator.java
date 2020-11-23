package Communication.server;

import common.sharedData.UserLite;

import java.net.Socket;
import java.util.List;
import java.util.UUID;

public interface DirectoryFacilitator {
    /**
     * Register nouveau client au Annuaire
     * @param clientSocket socket du client
     * @return
     */
    boolean registerClient(Socket clientSocket);

    /**
     * Deregister nouveau client au Annuaire
     * @param clientID ID du client
     * @return
     */
    boolean deregisterClient(UUID clientID);

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
     * Cherche la liste des NetworkUser selon User Id
     * @param users liste de clients
     * @return
     */
    List<NetworkUser> getConnections(List<UserLite> users);

    /**
     * Purge la liste de NetworkUser et ferme tous les sockets clients
     */
    void clear();
    /**
     * Recevoir l'impulsion d'un client
     * @param clientID ID du client
     * @param nbSequence numero de séquence actuel du client
     */
    void receivePulse(UUID clientID, int nbSequence);
}
