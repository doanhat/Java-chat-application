package IHMChannel.interfaces;

import common.interfaces.client.IIHMMainToIHMChannel;
import common.sharedData.Channel;

public class IHMMainToIHMChannel implements IIHMMainToIHMChannel {
    /**
     * Ouvre un channel
     *
     * @param channel [Channel] Channel qui a été sélectionné.
     */
    @Override
    public void openChannel(Channel channel) {

        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Envoie une demande pour rejoindre un channel
     *
     * @param channel [Channel] Channel que l'on souhaite rejoindre.
     */
    @Override
    public void askToJoin(Channel channel) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
