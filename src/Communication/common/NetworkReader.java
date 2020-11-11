package Communication.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

public class NetworkReader extends Thread {
    private CommunicationController refToControler;
    private Socket client;
    private ObjectInputStream dis;
    private List<NetworkMessage> fileMessage;

    public NetworkReader(CommunicationController ref, Socket client) throws IOException {
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
