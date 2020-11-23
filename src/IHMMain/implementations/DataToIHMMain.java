package IHMMain.implementations;

import IHMMain.IHMMainController;
import common.interfaces.client.IDataToIHMMain;
import common.sharedData.Channel;

import java.util.List;

public class DataToIHMMain implements IDataToIHMMain {

    private IHMMainController ihmMainController;

    public DataToIHMMain(IHMMainController ihmMainController) {
        this.ihmMainController = ihmMainController;
    }

    @Override
    public void removeChannel(Channel channel) {
        ihmMainController.getVisibleChannels().remove(channel);
    }

    @Override
    public void addChannelToList(Channel channel) {
        ihmMainController.getVisibleChannels().add(channel);
    }

    @Override
    public void updateListChannel(List<Channel> channelList) {
        ihmMainController.getVisibleChannels().setAll(channelList);
    }
}
