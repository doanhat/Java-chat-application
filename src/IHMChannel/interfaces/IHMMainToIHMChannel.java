package IHMChannel.interfaces;

import IHMChannel.ChannelPageDisplay;
import IHMChannel.IHMChannelController;
import common.interfaces.client.IIHMMainToIHMChannel;
import common.sharedData.Channel;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;

public class IHMMainToIHMChannel implements IIHMMainToIHMChannel {

    public IHMMainToIHMChannel(IHMChannelController controller){

        this.controller = controller;
    }

    /**
     * Ouvre un channel
     *
     * @param channel [Channel] Channel qui a été sélectionné.
     */
    @Override
    public void openChannel(Channel channel) {

        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Envoie une demande pour rejoindre un channel
     *
     * @param channel [Channel] Channel que l'on souhaite rejoindre.
     */
    @Override
    public void askToJoin(Channel channel) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Génère puis Retourne le noeud de la vue de IHMChannel
     *
     * @return
     */
    @Override
    public Region getIHMChannelWindow() {
        //Parent root = FXMLLoader.load(getClass().getResource("views/ChannelPage.fxml"));
        //Data init
        Channel channel = null;
        ChannelPageDisplay channelPageDisplay = null;

            controller = new IHMChannelController(); //passer ici le channel


        return (Region)controller.getRoot();
    }

    private IHMChannelController controller;
}
