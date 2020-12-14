package common.interfaces.client;
import common.sharedData.Channel;
import common.sharedData.UserLite;

import java.util.List;
import java.util.UUID;

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
     * @param channelID ID de l'objet à supprimer
     **/
    void delete(UUID channelID);

    void sendProprietaryChannels(List<Channel> channels);
}
