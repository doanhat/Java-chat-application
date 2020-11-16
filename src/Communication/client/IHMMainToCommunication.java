package Communication.client;

import common.interfaces.client.IIHMMainToCommunication;
import common.sharedData.Channel;
import common.sharedData.UserLite;

import java.util.List;

public class IHMMainToCommunication implements IIHMMainToCommunication {

    private CommunicationClientController commController;

    public IHMMainToCommunication(CommunicationClientController commController) {
        this.commController = commController;
    }

    /**
     * Demande la creation d'un nouveau channel au serveur
     *
     * @param channel [Channel] Objet channel a crée sur le serveur
     * @param isShared [Boolean] Si le channel est partagé ou non
     * @param isPublic [Boolean] Si le channel est publique ou non
     * @param owner [UserLite] Information sur le proprietaire du channel si c'est un channel privé
     **/
    public void createChannel(Channel channel, Boolean isShared, Boolean isPublic, UserLite owner){
        //this.commController.sendMessage(new createChannelMessage(channel, owner));//TODO V1
    }
}
