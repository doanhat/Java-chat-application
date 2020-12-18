package IHMChannel.interfaces;

import IHMChannel.IHMChannelController;
import common.interfaces.client.IIHMMainToIHMChannel;
import common.shared_data.Channel;
import common.shared_data.UserLite;
import javafx.scene.layout.Region;

import java.util.List;
import java.util.UUID;

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

    @Override
    public List<UserLite> getConnectedUsers(UUID channelId) {
        return controller.getChannelPageController().getChannelController(channelId).getConnectedMembersList();
    }


    private IHMChannelController controller;
}
