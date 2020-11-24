package Communication.common;

import Communication.messages.abstracts.NetworkMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.UUID;

public class NetworkReader implements Runnable {

    private final CommunicationController commController;
    private final ObjectInputStream socketIn;
    private UUID user;

    public NetworkReader(CommunicationController commController, ObjectInputStream socketIn) {
        this.commController = commController;
        this.socketIn = socketIn;
        this.user = null;
    }

    public UUID getUser() {
        return user;
    }

    public NetworkMessage readMessage() throws IOException, ClassNotFoundException {
        return (NetworkMessage) socketIn.readObject();
    }

    @Override
    public void run() {
        while (true) {
            try {
                NetworkMessage message = readMessage();

                // Dispatch message à TaskManager
                commController.taskManager.appendTask(new NetworkMessage.Handler(message, commController));
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                commController.disconnect(this.user);
                break;
            }
        }
    }
}
