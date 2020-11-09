package common.interfaces.client;
import common.sharedData.Channel;

public interface IDataToCommunication
{
    /**
     * Connection d'un utilisateur
     *
     **/
    void userConnexion();

    /**
     * Transfert au serveur la demande de suppresion d'un channel
     *
     * @param channel [ID] ID de l'objet à supprimer
     **/
    void delete(Channel channel);
}
