package Communication.client;

import Communication.common.NetworkMessage;
import sun.nio.ch.Net;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class NetworkClientReader implements Runnable {

    private CommunicationController refToControler;
    private Socket client;
    private Boolean running;
    private ObjectInputStream dis;
    private List<NetworkMessage> fileMessage;

    public NetworkClientReader(CommunicationController ref, Socket client) throws IOException {
        this.refToControler = ref;
        this.client = client;
        this.dis = new ObjectInputStream(client.getInputStream());
    }

    @Override
    public void run() {
        while (true) {
            try {
                NetworkMessage message = (NetworkMessage) dis.readObject();
                message.handle(refToControler);
                //fileMessage.add(message)
            } catch (IOException|ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public NetworkMessage readMessage() {
        return null;
    }
}
