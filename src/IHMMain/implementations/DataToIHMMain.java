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
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ihmMainController.getVisibleChannels().remove(channel);
            }
        });
    }

    @Override
    public void addChannelToList(Channel channel) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ihmMainController.getVisibleChannels().add(channel);
            }
        });
    }

    @Override
    public void updateListChannel(List<Channel> channelList) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ihmMainController.getVisibleChannels().setAll(channelList);
            }
        });
    }
}
