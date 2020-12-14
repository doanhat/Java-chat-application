package IHMChannel.interfaces;

import IHMChannel.IHMChannelController;
import common.interfaces.client.IIHMMainToIHMChannel;
import common.shared_data.Channel;
import javafx.scene.layout.Region;

public class IHMMainToIHMChannel implements IIHMMainToIHMChannel {

    public IHMMainToIHMChannel(IHMChannelController controller){

        this.controller = controller;
    }

    @Override
    public Region initIHMChannelWindow() {
        controller.setChannelPageToDisplay();
        return (Region)controller.getRoot();
    }

    @Override
    public void viewChannel(Channel channel) {
        if (controller.getChannelPageController().getOpenedChannels().contains(channel)){
            controller.getChannelPageController().changeTab(channel);
        }
        else{
            controller.getInterfaceToCommunication().askToJoin(channel);
        }
    }

    private IHMChannelController controller;
}
