package commun.interfaces.client;
import commun.Data.Channel;

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
