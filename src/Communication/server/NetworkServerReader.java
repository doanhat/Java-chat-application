package Communication.server;

import Communication.common.NetworkMessage;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

public class NetworkServerReader implements Runnable {

    private CommunicationServerController refToControler;
    private Socket server;
    private ObjectInputStream dis;
    private Boolean running;
    private List<NetworkMessage> fileMessage;

    public NetworkServerReader(CommunicationServerController refToControler, Socket server) throws IOException {
        this.refToControler = refToControler;
        this.server = server;
        dis = new ObjectInputStream(server.getInputStream());
    }

    @Override
    public void run() {
        while (true) {
            try {
                NetworkMessage message = (NetworkMessage) dis.readObject();
                message.handle(this.refToControler);
                //fileMessage.add(message)
            } catch (IOException|ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private NetworkMessage readMessage() {
        return null;
    }
}
