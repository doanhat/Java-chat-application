package Communication.client;

import Communication.common.CommunicationController;
import Communication.common.TaskManager;
import Communication.messages.abstracts.NetworkMessage;
import Communication.messages.client_to_server.connection.UserConnectionMessage;
import Communication.messages.client_to_server.connection.UserDisconnectionMessage;
import common.interfaces.client.*;
import common.shared_data.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommunicationClientController extends CommunicationController {

    private final NetworkClient client;
    private final HeartBeat heart;
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final DataClientHandler dataIfaceHandler;
    private final IHMMainHandler mainIfaceHandler;
    private final IHMChannelHandler channelIfaceHandler;
    private final CommunicationClientInterface commInterface;


    public CommunicationClientController() {
        super();
        client = new NetworkClient(this);
        heart = new HeartBeat(this);
        dataIfaceHandler = new DataClientHandler();
        mainIfaceHandler = new IHMMainHandler();
        channelIfaceHandler = new IHMChannelHandler();
        commInterface = new CommunicationClientInterface(this);
    }


    /* ---------------------------------------------- Core functionalities -------------------------------------------*/

    /**
     * Démarrer Communication Client Controller par se connecter au serveur
     *
     * @param ip adresse ID du serveur
     * @param port port du serveur
     * @param user local user
     */
    public void start(String ip, int port, UserLite user) {
        try {
            taskManager = new TaskManager();
            client.connect(ip, port);
            client.sendMessage(new UserConnectionMessage(user));

            logger.log(Level.INFO, "Connexion au server...");
        }
        catch (IOException e) {
            logger.log(Level.SEVERE, "Echec de connexion au server!");
            disconnect(null);
        }
    }

    /**
     * Arreter Communication Client Controller, annuler tous les threads actifs
     */
    public void stop() {
        taskManager.shutdown();
        heart.stop();

        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Wrapper de stop()
     *
     * @param user local user
     */
    @Override
    public void disconnect(UUID user) {
        sendMessage(new UserDisconnectionMessage(user));

        logger.log(Level.INFO, "Communication Controller déconnecté");

        stop();
    }

    public void connectionAccepted(UserLite localUser, List<UserLite> usersList, List<Channel> channelsList) {
        heart.start(localUser);
        mainHandler().notifyConnectionSuccess(usersList, channelsList);
    }

    /**
     * Envoyer un message réseau au serveur
     *
     * @param message message réseau
     */
    public void sendMessage(NetworkMessage message) {
        client.sendMessage(message);
    }

    /**
     * Recevoir la réponse du server
     */
    public void receiveServerHeartBeat() {
        heart.handleServerReply();
    }

    /* -------------------------------------------- Setup interfaces -------------------------------------------------*/

    /**
     * Installer les interfaces de Data, IHM Main et IHM Channel
     *
     * @param dataIface interface de Data
     * @param mainIface interface de Main
     * @param channelIface interface de Channel
     */
    public void setupInterfaces(ICommunicationToData dataIface,
                                   ICommunicationToIHMMain mainIface,
                                   ICommunicationToIHMChannel channelIface) {
        setICommunicationToData(dataIface);
        setICommunicationToIHMMain(mainIface);
        setICommunicationToIHMChannel(channelIface);
    }

    /**
     * Installer l'interfaces de Data
     *
     * @param dataIface interface de Data
     */
    public void setICommunicationToData(ICommunicationToData dataIface) {
        dataIfaceHandler.setICommunicationToData(dataIface);
    }

    /**
     * Installer l'interfaces de IHM Main
     *
     * @param mainIface interface de IHM Main
     */
    public void setICommunicationToIHMMain(ICommunicationToIHMMain mainIface) {
        mainIfaceHandler.setICommunicationToIHMMain(mainIface);
    }

    /**
     * Installer l'interfaces de IHM Channel
     *
     * @param channelIface Interface de IHMChannel
     */
    public void setICommunicationToIHMChannel(ICommunicationToIHMChannel channelIface) {
        channelIfaceHandler.setICommunicationToIHMChannel(channelIface);
    }

    public IDataToCommunication getDataToCommunication() {
        return commInterface;
    }

    public IIHMChannelToCommunication getIHMChannelToCommunication() {
        return commInterface;
    }

    public IIHMMainToCommunication getIHMMainToCommunication() {
        return commInterface;
    }

    public CommunicationClientInterface getCommunicationClientInterface() {
        return commInterface;
    }

    public DataClientHandler dataClientHandler() {
        return dataIfaceHandler;
    }

    public IHMChannelHandler channelHandler() {
        return channelIfaceHandler;
    }

    public IHMMainHandler mainHandler() {
        return mainIfaceHandler;
    }
}
