package IHMChannel.interfaces;

import IHMChannel.IHMChannelController;
import common.interfaces.client.IIHMMainToIHMChannel;
import common.sharedData.Channel;
import common.sharedData.UserLite;
import javafx.scene.layout.Region;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IHMMainToIHMChannel implements IIHMMainToIHMChannel {

    public IHMMainToIHMChannel(IHMChannelController controller){

        this.controller = controller;
    }

    @Override
    public Region initIHMChannelWindow(Channel channel) {
        controller.setChannelPageToDisplay(channel);
        controller.getInterfaceToCommunication().askToJoin(channel);
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
