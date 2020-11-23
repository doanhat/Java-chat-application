package IHMMain.implementations;

import IHMMain.IHMMainController;
import common.interfaces.client.IIHMChannelToIHMMain;
import common.sharedData.Channel;

public class IHMChannelToIHMMain implements IIHMChannelToIHMMain {

    private IHMMainController ihmMainController;

    public IHMChannelToIHMMain(IHMMainController ihmMainController) {
        this.ihmMainController = ihmMainController;
    }

    @Override
    public void redirectToHomePage() {

    }

    @Override
    public void closeChannel(Channel channel) {
        ihmMainController.getVisibleChannels().remove(channel);
    }
}
