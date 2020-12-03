package IHMChannel.interfaces;

import IHMChannel.IHMChannelController;
import common.interfaces.client.IIHMMainToIHMChannel;
import common.sharedData.Channel;
import javafx.scene.layout.Region;

import java.util.UUID;

public class IHMMainToIHMChannel implements IIHMMainToIHMChannel {

    public IHMMainToIHMChannel(IHMChannelController controller){

        this.controller = controller;
    }

    /**
     * Ouvre un channel
     *
     * @param channelId [Channel] Channel qui a été sélectionné.
     */
    @Override
    public void openChannel(UUID channelId) {
        controller.getInterfaceToCommunication().getChannelHistory(channelId);
    }

//    /**
//     * Envoie une demande pour rejoindre un channel
//     *
//     * @param channel [Channel] Channel que l'on souhaite rejoindre.
//     */
//    @Override
//    public void askToJoin(Channel channel) {
//        throw new UnsupportedOperationException("Not implemented yet");
//    }

    /**
     * Génère puis Retourne le noeud de la vue de IHMChannel
     *
     * @return
     */
    @Override
    public Region getIHMChannelWindow(Channel channel) {
        controller.setChannelPageToDisplay(channel);
        return (Region)controller.getRoot();
    }

    private IHMChannelController controller;
}
