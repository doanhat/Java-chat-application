package Communication.client;

import Communication.common.NetworkMessage;
import Communication.server.NetworkServerReader;
import Communication.server.NetworkServerWriter;

import java.io.IOException;
import java.net.Socket;

public class NetworkClient {
    private CommunicationController refToCommControler;
    private Socket comm;
    private String ip;
    private int port;
    private NetworkClientReader reader;
    private NetworkClientWriter writer;

    public NetworkClient(CommunicationController ref) {
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
        reader = new NetworkClientReader(refToCommControler, comm);
        writer = new NetworkClientWriter(comm);
    }

    public void sendMessage(NetworkMessage message){
        try {
            writer.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
