package tests.communication;

import Communication.client.CommunicationClientController;
import Communication.client.CommunicationClientInterfaceImpl;
import Communication.common.Parameters;
import common.interfaces.client.ICommunicationToData;
import common.interfaces.client.ICommunicationToIHMChannel;
import common.interfaces.client.ICommunicationToIHMMain;
import common.sharedData.Channel;
import common.sharedData.ChannelType;
import common.sharedData.UserLite;
import common.sharedData.Visibility;
import tests.communication.interfaces_simulation.VirtualCommunicationToData;
import tests.communication.interfaces_simulation.VirtualCommunicationToIHMChannel;
import tests.communication.interfaces_simulation.VirtualCommunicationToIHMMain;

import java.util.*;

public class CommClientCreateChannelTest {
    // shared data between interfaces
    private static List<UserLite> otherUsers    = new ArrayList<>();
    private static  Map<UUID, Channel> channels = new HashMap<>();

    public static void main(String[] args)
    {
        /* --------------------------------- Init Comm Controller ----------------------------------------*/
        CommunicationClientController commClient = new CommunicationClientController();

        Scanner reader = new Scanner(System.in);

        System.out.print("Enter pseudo: ");

        String username     = reader.nextLine();
        UserLite localUser  = new UserLite(username, "dernier maitre de l'air");

        /* --------------------------------- Init Virtual Interfaces ----------------------------------------*/

        ICommunicationToData dataIface = new VirtualCommunicationToData(localUser, otherUsers, channels);
        ICommunicationToIHMMain mainIface = new VirtualCommunicationToIHMMain(otherUsers, channels);
        ICommunicationToIHMChannel channelIface = new VirtualCommunicationToIHMChannel();

        commClient.setupInterfaces(dataIface, mainIface, channelIface);

        /* ------------------------------------------- Test Communication interface ----------------------------------*/
        CommunicationClientInterfaceImpl commInterface = new CommunicationClientInterfaceImpl(commClient);

        commInterface.userConnect(localUser);

        // Wait for connection established
        try {
            Thread.sleep(3000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Create channel
        Channel channel = new Channel("Test Channel", localUser, "Test", Visibility.PUBLIC, ChannelType.SHARED);

        commInterface.createChannel(channel, true, true, localUser);

        // Wait for request to finished
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Command handling
        while (true) {
            System.out.print("Request join channel: ");

            String channelID = reader.nextLine();

            Channel requestChannel = getChannel(channelID);

            if (requestChannel == null) {
                System.err.println("Cannot find channel requester");
            }
            else {
                commInterface.askToJoin(channel);
            }
        }

        //commClient.stop();
    }

    private static Channel getChannel(String channelID) {
        try {
            Channel channel = channels.get(UUID.fromString(channelID));

            return channel;
        }
        catch (IllegalArgumentException e) {
            System.err.println("Argument not valid");
        }

        return null;
    }
}
