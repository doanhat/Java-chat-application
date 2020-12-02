package Communication.common;

import Communication.messages.abstracts.NetworkMessage;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.UUID;

/**
 * Classe gérant la communication la lecture des information reçues sur le socket réseau
 *
 */
public class NetworkReader extends CyclicTask {

    private final CommunicationController commController;
    private final ObjectInputStream socketIn;
    private UUID userID;

    /**
     * Constructeur du Lecteur.
     * @param commController Controlleur de communication
     * @param socketIn Socket sur lequel on recois les objets.
     */
    public NetworkReader(CommunicationController commController, ObjectInputStream socketIn) {
        this.commController = commController;
        this.socketIn       = socketIn;
        this.userID         = null;
    }

    /**
     * Setter de l'identifiant unique d'utilisateur
     * @param userID UUID de l'utilisateur
     */
    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    public UUID getUser() {
        return userID;
    }

    /**
     * Récupère le message reçu sur le réseau et le transforme en objet de type {@link Communication.messages.abstracts.NetworkMessage} en utilisant
     * {@link ObjectInputStream#readObject()}
     * @see ObjectInputStream#readObject()
     * @return {@link NetworkMessage} NetworkMessage reçu
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public NetworkMessage readMessage() throws IOException, ClassNotFoundException {
        return (NetworkMessage) socketIn.readObject();
    }

    /**
     * Récupère un message en utilisant {@link #readMessage()} puis ajoute a la file de tache l'execution de la tache du message.
     * Cette methode traite aussi un certain nombre d'exceptions liées aux classes ou a des erreurs d'IO ou de fermeture de socket entrainant une erreur.
     * <br> Les exceptions traitées sont les {@link ClassNotFoundException}, {@link EOFException} et {@link IOException}.
     */
    @Override
    public void action() {
        try {
            NetworkMessage message = readMessage();

            // Dispatch message à TaskManager
            commController.taskManager.appendTask(new NetworkMessage.Handler(message, commController));
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch(EOFException e) {
            //eof - no error in this case
        }
        catch (IOException e) {
            //e.printStackTrace();

            //commController.disconnect(userID);
            cancel = true;
        }
    }
}
