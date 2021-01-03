package communication.messages.server_to_client.channel_operation;

import communication.client.CommunicationClientController;
import communication.common.ChannelOperation;
import communication.common.info_packages.InfoPackage;
import communication.messages.abstracts.ServerToClientMessage;


public class ReceiveChannelOperationMessage extends ServerToClientMessage {

    private static final long             serialVersionUID = -8527237423704319L;
    private final        ChannelOperation operation;
    private final        InfoPackage      infoPackage;

    /**
     * Message informant les utilisateurs connectés a un channel d'une opération
     * @param operation Opération
     * @param infoPackage   Contenu de l'opération
     */
    public ReceiveChannelOperationMessage(ChannelOperation operation, InfoPackage infoPackage) {
        this.operation   = operation;
        this.infoPackage = infoPackage;
    }

    @Override
    protected void handle(CommunicationClientController commController) {
        commController.dataClientHandler().notifyChat(operation, infoPackage);
    }
}
