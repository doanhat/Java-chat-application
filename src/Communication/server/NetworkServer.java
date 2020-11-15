package Communication.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class NetworkServer
{
    private final CommunicationServerController commController;
    private ServerSocket comm;
    // TODO chose port to config
    private final int port;
    private final Map<String, NetworkUser> connexion;

    public NetworkServer(CommunicationServerController commController, int port)
    {
        this.commController = commController;
        this.port = port;
        this.connexion = new HashMap<>();
    }

    public void start()
    {
        try
        {
            comm = new ServerSocket(port);

            Thread acceptor = new Thread(() -> {
                while (true)
                {
                    try
                    {
                        Socket client = comm.accept();

                        connexion.put( Arrays.toString(client.getInetAddress().getAddress()),
                                       new NetworkUser(commController, client) );

                        System.out.println("Nouveau client");
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            });

            acceptor.start();

            System.out.println("Serveur en écoute sur le port " + port);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void close() throws IOException
    {
        if (!comm.isClosed())
        {
            comm.close();
        }
    }
}
