package IHMChannel;

import IHMChannel.controllers.ChannelPageController;
import common.sharedData.Channel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class ChannelPageDisplay {

    public Parent root= null;
    public ChannelPageController channelPageController;

    @FXML
    BorderPane pageToDisplay;

    /**
     * Constructeur de la classe de création de la "page" ChannelPage
     */
    public ChannelPageDisplay(Channel channel) throws IOException {

        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("views/ChannelPage.fxml"));
        root = fxmlLoader.load();
        channelPageController = fxmlLoader.getController();
        channelPageController.addOpenedChannel(channel);
        //<ChannelPageController> fxmlLoader.getController().addOpenedChannel();

    }
}
