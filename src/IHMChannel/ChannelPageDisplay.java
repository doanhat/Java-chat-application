package IHMChannel;

import IHMChannel.controllers.ChannelPageController;
import common.sharedData.Channel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class ChannelPageDisplay {

    public Parent root= null;

    /**
     * Constructeur de la classe de création de la "page" ChannelPage
     */
    public ChannelPageDisplay() throws IOException {

        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("views/ChannelPage.fxml"));
        root = fxmlLoader.load();

    }
}
