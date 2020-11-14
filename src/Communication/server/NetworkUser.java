package Communication.server;

import Communication.common.NetworkMessage;
import Communication.common.NetworkReader;
import Communication.common.NetworkWriter;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

public class NetworkUser {

    private final CommunicationServerController refToCommController;
    private UUID id;
    private NetworkReader reader;
    private NetworkWriter writer;

    public NetworkUser(CommunicationServerController ref, Socket comm) {
        refToCommController = ref;
        try {
            writer = new NetworkWriter(comm);
            reader = new NetworkReader(refToCommController, comm);
            writer.start();
            reader.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(NetworkMessage message) {
        writer.sendMessage(message);
    }
}
