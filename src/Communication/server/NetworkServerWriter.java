package Communication.server;

import Communication.common.NetworkMessage;
import common.sharedData.UserLite;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class NetworkServerWriter {

    private Socket comm;
    private UserLite user;
    private ObjectOutputStream dos;
    private List<NetworkMessage> fileMessage;

    public NetworkServerWriter(Socket server) throws IOException {
        comm = server;
        dos = new ObjectOutputStream(server.getOutputStream());

    }

    public void sendMessage(NetworkMessage message) throws IOException {
        dos.writeObject(message);
    }
}
