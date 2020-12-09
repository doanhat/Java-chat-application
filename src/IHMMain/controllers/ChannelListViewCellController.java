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

    private PseudoClass openedPseudoClass = PseudoClass.getPseudoClass("opened");

    public ChannelListViewCellController(IHMMainController ihmMainController) {
        /**
         * Add listener on the item to update style if the item (channel) became a opened channel
         * We add pseudo class :opened on the item
         */
        InvalidationListener listener = observable -> pseudoClassStateChanged(
                openedPseudoClass,
                getItem() != null && ihmMainController.getOpenedChannels().contains(getItem())
        );
        ihmMainController.getOpenedChannels().addListener(listener);
        itemProperty().addListener(listener);
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
