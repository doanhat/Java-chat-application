package Communication.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class NetworkServer {
    private final CommunicationServerController refToCommServerController;
    private ServerSocket comm;
    private int port; // Config
    private Map<String, AID> connexion;

    public NetworkServer(CommunicationServerController ref, int port) {
        this.refToCommServerController = ref;
        this.port = port;
        this.connexion = new HashMap<String, AID>();
    }

    public void start() {
        try {
            comm = new ServerSocket(port);
            Thread acceptor = new Thread() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Socket client = comm.accept();
                            connexion.put(client.getInetAddress().getAddress().toString(), new AID(refToCommServerController, client));
                            System.out.println("Nouveau client");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            acceptor.start();
            System.out.println("Serveur en écoute sur le port " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
