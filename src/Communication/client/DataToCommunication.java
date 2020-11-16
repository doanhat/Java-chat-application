package Communication.client;

import common.interfaces.client.IDataToCommunication;
import common.sharedData.Channel;
import common.sharedData.UserLite;

public class DataToCommunication implements IDataToCommunication {

    private CommunicationClientController commController;

    public DataToCommunication(CommunicationClientController commController) {
        this.commController = commController;
    }

    /**
     * Connection utilisateur local
     *
     **/
    @Override
    public void userConnect(UserLite user){
        this.commController.start("127.0.0.1", 17777, user); //TODO V1
    }

    /**
     * Transfert au serveur la demande de suppresion d'un channel
     *
     * @param channel [ID] ID de l'objet à supprimer
     **/
    @Override
    public void delete(Channel channel){

    }
}
