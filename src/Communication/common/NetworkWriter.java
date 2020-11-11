package Communication.common;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NetworkWriter extends Thread {

    private Socket comm;
    private ObjectOutputStream dos;
    private List<NetworkMessage> fileMessage;

    public NetworkWriter(Socket comm) throws IOException {
        this.comm = comm;
        this.dos = new ObjectOutputStream(comm.getOutputStream());
        fileMessage = Collections.synchronizedList(new ArrayList<>());
    }

    public void sendMessage(NetworkMessage message) throws IOException {
        synchronized (fileMessage) {
            fileMessage.add(message);
            dos.writeObject(message);
        }
    }

    @Override
    public void run() {
        while (true) {
            synchronized (fileMessage) {
                if (!fileMessage.isEmpty()) {
                    try {
                        NetworkMessage msg = fileMessage.remove(0);
                        dos.writeObject(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        fileMessage.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void notifyFileMessage() {
        synchronized (fileMessage) {
            fileMessage.notify();
        }
    }

    public List<NetworkMessage> getFileMessage() {
        return fileMessage;
    }
}
