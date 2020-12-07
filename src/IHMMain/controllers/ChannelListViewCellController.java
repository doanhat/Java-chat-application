package IHMMain.controllers;


import IHMMain.IHMMainController;
import common.sharedData.Channel;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ChannelListViewCellController extends ListCell<Channel> {
    @FXML
    private Label title;

    @FXML
    private Label description;

    @FXML
    private AnchorPane anchorPane;

    private FXMLLoader fxmlLoader;

    private IHMMainController ihmMainController;

    private PseudoClass openedPseudoClass = PseudoClass.getPseudoClass("opened");

    public ChannelListViewCellController(IHMMainController ihmMainController) {
        this.ihmMainController = ihmMainController;

        if (this.getItem() != null && this.ihmMainController.getOpenedChannels() != null) {
            InvalidationListener listener = observable -> this.pseudoClassStateChanged(openedPseudoClass, ihmMainController.getOpenedChannels().stream().anyMatch(c -> c.getId() == this.getItem().getId()));
            ihmMainController.getOpenedChannels().addListener(listener);
        }
    }

    @Override
    protected void updateItem(Channel channel, boolean empty) {
        super.updateItem(channel, empty);

        if (empty || channel == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("../views/ChannelListViewCell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            title.setText(channel.getName());
            description.setText(channel.getDescription());

            setText(null);
            setGraphic(anchorPane);
            
        }
    }
}
