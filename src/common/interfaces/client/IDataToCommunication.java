package common.interfaces.client;
import common.sharedData.Channel;
import common.sharedData.UserLite;

public interface IDataToCommunication
{
    /**
     * Connection utilisateur local
     *
     **/
    void userConnect(UserLite user);

    /**
     * Transfert au serveur la demande de suppresion d'un channel
     *
     * @param channel [ID] ID de l'objet à supprimer
     **/
    void delete(Channel channel);
}
