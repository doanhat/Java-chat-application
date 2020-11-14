package Communication.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

public class NetworkReader extends Thread {
    private final CommunicationController refToController;
    private final Socket comm;
    private final ObjectInputStream ois;
    //private List<NetworkMessage> messagesQueue;

    public NetworkReader(CommunicationController ref, Socket client) throws IOException {
        this.refToController = ref;
        this.comm = client;
        this.ois = new ObjectInputStream(client.getInputStream());
    }

    @Override
    public void run() {
        while (true) {
            try {
                NetworkMessage message = (NetworkMessage) ois.readObject();
                message.handle(refToController);
                //messagesQueue.add(message)
            } catch (IOException|ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public NetworkMessage readMessage() {
        return null;
    }

    public void close() throws IOException {
        if(!comm.isClosed()) {
            comm.close();
        }
    }
}
