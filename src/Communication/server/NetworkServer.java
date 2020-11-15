package Communication.server;

import Communication.common.NetworkWriter;
import Communication.common.Parameters;
import Communication.common.Task;

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
    private NetworkWriter msgSender;

    // TODO: move this to DF management
    private final Map<String, NetworkUser> connections;

    public NetworkServer(CommunicationServerController commController, int port)
    {
        this.commController = commController;
        this.connections    = new HashMap<>();
    }

    public void start()
    {
        try
        {
            serverSocket = new ServerSocket(Parameters.PORT);
            msgSender    = new NetworkWriter();

            commController.taskManager.appendTask(new ClientAcceptor(this));
            commController.taskManager.appendTask(msgSender);

            System.out.println("Serveur en écoute sur le port " + Parameters.PORT);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void sendMessage(NetworkWriter.DeliveryPacket packet)
    {
        msgSender.sendMessage(packet);
    }

    public void addNetworkUser(Socket clientSocket)
    {
        if (clientSocket != null)
        {
            connections.put( Arrays.toString(clientSocket.getInetAddress().getAddress()),
                             new NetworkUser(commController, clientSocket) );
        }
    }

    public void close() throws IOException
    {
        if (!serverSocket.isClosed())
        {
            serverSocket.close();
        }
    }

    private static class ClientAcceptor extends Task
    {
        private NetworkServer networkServer;

        public ClientAcceptor(NetworkServer networkServer)
        {
            this.networkServer = networkServer;
        }

        @Override
        public void run()
        {
            while (!cancel)
            {
                try
                {
                    Socket clientSocket = networkServer.serverSocket.accept();

                    // TODO Use DF to manage connections
                    networkServer.addNetworkUser(clientSocket);

                    System.out.println("Nouveau client");
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
