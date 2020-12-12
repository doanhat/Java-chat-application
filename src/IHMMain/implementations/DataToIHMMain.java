package IHMMain.implementations;

import IHMMain.IHMMainController;
import common.interfaces.client.IDataToIHMMain;
import common.sharedData.Channel;
import javafx.application.Platform;

import java.util.List;

public class DataToIHMMain implements IDataToIHMMain {

    private IHMMainController ihmMainController;

    public DataToIHMMain(IHMMainController ihmMainController) {
        this.ihmMainController = ihmMainController;
    }

    @Override
    public void removeChannel(Channel channel) {
        Platform.runLater(() -> ihmMainController.getVisibleChannels().remove(channel));
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
