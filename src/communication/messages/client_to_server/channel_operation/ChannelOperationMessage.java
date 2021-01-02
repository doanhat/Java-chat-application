package communication.messages.client_to_server.channel_operation;

import communication.common.ChannelOperation;
import communication.common.info_packages.InfoPackage;
import communication.messages.abstracts.ClientToServerMessage;
import communication.messages.server_to_client.channel_operation.proprietary_channels.InformOwnerChannelOperationMessage;
import communication.server.CommunicationServerController;
import common.shared_data.Channel;
import common.shared_data.ChannelType;

public class ChannelOperationMessage extends ClientToServerMessage {

    private static final long             serialVersionUID = 165413248758221L;
    private final        ChannelOperation operation;
    private final        InfoPackage      infoPackage;

    /**
     * Message d'un utilisateur souhaitant faire une opération sur un channel
     * @param operation Opération
     * @param infoPackage Contenu de l'opération
     */
    public ChannelOperationMessage(ChannelOperation operation, InfoPackage infoPackage) {
        this.operation   = operation;
        this.infoPackage = infoPackage;
    }


    @Override
    protected void handle(CommunicationServerController commController) {
        Channel channel = commController.getChannel(infoPackage.channelID);

        if (channel == null) {
            return;
        }

        // Server serves as a proxy in case of proprietary Channel
        if (channel.getType() == ChannelType.OWNED) {
            commController.sendMessage(channel.getCreator().getId(),
                                       new InformOwnerChannelOperationMessage(operation, infoPackage));
        }
        else {
            commController.handleChannelOperation(operation, infoPackage);
        }
    }
}
