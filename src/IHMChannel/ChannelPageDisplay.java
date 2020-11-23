package IHMChannel;

import IHMChannel.controllers.ChannelPageController;
import common.IHMTools.IHMTools;
import common.sharedData.Channel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class ChannelPageDisplay {

    public Parent root= null;
    private ChannelPageController channelPageController;
    private IHMChannelController ihmChannelController;

    @FXML
    BorderPane pageToDisplay;

    /**
     * Constructeur de la classe de création de la "page" ChannelPage
     */
    public ChannelPageDisplay(Channel channel, IHMChannelController ihmChannelController) throws IOException {

        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("views/ChannelPage.fxml"));
        root = fxmlLoader.load();
        setChannelPageController(fxmlLoader.getController());
        this.ihmChannelController = ihmChannelController;
        ihmChannelController.setRoot(root);
        getChannelPageController().setIhmChannelController(ihmChannelController);
        getChannelPageController().addOpenedChannel(channel);
        //<ChannelPageController> fxmlLoader.getController().addOpenedChannel();

    }

    public ChannelPageController getChannelPageController() {
        return channelPageController;
    }

    public void setChannelPageController(ChannelPageController channelPageController) {
        this.channelPageController = channelPageController;
    }

    public Parent getRoot() { return root;}
}
