package Communication.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class NetworkServer {
    private final CommunicationServerController refToCommServerController;
    private ServerSocket comm;
    private int port; // Config
    private Map<String, NetworkUser> connexion;

    public NetworkServer(CommunicationServerController ref, int port) {
        this.refToCommServerController = ref;
        this.port = port;
        this.connexion = new HashMap<>();
    }

    public void start() {
        try {
            comm = new ServerSocket(port);
            Thread acceptor = new Thread(() -> {
                while (true) {
                    try {
                        Socket client = comm.accept();
                        connexion.put(Arrays.toString(client.getInetAddress().getAddress()), new NetworkUser(refToCommServerController, client));
                        System.out.println("Nouveau client");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            acceptor.start();
            System.out.println("Serveur en écoute sur le port " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void close() throws IOException {
        if (!comm.isClosed() ) {
            comm.close();
        }
    }

}
