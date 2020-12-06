package Communication.common;

import java.util.UUID;



/**
 * Classe abstraite mêre des classes gérant la Communication côté serveur et côté client.
 *
 */
public abstract class CommunicationController {
    public final TaskManager taskManager = new TaskManager();

    /**
     * Déconnecte l'utilisateur passé en paramètre. Le comportement est different sur le serveur et le client
     * @see Communication.client.CommunicationClientController
     * @see Communication.server.CommunicationServerController
     * @param user identifiant unique d'un utilisateur sur le serveur
     */
    public abstract void disconnect(UUID user);
}
