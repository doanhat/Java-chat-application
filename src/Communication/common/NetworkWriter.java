package Communication.common;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NetworkWriter extends Thread {

    private final Socket comm;
    private final ObjectOutputStream oos;
    private final List<NetworkMessage> messagesQueue;

    public NetworkWriter(Socket comm) throws IOException {
        this.comm = comm;
        this.oos = new ObjectOutputStream(comm.getOutputStream());
        messagesQueue = Collections.synchronizedList(new ArrayList<>());
    }

    public void sendMessage(NetworkMessage message) {
        synchronized (messagesQueue) {
            messagesQueue.add(message);
            messagesQueue.notifyAll();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                synchronized (messagesQueue) {
                    if (!messagesQueue.isEmpty()) {
                        NetworkMessage msg = messagesQueue.remove(0);
                        oos.writeObject(msg);
                    } else {
                        messagesQueue.wait();
                    }
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void close() throws IOException {
        if(!comm.isClosed()) {
            comm.close();
        }
    }

}
