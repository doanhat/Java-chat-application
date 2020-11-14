package IHMChannel;

import IHMChannel.controllers.ChannelController;
import common.sharedData.Channel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class ChannelPageDisplay {
    Channel channel;
    public Parent root= null;

    /**
     * Constructeur de la classe de création de la "page" ChannelPage
     * @param channelToDisplay channel affiché dans la page
     */
    public ChannelPageDisplay(Channel channelToDisplay) throws IOException {
        this.channel = channelToDisplay;
        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("views/ChannelPage.fxml"));
        root = fxmlLoader.load();
        ChannelController channelController = fxmlLoader.getController();
        channelController.setChannel(channel);
    }
}
