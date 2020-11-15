package Communication.server;

import Communication.common.*;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

public class NetworkUser
{
    private final CommunicationServerController commController;
    private UUID id;
    private NetworkReader reader;
    private NetworkWriter writer;

    public NetworkUser(CommunicationServerController commController, Socket comm) {
        this.commController = commController;

        try
        {
            this.writer = new NetworkWriter(comm);
            this.reader = new NetworkReader(commController, comm);
            this.writer.start();
            this.reader.start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void sendMessage(NetworkMessage message)
    {
        writer.sendMessage(message);
    }
}
