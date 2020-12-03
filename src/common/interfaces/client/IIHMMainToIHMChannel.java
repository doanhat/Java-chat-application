package common.interfaces.client;

import common.sharedData.Channel;
import javafx.scene.layout.Region;

import java.util.UUID;

/**
 * Interface fournie par le module IHMChannel pour le module IHMMain
 */
public interface IIHMMainToIHMChannel {
    /**
     * Ouvre un channel
     * @param channelId [Channel] Channel qui a été sélectionné.
     */
    public void openChannel(UUID channelId);

//    /**
//     * Envoie une demande pour rejoindre un channel
//     * @param channel [Channel] Channel que l'on souhaite rejoindre.
//     */
//    public void askToJoin(Channel channel);

    /**
    * Retourne le noeud de la vue de IHMChannel
    * @return
    */
    public Region getIHMChannelWindow(Channel channel);
}