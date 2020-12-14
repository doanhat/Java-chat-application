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
        //controller.getInterfaceToCommunication().getChannelHistory(channel.getId()); //TODO décommenter cette ligne pour intégration avec Comm
        //TODO: enlever cette ligne pour intégration avec Comm. Elle ne sert qu'aux tests
        //Membres connectés
        List<String> nickName = new ArrayList<>();
        nickName.add("Léa");
        nickName.add("Aida");
        nickName.add("Lucas");
        nickName.add("Vladimir");
        nickName.add("Jérôme");
        nickName.add("Van-Triet");
        List<UserLite> connectedUsers = new ArrayList<>();
        for(int i=0; i < nickName.size(); i++){
            UserLite u = new UserLite();
            u.setNickName(nickName.get(i));
            connectedUsers.add(u);
        }

        controller.getInterfaceForCommunication().displayChannelHistory(channel, channel.getMessages(), connectedUsers);



        return (Region)controller.getRoot();
    }

    @Override
    public void viewChannel(UUID channelId) {
        controller.getInterfaceToCommunication().getChannelHistory(channelId);
    }

    private IHMChannelController controller;
}
