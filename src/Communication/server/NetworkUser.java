package Communication.server;

import Communication.common.*;
import Communication.messages.abstracts.NetworkMessage;
import Communication.messages.client_to_server.UserConnectionMessage;
import Communication.messages.server_to_client.AcceptationMessage;
import Communication.messages.server_to_client.NewUserConnectedMessage;
import common.shared_data.Channel;
import common.shared_data.UserLite;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
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
                this.reader.setUserID(this.userInfo.getId());

                System.err.println("Accepte connection du client " + uuid());

                List<Channel> userChannels = commController.getUserChannels(this.userInfo);
                List<UserLite> onlineUsers = commController.onlineUsers();

                commController.sendMessage(preparePacket(new AcceptationMessage(userChannels, onlineUsers)));

                commController.sendBroadcast(new NewUserConnectedMessage(this.userInfo), this.userInfo);
            }
            else {
                System.err.println("Echec dans la recuperation UUID du nouveau client");

                return;
            }

            // dispatch reader to thread pool after connection procedure
            System.err.println("Demarrer Socket reader pour client " + uuid());

            commController.taskManager.appendCyclicTask(this.reader);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            try {
                this.stop();
            }
            catch (IOException ioException) {
                ioException.printStackTrace();
            }
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
        socket.close();
    }

    public UserLite getUserInfo() {
        return userInfo;
    }
}
