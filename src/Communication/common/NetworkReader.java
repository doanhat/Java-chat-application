package Communication.common;

import Communication.messages.abstracts.NetworkMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.UUID;

public class NetworkReader extends CyclicTask {

    private final CommunicationController commController;
    private final ObjectInputStream socketIn;
    //private List<NetworkMessage> messagesQueue;
    private UUID user;

    public NetworkReader(CommunicationController commController, ObjectInputStream socketIn) {
        this.commController = commController;
        this.socketIn = socketIn;
        this.user = null;
    }

    public NetworkMessage readMessage() throws IOException, ClassNotFoundException {
        return (NetworkMessage) socketIn.readObject();
    }

    @Override
    protected void action() {
        try {
            NetworkMessage message = readMessage();

            // Dispatch message à TaskManager
            commController.taskManager.appendTask(new NetworkMessage.Handler(message, commController));
            //messagesQueue.add(message)
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            this.stop();
            commController.disconnect(this.user);
        }
    }
}
