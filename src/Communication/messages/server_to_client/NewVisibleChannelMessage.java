package Communication.messages.server_to_client;

import Communication.client.CommunicationClientController;
import Communication.messages.abstracts.ServerToClientMessage;
import common.sharedData.Channel;
import common.sharedData.UserLite;
import common.sharedData.Visibility;

import java.io.Serializable;
import java.util.List;

/**
 * Cette classe indique au client qu'un nouveau canal vient d'être créé
 *
 */
public class NewVisibleChannelMessage extends ServerToClientMessage {

	private static final long serialVersionUID = 4145865987516690680L;
	private final Channel channel;

    public NewVisibleChannelMessage(Channel channel){
        this.channel = channel;
    }

    @Override
    protected void handle(CommunicationClientController commClientController) {
        commClientController.notifyVisibleChannel(channel);
    }
}
