package Communication.client;

import Communication.common.NetworkMessage;
import Communication.common.NetworkReader;
import Communication.common.NetworkWriter;

import java.io.IOException;
import java.net.Socket;

public class NetworkClient {
    private final CommunicationClientController refToCommController;
    private Socket comm;
    private NetworkReader reader;
    private NetworkWriter writer;

    public NetworkClient(CommunicationClientController ref) {
        refToCommController = ref;
    }

    public void connexion(String ip, int port) throws IOException {
        comm = new Socket(ip, port);
        System.out.println("Connection à " + ip + ":" + port);
        reader = new NetworkReader(refToCommController, comm);
        writer = new NetworkWriter(comm);
        reader.start();
        writer.start();
    }

    public void sendMessage(NetworkMessage message) {
        writer.sendMessage(message);
    }

    public void close() throws IOException {
        reader.close();
        writer.close();
        comm.close();
    }
}
