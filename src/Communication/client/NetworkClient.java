package Communication.client;

import Communication.messages.abstracts.NetworkMessage;
import Communication.common.NetworkReader;
import Communication.common.NetworkWriter;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class NetworkClient
{
    private final CommunicationClientController commController;
    private Socket socket;
    private ObjectOutputStream socketOut;
    private NetworkReader reader;
    private NetworkWriter writer;

    public NetworkClient(CommunicationClientController commController)
    {
        this.commController = commController;
    }

    public void connect(String ip, int port) throws IOException
    {
        socket = new Socket(ip, port);
        System.out.println("Connexion à " + ip + ":" + port);
        socketOut = new ObjectOutputStream(socket.getOutputStream());

        reader = new NetworkReader(commController, new ObjectInputStream(socket.getInputStream()));
        writer = new NetworkWriter();

        // Dispatch reader, writer to thread pool
        commController.taskManager.appendTask(reader);
        commController.taskManager.appendTask(writer);
    }

    public void sendMessage(NetworkMessage message)
    {
        writer.sendMessage(new NetworkWriter.DeliveryPacket(socketOut, message));
    }

    public void close() throws IOException
    {
        socket.close();
    }
}
