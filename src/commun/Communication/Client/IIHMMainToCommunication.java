package commun.Communication.Client;

import commun.Data.*;
import java.util.*;

public interface IIHMMainToCommunication
{
    /**
     * Retourne la liste des utilisateurs connectés à l'application
     **/
    List<UserLite> getConnectedUsers();
    /**
     * Retourne la liste des canaux visibles
     **/
    List<Channel> getChannels();

    /**
     * Demande la creation d'un nouveau channel au serveur
     *
     * @param chan [Channel] Objet channel a crée sur le serveur
     * @param isShared [Boolean] Si le channel est partagé ou non
     * @param isPublic [Boolean] Si le channel est publique ou non
     * @param owner [UserLite] Information sur le proprietaire du channel si c'est un channel privé
     **/
    void createChannel(Channel chan, Boolean isShared, Boolean isPublic, UserLite owner);
}
