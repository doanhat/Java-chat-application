package IHMMain.implementations;

import IHMMain.IHMMainController;
import common.interfaces.client.IDataToIHMMain;
import common.shared_data.Channel;
import javafx.application.Platform;

import java.util.List;
import java.util.UUID;

public class DataToIHMMain implements IDataToIHMMain {

    private IHMMainController ihmMainController;

    public DataToIHMMain(IHMMainController ihmMainController) {
        this.ihmMainController = ihmMainController;
    }

    @Override
    public void removeChannel(UUID channelID) {
        Platform.runLater(() -> {
            ihmMainController.getMainWindowController().getIHMMainWindowController().removeChannelFromList(channelID);
            ihmMainController.getIHMMainToIHMChannel().removeChannel(channelID);
        });
    }

    @Override
    public void addChannel(Channel channel) {
        Platform.runLater(() -> ihmMainController.getVisibleChannels().add(channel));
    }

    @Override
    public void addAllChannels(List<Channel> channels) {
        Platform.runLater(() -> ihmMainController.getVisibleChannels().addAll(channels));
    }
}
