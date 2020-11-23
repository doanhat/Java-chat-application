package tests.communication;

import Communication.client.CommunicationClientController;
import Communication.client.CommunicationClientInterfaceImpl;
import Communication.common.Parameters;
import common.interfaces.client.ICommunicationToData;
import common.interfaces.client.ICommunicationToIHMChannel;
import common.interfaces.client.ICommunicationToIHMMain;
import common.sharedData.Channel;
import common.sharedData.SharedChannel;
import common.sharedData.UserLite;
import common.sharedData.Visibility;
import tests.communication.interfaces_simulation.VirtualCommunicationToData;
import tests.communication.interfaces_simulation.VirtualCommunicationToIHMChannel;
import tests.communication.interfaces_simulation.VirtualCommunicationToIHMMain;

import java.util.*;

public class CommClientCreateChannelTest {
    public static void main(String[] args)
    {
        /* --------------------------------- Init Comm Controller ----------------------------------------*/
        CommunicationClientController commClient = new CommunicationClientController();

        Scanner reader = new Scanner(System.in);

        System.out.print("Enter pseudo: ");

        String username     = reader.nextLine();
        UserLite localUser  = new UserLite(username, "dernier maitre de l'air");

        commClient.start(Parameters.SERVER_IP, Parameters.PORT, localUser);


        /* --------------------------------- Init Virtual Interfaces ----------------------------------------*/

        // shared data between interfaces
        List<UserLite> otherUsers = new ArrayList<>();
        Map<UUID, Channel> channels = new HashMap<>();

        ICommunicationToData dataIface = new VirtualCommunicationToData(localUser, otherUsers, channels);
        ICommunicationToIHMMain mainIface = new VirtualCommunicationToIHMMain(otherUsers, channels);
        ICommunicationToIHMChannel channelIface = new VirtualCommunicationToIHMChannel();

        commClient.setupInterfaces(dataIface, mainIface, channelIface);

        // Wait for connection established
        try {
            Thread.sleep(3000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        /* ------------------------------------------- Test Communication interface ----------------------------------*/
        CommunicationClientInterfaceImpl commInterface = new CommunicationClientInterfaceImpl(commClient);

        Channel channel = new SharedChannel("Test Channel", localUser, "Test", Visibility.PUBLIC);

        commInterface.createChannel(channel, true, true, localUser);

        //commClient.stop();
    }
}
