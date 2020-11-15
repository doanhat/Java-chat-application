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
    private ServerSocket serverSocket;
    // TODO chose port to config
    private final int port;
    // TODO: move this to DF management
    private final Map<String, NetworkUser> connection;

    public NetworkServer(CommunicationServerController commController, int port)
    {
        this.commController = commController;
        this.port           = port;
        this.connection     = new HashMap<>();
    }

    public void start()
    {
        try
        {
            serverSocket = new ServerSocket(port);

            Thread acceptor = new Thread(() -> {
                while (true)
                {
                    try
                    {
                        Socket clientSocket = serverSocket.accept();

                        connection.put( Arrays.toString(clientSocket.getInetAddress().getAddress()),
                                        new NetworkUser(commController, clientSocket) );

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
        if (!serverSocket.isClosed())
        {
            serverSocket.close();
        }
    }
}
