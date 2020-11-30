package Communication.server;

import Communication.common.CyclicTask;
import Communication.common.NetworkWriter;
import Communication.common.Parameters;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe principale du serveur. Son rôle est d'instancier les socket et de préparer les taches cycliques de receptions
 */
public class NetworkServer {

    private final CommunicationServerController commController;
    private final DirectoryFacilitator directoryFacilitator;
    private ServerSocket serverSocket;
    private NetworkWriter msgSender;

    public NetworkServer(CommunicationServerController commController) {
        this.commController         = commController;
        this.directoryFacilitator   = new DirectoryFacilitatorImpl(commController);
    }

    /**
     * Démarre le serveur
     */
    public void start() {
        try {
            serverSocket = new ServerSocket(Parameters.PORT);
            msgSender    = new NetworkWriter();

            commController.taskManager.appendCyclicTask(new ClientAcceptor(this));
            commController.taskManager.appendCyclicTask(msgSender);

            System.err.println("Serveur en écoute sur le port " + Parameters.PORT);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Arrête le serveur
     * @throws IOException si le socket entraîne une IOException lors de sa fermeture
     */
    public void close() throws IOException {
        directoryFacilitator.clear();

        if (!serverSocket.isClosed()) {
            serverSocket.close();

            System.err.println("Serveur socket fermé");
        }
    }

    /**
     * Envoie le message passé en paramètre.
     * @param packet encapsulation d'un message contenant les informations nécessaires
     */
    public void sendMessage(NetworkWriter.DeliveryPacket packet) {
        msgSender.sendMessage(packet);
    }

    public DirectoryFacilitator directory() {
        return directoryFacilitator;
    }

    /**
     * Classe dont le rôle est d'être une tache periodique dont le rôle est d'accepter les nouveaux clients sur le serveur.
     *
     */
    private static class ClientAcceptor extends CyclicTask {

        private NetworkServer networkServer;

        public ClientAcceptor(NetworkServer networkServer) {
            this.networkServer = networkServer;
        }

        @Override
        /**
         * Accepte un nouvau client sur la socket et inscrit le socket.
         * Si la connexion est annulée, provoquant une IOException, la tache est annulée.
         */
        public void action() {
            try {
                Socket clientSocket = networkServer.serverSocket.accept();

                networkServer.directoryFacilitator.registerClient(clientSocket);
            }
            catch (IOException e) {
                //e.printStackTrace();
                cancel = true;
            }
        }
    }
}
