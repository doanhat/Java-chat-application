package Communication.client;

        import Communication.common.NetworkMessage;

        import java.io.IOException;
        import java.io.ObjectOutputStream;
        import java.net.Socket;

public class NetworkClientWriter {

    private Socket client;
    private ObjectOutputStream dos;

    public NetworkClientWriter(Socket client) throws IOException {
        this.client = client;
        this.dos = new ObjectOutputStream(client.getOutputStream());
    }

    public void sendMessage(NetworkMessage message) throws IOException {
        //fileMessage.add(message);
        dos.writeObject(message);
    }

}
