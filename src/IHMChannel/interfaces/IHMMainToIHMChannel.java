package IHMChannel.interfaces;

import IHMChannel.IHMChannelController;
import common.interfaces.client.IIHMMainToIHMChannel;
import common.sharedData.Channel;
import javafx.scene.layout.Region;

import java.util.UUID;

public class IHMMainToIHMChannel implements IIHMMainToIHMChannel {

    public IHMMainToIHMChannel(IHMChannelController controller){

        this.controller = controller;
    }

    @Override
    public Region initIHMChannelWindow(Channel channel) {
        controller.setChannelPageToDisplay(channel);
        return (Region)controller.getRoot();
    }

    @Override
    public void viewChannel(UUID channelId) {

    }

    private IHMChannelController controller;
}
