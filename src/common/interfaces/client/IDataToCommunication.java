package common.interfaces.client;
import common.shared_data.Channel;
import common.shared_data.UserLite;

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
    void deleteChannel(UUID channelID);
}
