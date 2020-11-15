package Communication.server;

import Communication.common.*;
import Communication.messages.abstracts.NetworkMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

public class NetworkUser
{
    private final CommunicationServerController commController;
    // TODO: get UUID from incoming client
    private UUID id;
    private Socket socket;
    private ObjectOutputStream socketOut;
    private NetworkReader reader;

    public NetworkUser(CommunicationServerController commController, Socket socket)
    {
        this.commController = commController;
        this.socket         = socket;

        try
        {
            this.socketOut  = new ObjectOutputStream(this.socket.getOutputStream());
            this.reader     = new NetworkReader(commController,
                                                new ObjectInputStream(this.socket.getInputStream()));

            // dispatch reader to thread pool
            commController.taskManager.appendTask(this.reader);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public NetworkWriter.DeliveryPacket preparePacket(NetworkMessage message)
    {
        return new NetworkWriter.DeliveryPacket(socketOut, message);
    }

    public void stop() throws IOException
    {
        socket.close();
    }
}
