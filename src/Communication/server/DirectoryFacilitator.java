package Communication.server;

import common.sharedData.UserLite;

import java.net.Socket;
import java.util.List;
import java.util.UUID;

public interface DirectoryFacilitator {
    /**
     * Enregistrer le nouveau client a l'annuaire
     * @param clientSocket socket du client
     * @return <code>true</code> si le nouveau client <code>false</code> si le nouveau client n'a pas été inscrit à l'annuaire.
     */
    boolean registerClient(Socket clientSocket);

    /**
     * Supprimer l'enregistrement du client a l'annuaire
     * @param clientID ID du client
     * @return <code>true</code> si le client à bien été supprimé de l'annuaire </code>false</code> sinon
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
     * Recevoir l'impulsion d'un client
     * @param clientID ID du client
     */
    void receivePulse(UUID clientID);

    /**
     * Purge la liste de NetworkUser et ferme tous les sockets clients
     */
    void clear();
}
