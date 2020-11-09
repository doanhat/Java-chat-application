package commun.Communication.Client;
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
     * @param objectId [ID] ID de l'objet à supprimer
     **/
    void delete(Channel channel);
}
