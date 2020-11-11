package Communication.client;

import Communication.common.NetworkMessage;
import Communication.common.NetworkReader;
import Communication.common.NetworkWriter;

import java.io.IOException;
import java.net.Socket;

public class NetworkClient {
    private CommunicationClientController refToCommControler;
    private Socket comm;
    private String ip;
    private int port;
    private NetworkReader reader;
    private NetworkWriter writer;

    public NetworkClient(CommunicationClientController ref) {
        refToCommControler = ref;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void connexion(String ip, int port) throws IOException {
        this.ip = ip;
        this.port = port;
        comm = new Socket(ip, port);
        System.out.println("Connection à " + ip + ":" + port);
        reader = new NetworkReader(refToCommControler, comm);
        writer = new NetworkWriter(comm);
        reader.start();
        writer.start();
    }

    public void sendMessage(NetworkMessage message) {
        try {
            writer.notifyFileMessage();
            writer.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
