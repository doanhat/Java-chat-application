package IHMMain.controllers;


import common.shared_data.Channel;
import javafx.application.Platform;
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
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    setGraphic(anchorPane);
                }
            });

        }
    }
}
