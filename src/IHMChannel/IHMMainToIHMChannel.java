package IHMChannel;

import IHMTools.IHMTools;
import common.interfaces.client.IIHMMainToIHMChannel;
import common.sharedData.Channel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Region;

import java.io.IOException;

public class IHMMainToIHMChannel implements IIHMMainToIHMChannel {


    /**
     * Ouvre un channel
     *
     * @param channel [Channel] Channel qui a été sélectionné.
     */
    @Override
    public void openChannel(Channel channel) {

    }

    /**
     * Envoie une demande pour rejoindre un channel
     *
     * @param channel [Channel] Channel que l'on souhaite rejoindre.
     */
    @Override
    public void askToJoin(Channel channel) {

    }

    /**
     * Retourne le noeud de la vue de IHMChannel
     * @return
     */
    @Override
    public Region getIHMChannelWindow() {
        try {
            FXMLLoader fxmlLoader = new
                    FXMLLoader(getClass().getResource("../IHMChannel/IHMChannelWindow.fxml"));
            Parent parent = fxmlLoader.load(); //On recupère le noeud racine du fxml chargé
            return (Region)parent;
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }


}
