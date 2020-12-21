package common.interfaces.client;

import common.shared_data.*;

import java.util.List;
import java.util.UUID;

/**
 * The interface Iihm main to communication.
 */
public interface IIHMMainToCommunication
{
    /**
     * Mettre adresse du serveur
     * @param addressIP
     */
    void setIP(String addressIP);

    /**
     * Mettre port du serveur
     * @param port
     */
    void setPort(int port);

    /**
     * @return Adresse du serveur
     */
    String getIP();

    /**
     * @return Port du serveur
     */
    int getPort();

    /**
     * Déconnecter application
     */
    void disconnect();

    /**
     * Demande la creation d'un nouveau channel au serveur
     *
     * @param channel [Channel] Objet channel a crée sur le serveur
     * @param isShared [Boolean] Si le channel est partagé ou non
     * @param isPublic [Boolean] Si le channel est publique ou non
     * @param owner [UserLite] Information sur le proprietaire du channel si c'est un channel privé
     **/
    void createChannel(Channel channel, Boolean isShared, Boolean isPublic, UserLite owner);

    /**
     * Envoyer une image encodée en string Base64 au server pour stocker
     *
     * @param user          utilisateur ayant l'image comme avatar
     * @param encodedString le string encodée en Base64
     */
    void saveAvatarToServer(UserLite user, String encodedString);

    /**
     * Demande de Récupérer le chemin vers l'avatar de l'utilisateur dans le serveur
     *
     * @param user utilisateur
     * @return
     */
    // NOTE TO MAIN: implement etAvatarPath(UserLite user, String avatarPath); in ICommunicationToIHMMain to receive path from server
    void getAvatarPath(UserLite user);

    void getConnectedUsers(UUID channelID);
}
