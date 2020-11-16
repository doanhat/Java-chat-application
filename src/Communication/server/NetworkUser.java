package Communication.server;

import Communication.common.*;
import Communication.messages.abstracts.NetworkMessage;
import Communication.messages.client_to_server.UserConnectionMessage;
import common.sharedData.UserLite;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

public class NetworkUser {

    private final CommunicationServerController commController;
    private UserLite userInfo;
    private Socket socket;
    private ObjectOutputStream socketOut;
    private NetworkReader reader;

    public NetworkUser(CommunicationServerController commController, Socket socket) {
        this.commController = commController;
        this.socket         = socket;

        try {
            this.socketOut  = new ObjectOutputStream(this.socket.getOutputStream());
            this.reader     = new NetworkReader(commController,
                                                new ObjectInputStream(this.socket.getInputStream()));

            // NOTE: read first message after connection establish has to be NewUserConnectionMessage
            UserConnectionMessage connectionMessage = (UserConnectionMessage) this.reader.readMessage();

            if (connectionMessage != null) {
                this.userInfo = connectionMessage.getUser();
                System.out.println("Nouveau client - UUID: " + this.userInfo.getId());
                commController.taskManager.appendTask(new NetworkMessage.Handler(connectionMessage, commController));
            }
            else {
                System.out.println("Echec dans la recuperation UUID du nouveau client");
            }

            // dispatch reader to thread pool after connection procedure
            commController.taskManager.appendCyclicTask(this.reader);
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public UUID uuid() {
        return userInfo.getId();
    }

    public UserLite userInfo() {
        return userInfo;
    }

    public NetworkWriter.DeliveryPacket preparePacket(NetworkMessage message) {
        return new NetworkWriter.DeliveryPacket(socketOut, message);
    }

    public void stop() throws IOException {
        reader.stop();
        socket.close();
    }
}