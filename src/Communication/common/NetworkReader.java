package Communication.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

public class NetworkReader extends Thread
{
    private final CommunicationController commController;
    private final Socket socket;
    private final ObjectInputStream ois;
    //private List<NetworkMessage> messagesQueue;

    public NetworkReader(CommunicationController commController, Socket client) throws IOException
    {
        this.commController = commController;
        this.socket = client;
        this.ois = new ObjectInputStream(client.getInputStream());
    }

    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                NetworkMessage message = (NetworkMessage) ois.readObject();
                message.handle(commController);
                //messagesQueue.add(message)
            }
            catch (IOException|ClassNotFoundException e)
            {
                e.printStackTrace();
            }
        }
    }

    public NetworkMessage readMessage()
    {
        return null;
    }

    public void close() throws IOException
    {
        if(!socket.isClosed())
        {
            socket.close();
        }
    }
}
