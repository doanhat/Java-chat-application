package commun.Communication.Client;

public interface IDataToCommunication
{
    /**
     * Connection d'un utilisateur
     *
     **/
    void userConnexion();

    /**
     * Transfert au serveur la demande de suppresion d'un objet
     *
     * @param objectId [ID] ID de l'objet à supprimer
     **/
    // TODO verify class ID
    //void delete(ID objectId);
}
