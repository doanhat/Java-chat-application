package tests.communication;

import communication.server.CommunicationServerController;
import common.interfaces.server.IServerCommunicationToData;
import common.shared_data.Channel;
import common.shared_data.UserLite;
import tests.communication.interfaces_simulation.VirtualDataServer;

import java.util.*;

public class CommServerTest {

    public static void main(String[] args)
    {
        // local data
        List<UserLite> users = new ArrayList<>();
        Map<UUID, Channel> channels = new HashMap<>();
        Map<UserLite, List<UUID>> mapUserChannels = new HashMap<>();

        IServerCommunicationToData dataServer = new VirtualDataServer(users, channels, mapUserChannels);

        CommunicationServerController commServer = new CommunicationServerController();

        commServer.setIServerCommunicationToData(dataServer);

        commServer.start();

        Scanner reader = new Scanner(System.in);

        while (true) {
            System.out.print("Disconnect: ");

            if("exit".equals(reader.nextLine())) {
                break;
            }
        }

        commServer.stop();

        return;
    }
}
