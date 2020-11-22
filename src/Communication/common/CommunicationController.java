package Communication.common;

import java.util.UUID;

public abstract class CommunicationController {
    public final TaskManager taskManager = new TaskManager();

    public abstract void disconnect(UUID user);
}
