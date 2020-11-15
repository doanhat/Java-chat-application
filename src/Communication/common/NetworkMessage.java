package Communication.common;

import java.io.Serializable;

public abstract class NetworkMessage implements Serializable
{
    public abstract void handle(CommunicationController controller);

    public static class Handler implements Runnable
    {
        private NetworkMessage message;
        private CommunicationController commController;

        public Handler(NetworkMessage message, CommunicationController commController)
        {
            this.message = message;
            this.commController = commController;
        }

        @Override
        public void run()
        {
            message.handle(commController);
        }
    }
}


