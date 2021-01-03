package communication.common;

import java.util.UUID;


/**
 * Classe abstraite mêre des classes gérant la Communication côté serveur et côté client.
 */
public abstract class CommunicationController {
    public TaskManager taskManager;

    /**
     * Déconnecte l'utilisateur passé en paramètre. Le comportement est different sur le serveur et le client
     *
     * @param user identifiant unique d'un utilisateur sur le serveur
     * @see communication.client.CommunicationClientController
     * @see communication.server.CommunicationServerController
     */
    public abstract void disconnect(UUID user);
}
