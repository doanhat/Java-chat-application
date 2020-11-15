package Communication.client;

import Communication.common.NetworkMessage;
import Communication.common.NetworkReader;
import Communication.common.NetworkWriter;

import java.io.IOException;
import java.net.Socket;

public class NetworkClient {
    private final CommunicationClientController commController;
    private Socket socket;
    private NetworkReader reader;
    private NetworkWriter writer;

    public NetworkClient(CommunicationClientController commController)
    {
        this.commController = commController;
    }

    public void connexion(String ip, int port) throws IOException
    {
        socket = new Socket(ip, port);
        System.out.println("Connection à " + ip + ":" + port);
        reader = new NetworkReader(commController, socket);
        writer = new NetworkWriter(socket);
        reader.start();
        writer.start();
    }

    public void sendMessage(NetworkMessage message) {
        writer.sendMessage(message);
    }

    public void close() throws IOException {
        reader.close();
        writer.close();
        socket.close();
    }
}
