package Communication.client;

import common.interfaces.client.IDataToCommunication;
import common.sharedData.Channel;
import common.sharedData.UserLite;
import Communication.common.Parameters;

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
        this.commController.start(Parameters.SERVER_IP, Parameters.PORT, user); //TODO V1
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
