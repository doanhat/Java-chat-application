package Communication.server;

import Communication.common.CommunicationController;

import java.io.IOException;

public class CommunicationServerController extends CommunicationController
{

    private final NetworkServer server;

    public CommunicationServerController()
    {
        super();

        server = new NetworkServer(this);
    }

    public void start()
    {
        server.start();
    }

    public void stop()
    {
        taskManager.shutdown();

        try
        {
            server.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
