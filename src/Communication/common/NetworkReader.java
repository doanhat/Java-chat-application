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

    public NetworkReader(CommunicationController commController, Socket clientSocket) throws IOException
    {
        this.commController = commController;
        this.socket = clientSocket;
        this.ois = new ObjectInputStream(socket.getInputStream());
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
        return (NetworkMessage) ois.readObject();
    }

    public void close() throws IOException
    {
        if(!socket.isClosed())
        {
            socket.close();
        }
    }
}
