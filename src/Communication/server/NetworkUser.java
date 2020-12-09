package Communication.server;

import Communication.common.*;
import Communication.messages.abstracts.NetworkMessage;
import Communication.messages.client_to_server.connection.UserConnectionMessage;
import Communication.messages.server_to_client.connection.AcceptationMessage;
import Communication.messages.server_to_client.connection.NewUserConnectedMessage;
import common.sharedData.Channel;
import common.sharedData.UserLite;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe d'un utilisateur connecté au serveur. Cette classe permet de gérer le socket et les messages entrant et sortant de l'utilisateurs, ainsi que maintenir l'état du client.
 *
 */
public class NetworkUser {

    private final CommunicationServerController commController;
    private UserLite userInfo;
    private Socket socket;
    private ObjectOutputStream socketOut;
    private NetworkReader reader;
    private boolean isActive;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * Construit l'objet depuis le socket du client
     * @param commController controlleur de communication
     * @param socket socket du client
     */
    public NetworkUser(CommunicationServerController commController, Socket socket) {
        this.commController = commController;
        this.socket         = socket;
        this.isActive       = true;

        try {
            this.socketOut  = new ObjectOutputStream(this.socket.getOutputStream());
            this.reader     = new NetworkReader(commController,
                                                new ObjectInputStream(this.socket.getInputStream()));

            // NOTE: read first message after connection establish has to be NewUserConnectionMessage
            UserConnectionMessage connectionMessage = (UserConnectionMessage) this.reader.readMessage();

            if (connectionMessage != null) {
                this.userInfo = connectionMessage.getUser();
                
                UUID userUUID = this.userInfo.getId();
                this.reader.setUserID(userUUID);
                logger.log(Level.FINE, "Accepte connection du client {}" , userUUID);

                List<Channel> userChannels = commController.getUserChannels(this.userInfo);
                List<UserLite> onlineUsers = commController.onlineUsers();

                /**
                 * We add the current user on the list off onlineUsers here,
                 * because at the moment, commController.onlineUSers() doesn't know this current user
                 * IT's not yet register inside server connections
                 */
                onlineUsers.add(this.userInfo);

                commController.sendMessage(preparePacket(new AcceptationMessage(userChannels, onlineUsers)));
                commController.sendBroadcast(new NewUserConnectedMessage(this.userInfo), this.userInfo);
            }
            else {
                logger.log(Level.WARNING, "Echec dans la recuperation UUID du nouveau client");
                return;
            }

            // dispatch reader to thread pool after connection procedure
            logger.log(Level.FINE, "Démarrage du Socket reader pour client {}" , uuid());

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

    /**
     * récupère l'uuid du client
     * @return l'UUID du client en utilisant la methode {@link UserLite#getId()}
     */
    public UUID uuid() {
        return userInfo.getId();
    }

    public UserLite userInfo() {
        return userInfo;
    }

    public boolean isActive() {
        return isActive;
    }

    /**
     * Place le client en tant qu'actif ou en tant qu'abscent
     * @param flag <code>true</code> si le client est actif, <code>false</code> sinon.
     */
    public void active(boolean flag) {
        logger.log(Level.FINER, "Client {0} active {1}", new Object[]{uuid(), flag});
        this.isActive = flag;
    }

    /**
     * Prepare le Packet pour un message réseau défini en l'encapsulant dans un {@link NetworkWriter.DeliveryPacket}
     * @param message message a encapsuler
     * @return instance créée du {@link NetworkWriter.DeliveryPacket}
     */
    public NetworkWriter.DeliveryPacket preparePacket(NetworkMessage message) {
        return new NetworkWriter.DeliveryPacket(socketOut, message);
    }

    /**
     * Ferme le socket de communication
     * @throws IOException Si le socket renvoie une IOException
     */
    public void stop() throws IOException {
        socket.close();
    }

    /**
     * getter de UserInfo
     * @return information de l'utilisateur pour lequel ce socket est ouvert
     */
    public UserLite getUserInfo() {
        return userInfo;
    }
}
