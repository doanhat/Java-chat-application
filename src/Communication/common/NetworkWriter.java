package Communication.common;

import Communication.messages.abstracts.NetworkMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe gérant l'écriture et l'envoi de messages sur le réseau depuis une Liste synchronisée de messages.
 *
 */
public class NetworkWriter extends CyclicTask {

    private final List<DeliveryPacket> messagesQueue;
    private static final Logger LOGGER = Logger.getLogger(NetworkWriter.class.getName());

    public NetworkWriter() {
        messagesQueue = Collections.synchronizedList(new ArrayList<>());
    }

    /**
     * Ajoute un Message à envoyer à la liste synchronisée de messages
     * @param packet intance encapsulant le recepteur et le message reçu
     */
    public void sendMessage(DeliveryPacket packet) {
        synchronized (messagesQueue) {
            messagesQueue.add(packet);
            messagesQueue.notifyAll();
        }
    }

    /**
     * Effectue l'envoi du dernier message de la liste d'attente.
     */
    @Override
    public void action() {
        try {
            synchronized (messagesQueue) {
                if (!messagesQueue.isEmpty()) {
                    DeliveryPacket packet = messagesQueue.remove(0);
                    packet.send();
                }
                else {
                    messagesQueue.wait();
                }
            }
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cesse l'envoi de messages
     */
    @Override
    public void stop() {
        super.stop();

        synchronized (messagesQueue) {
            messagesQueue.notifyAll();
        }
    }

    /**
     * Vide la liste de messages
     */
    @Override
    protected void cleanup() {
        synchronized (messagesQueue) {
            messagesQueue.clear();
        }
    }

    /**
     * Classe embarqué encapsule message et recepteur
     */
    public static class DeliveryPacket {
        private ObjectOutputStream receiver;
        private NetworkMessage message;

        public DeliveryPacket(ObjectOutputStream receiver, NetworkMessage message) {
            this.receiver = receiver;
            this.message = message;
        }

        /**
         * Envoie le message en sur la socket en utilisant {@link ObjectOutputStream#writeObject(Object)} en lui passant le message à envoyer.
         * @throws IOException si l'outputStream renvoie une IOException au moment du write
         */
        public void send() throws IOException {
            LOGGER.log(Level.FINE, "Send message {}", message.getClass());
            receiver.writeObject(message);
        }
    }
}
