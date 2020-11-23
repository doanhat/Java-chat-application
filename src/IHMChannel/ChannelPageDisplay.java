package IHMChannel;

import IHMChannel.controllers.ChannelPageController;
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

    /**
     * Getter de la référence au contrôleur de la vue views/ChannelPage.fxml
     * @return la référence au contrôleur de la vue views/ChannelPage.fxml
     */
    public ChannelPageController getChannelPageController() {
        return channelPageController;
    }

    /**
     * Setter de la référence au contrôleur de la vue views/ChannelPage.fxml
     * @param channelPageController la référence au contrôleur de la vue views/ChannelPage.fxml
     */
    public void setChannelPageController(ChannelPageController channelPageController) {
        this.channelPageController = channelPageController;
    }
}
