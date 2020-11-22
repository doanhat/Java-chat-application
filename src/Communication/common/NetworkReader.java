package Communication.common;

import Communication.messages.abstracts.NetworkMessage;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.UUID;

public class NetworkReader extends CyclicTask {

    private final CommunicationController commController;
    private final ObjectInputStream socketIn;
    private UUID userID;

    public NetworkReader(CommunicationController commController, ObjectInputStream socketIn) {
        this.commController = commController;
        this.socketIn       = socketIn;
        this.userID         = null;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    public NetworkMessage readMessage() throws IOException, ClassNotFoundException {
        return (NetworkMessage) socketIn.readObject();
    }

    @Override
    public void action() {
        try {
            NetworkMessage message = readMessage();

            // Dispatch message à TaskManager
            commController.taskManager.appendTask(new NetworkMessage.Handler(message, commController));
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch(EOFException e) {
            //eof - no error in this case
        }
        catch (IOException e) {
            e.printStackTrace();

            commController.disconnect(userID);
            cancel = true;
        }
    }
}
