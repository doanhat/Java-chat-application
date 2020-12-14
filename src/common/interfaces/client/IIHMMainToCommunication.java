package common.interfaces.client;

import common.shared_data.*;

public interface IIHMMainToCommunication
{
    /**
     * Mettre adresse du serveur
     * @param addressIP
     */
    public void setIP(String addressIP);

    /**
     * Mettre port du serveur
     * @param port
     */
    public void setPort(int port);

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
}
