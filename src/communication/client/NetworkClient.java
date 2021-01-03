package communication.client;

import communication.common.NetworkReader;
import communication.common.NetworkWriter;
import communication.messages.abstracts.NetworkMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cette classe sert à gérer la connexion du client au serveur au travers de socket et de {@link communication.common.NetworkReader} et {@link communication.common.NetworkWriter}
 */
public class NetworkClient {

    private final CommunicationClientController commController;
    private final Logger                        logger = Logger.getLogger(this.getClass().getName());
    private       Socket                        socket;
    private       ObjectOutputStream            socketOut;
    private       NetworkReader                 reader;
    private       NetworkWriter                 writer;

    /**
     * Constructeur du client réseau
     *
     * @param commController singleton du controlleur client.
     */
    public NetworkClient(CommunicationClientController commController) {
        this.commController = commController;
    }

    /**
     * Effectue la connexion du client au serveur en utilisant les différents paramètres passés en information
     *
     * @param ip   adress ip du serveur
     * @param port port tcp du serveur auquel se connecter
     * @throws IOException exception envoyée en cas d'erreur à la création des Streams ou du socket client.
     */
    public void connect(String ip, int port) throws IOException {
        socket = new Socket(ip, port);

        logger.log(Level.INFO, "Connexion à {0}:{1}", new Object[]{ip, port});

        socketOut = new ObjectOutputStream(socket.getOutputStream());

        reader = new NetworkReader(commController, new ObjectInputStream(socket.getInputStream()));
        writer = new NetworkWriter();

        // Dispatch reader, writer to thread pool
        commController.taskManager.appendCyclicTask(reader);
        commController.taskManager.appendCyclicTask(writer);
    }

    /**
     * Envoie un message au serveur sur lequel on est connecté
     *
     * @param message message a envoyer
     */
    public void sendMessage(NetworkMessage message) {
        writer.sendMessage(new NetworkWriter.DeliveryPacket(socketOut, message));
    }

    public UUID getUUID() {
        if (reader != null) {
            return reader.getUser();
        }
        return null;
    }

    /**
     * Ferme le socket entrainant la deconnexion du client.
     *
     * @throws IOException générée en cas d'echec de fermeture du socket
     */
    public void close() throws IOException {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }
}
