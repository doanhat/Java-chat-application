package Communication.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

public class NetworkReader extends Thread
{
    private final CommunicationController commController;
    private final ObjectInputStream socketIn;
    //private List<NetworkMessage> messagesQueue;

    public NetworkReader(CommunicationController commController, ObjectInputStream socketIn) throws IOException
    {
        this.commController = commController;
        this.socketIn       = socketIn;
    }

    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                NetworkMessage message = readMessage();
                // TODO Dispatch message to TaskManager(thread pool manager)
                message.handle(commController);
                //messagesQueue.add(message)
            }
            catch (IOException|ClassNotFoundException e)
            {
                e.printStackTrace();
            }
        }
    }

    private NetworkMessage readMessage() throws IOException, ClassNotFoundException
    {
        return (NetworkMessage) socketIn.readObject();
    }
}
