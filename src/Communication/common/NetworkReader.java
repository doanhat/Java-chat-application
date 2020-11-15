package Communication.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

public class NetworkReader extends Task
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
        while (!cancel)
        {
            try
            {
                NetworkMessage message = readMessage();

                // Dispatch message to TaskManager
                commController.taskManager.appendTask(new NetworkMessage.Handler(message, commController));
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
