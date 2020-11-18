package Communication.server;

import common.sharedData.UserLite;

import java.net.Socket;
import java.util.List;
import java.util.UUID;

public interface DirectoryFacilitator {
    public void registerClient(Socket clientSocket);

    public void deregisterClient(UUID clientID);

    public NetworkUser getConnection(UUID clientID);

    public List<UserLite> onlineUsers();

    public List<NetworkUser> getAllConnections();

    public List<NetworkUser> getConnections(List<UUID> uuids);
}
