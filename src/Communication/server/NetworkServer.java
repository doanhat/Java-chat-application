package Communication.server;

import Communication.common.CyclicTask;
import Communication.common.NetworkWriter;
import Communication.common.Parameters;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkServer {

    private final CommunicationServerController commController;
    private final DirectoryFacilitator directoryFacilitator;
    private ServerSocket serverSocket;
    private NetworkWriter msgSender;

    public NetworkServer(CommunicationServerController commController) {
        this.commController         = commController;
        this.directoryFacilitator   = new DirectoryFacilitatorImpl(commController);
    }

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

    public void close() throws IOException {
        directoryFacilitator.clear();

        if (!serverSocket.isClosed()) {
            serverSocket.close();

            System.err.println("Serveur socket fermé");
        }
    }

    public void sendMessage(NetworkWriter.DeliveryPacket packet) {
        msgSender.sendMessage(packet);
    }

    public DirectoryFacilitator directory() {
        return directoryFacilitator;
    }

    private static class ClientAcceptor extends CyclicTask {

        private NetworkServer networkServer;

        public ClientAcceptor(NetworkServer networkServer) {
            this.networkServer = networkServer;
        }

        @Override
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
