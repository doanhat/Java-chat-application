package tests.communication;

import Communication.client.CommunicationClientInterface;
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

public class CommClientAddAdminTest {
    // shared data between interfaces
    private static final List<UserLite> otherUsers = new ArrayList<>();
    private static final Map<UUID, Channel> channels = new HashMap<>();

    public static void main(String[] args) {
        /* --------------------------------- Init Comm Controller ----------------------------------------*/
        Scanner reader = new Scanner(System.in);

        System.out.print("Enter pseudo: ");

        String username = reader.nextLine();
        UserLite localUser = new UserLite(username, "dernier maitre de l'air");

        /* --------------------------------- Init Virtual Interfaces ----------------------------------------*/

        ICommunicationToData dataIface = new VirtualCommunicationToData(localUser, otherUsers, channels);
        ICommunicationToIHMMain mainIface = new VirtualCommunicationToIHMMain(otherUsers, channels);
        ICommunicationToIHMChannel channelIface = new VirtualCommunicationToIHMChannel();

        /* ------------------------------------------- Test Communication interface ----------------------------------*/
        CommunicationClientInterface commInterface = CommunicationClientInterface.instance();
        commInterface.setupInterfaces(dataIface, mainIface, channelIface);

        commInterface.userConnect(localUser);

        // Wait for connection established
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Create channel
        Channel channel = new Channel("Test Channel of " + localUser.getNickName(), localUser,
                "Test", Visibility.PUBLIC, ChannelType.SHARED);

        commInterface.createChannel(channel, true, true, localUser);

        // Wait for request to finished
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Command handling
        while (true) {
            System.out.print("Request new add admin:");
            String userId = reader.nextLine();
            UserLite newAdmin = getUser(userId);

            System.out.print("To Channel:");
            String channelID = reader.nextLine();
            Channel requestChannel = getChannel(channelID);

            if (requestChannel == null || newAdmin == null) {
                System.err.println("Cannot find requested channel");
            }
            else {
                commInterface.giveAdmin(newAdmin, channel);
            }
        }

        //commInterface.disconnect();
    }

    private static Channel getChannel(String channelID) {
        try {
            return channels.get(UUID.fromString(channelID));
        }
        catch (IllegalArgumentException e) {
            System.err.println("Argument not valid");
        }

        return null;
    }

    private static UserLite getUser(String userId) {
        UUID id = null;

        try {
            id = UUID.fromString(userId);
        }
        catch (IllegalArgumentException e) {
            System.err.println("Argument not valid");
        }

        for (UserLite user: otherUsers) {
            if (user.getId() == id) {
                return user;
            }
        }

        return null;
    }
}
