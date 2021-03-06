package communication.messages.server_to_client.channel_operation.proprietary_channels;

import communication.client.CommunicationClientController;
import communication.common.ChannelOperation;
import communication.common.info_packages.InfoPackage;
import communication.messages.abstracts.ServerToClientMessage;
import communication.messages.client_to_server.channel_operation.proprietary_channels.OwnerValidateChannelOperationMessage;

public class InformOwnerChannelOperationMessage extends ServerToClientMessage {

    private static final long             serialVersionUID = -287745780048758221L;
    private final        ChannelOperation operation;
    private final        InfoPackage      infoPackage;

    /**
     * Informe un proprietaire d'une opération sur un de ses channels
     * @param operation Opération
     * @param infoPackage Contenu l'opération
     */
    public InformOwnerChannelOperationMessage(ChannelOperation operation, InfoPackage infoPackage) {
        this.operation   = operation;
        this.infoPackage = infoPackage;
    }

    @Override
    protected void handle(CommunicationClientController commController) {
        commController.dataClientHandler().saveChat(operation, infoPackage);

        // Send Confirmation back to Server
        commController.sendMessage(new OwnerValidateChannelOperationMessage(operation, infoPackage));
    }
}
