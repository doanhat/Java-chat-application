package Communication.client;

import Communication.common.CommunicationController;

import java.io.IOException;

public class CommunicationClientController extends CommunicationController
{
    private final NetworkClient client;

    public CommunicationClientController()
    {
        super();

        client = new NetworkClient(this);
    }

    public void start(String ip, int port)
    {
        try
        {
            client.connect(ip, port);
            System.out.println("Connexion au server...");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void stop()
    {
        taskManager.shutdown();

        try
        {
            client.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
