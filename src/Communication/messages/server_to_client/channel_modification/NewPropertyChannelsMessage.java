package Communication.messages.server_to_client.channel_modification;

import Communication.client.CommunicationClientController;
import Communication.common.ChannelOperation;
import Communication.common.CommunicationController;
import Communication.common.info_packages.InfoPackage;
import Communication.messages.abstracts.ServerToClientMessage;

import java.util.UUID;

public class NewPropertyChannelsMessage extends ServerToClientMessage {
    private static final long serialVersionUID = 3965212554134221180L;

    private static InfoPackage infoPackage;
    private static ChannelOperation channelOperation;





    public NewPropertyChannelsMessage(InfoPackage info, ChannelOperation op) {
       this.infoPackage = info;
       this.channelOperation = op;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.dataClientHandler().saveChat(channelOperation, infoPackage);
    }
}
